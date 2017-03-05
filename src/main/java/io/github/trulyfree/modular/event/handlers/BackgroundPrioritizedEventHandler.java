package io.github.trulyfree.modular.event.handlers;

import java.util.Collection;
import java.util.Iterator;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.event.PrioritizedEvent;
import io.github.trulyfree.modular.general.Forkable;

/* Modular library by TrulyFree: A general-use module-building library.
 * Copyright (C) 2016  VTCAKAVSMoACE
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * BackgroundPrioritizedEventHandler class. Most modular programs will likely wish to use this handler type.
 * 
 * @author vtcakavsmoace
 *
 */
public class BackgroundPrioritizedEventHandler extends PrioritizedEventHandler implements Forkable {

	/**
	 * 
	 */
	private volatile boolean running;

	/**
	 * 
	 */
	private volatile boolean activeIO;

	/**
	 * 
	 */
	private final ThreadedEventHandlerManager watcher;

	/**
	 * 
	 */
	private Event before;

	/**
	 * 
	 */
	private Event after;

	/**
	 * @param maxthreads
	 */
	public BackgroundPrioritizedEventHandler(int maxthreads) {
		watcher = new ThreadedEventHandlerManager(maxthreads);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler#enact(
	 * )
	 */
	@Override
	public boolean enact() {
		if (running) {
			return false;
		}
		running = true;
		if (before != null)
			before.enact();
		new Thread(watcher).start();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler#setup(
	 * )
	 */
	@Override
	public boolean setup() {
		if (running) {
			return false;
		}
		return super.setup();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler#
	 * isReady()
	 */
	@Override
	public boolean isReady() {
		return running || super.isReady();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler#
	 * destroy()
	 */
	@Override
	public boolean destroy() {
		safeHalt();
		return super.destroy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler#add(io
	 * .github.trulyfree.modular.event.PrioritizedEvent)
	 */
	@Override
	public boolean add(PrioritizedEvent ero) {
		if (!waitForIO()) {
			return false;
		}
		activeIO = true;
		boolean toReturn = super.add(ero);
		activeIO = false;
		notifyForIO();
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler#size()
	 */
	@Override
	public synchronized int size() {
		if (!waitForIO()) {
			return -1;
		}
		activeIO = true;
		int toReturn = super.size();
		activeIO = false;
		notifyForIO();
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler#
	 * contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		if (!waitForIO()) {
			return false;
		}
		activeIO = true;
		boolean toReturn = super.contains(o);
		activeIO = false;
		notifyForIO();
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler#
	 * toArray()
	 */
	@Override
	public Object[] toArray() {
		if (!waitForIO()) {
			return null;
		}
		activeIO = true;
		Object[] toReturn = super.toArray();
		activeIO = false;
		notifyForIO();
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler#
	 * toArray(java.lang.Object[])
	 */
	@Override
	public <U> U[] toArray(U[] a) {
		if (!waitForIO()) {
			return null;
		}
		activeIO = true;
		U[] toReturn = super.toArray(a);
		activeIO = false;
		notifyForIO();
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler#get(
	 * int, int)
	 */
	@Override
	public PrioritizedEvent get(int ord, int index) {
		if (!waitForIO()) {
			return null;
		}
		activeIO = true;
		PrioritizedEvent toReturn = super.get(ord, index);
		activeIO = false;
		notifyForIO();
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler#remove
	 * (int, int)
	 */
	@Override
	public PrioritizedEvent remove(int ord, int index) {
		if (!waitForIO()) {
			return null;
		}
		activeIO = true;
		PrioritizedEvent toReturn = super.remove(ord, index);
		activeIO = false;
		notifyForIO();
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler#remove
	 * (java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		if (!waitForIO()) {
			return false;
		}
		activeIO = true;
		boolean toReturn = super.remove(o);
		activeIO = false;
		notifyForIO();
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler#
	 * retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		if (!waitForIO()) {
			return false;
		}
		activeIO = true;
		boolean toReturn = super.retainAll(c);
		activeIO = false;
		notifyForIO();
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler#clear(
	 * )
	 */
	@Override
	public void clear() {
		if (!waitForIO()) {
			return;
		}
		activeIO = true;
		super.clear();
		activeIO = false;
		notifyForIO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.general.Forkable#safeHalt()
	 */
	@Override
	public boolean safeHalt() {
		watcher.safeHalt();
		if (after != null)
			after.enact();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.general.Forkable#immediateHalt()
	 */
	@Override
	public boolean immediateHalt() throws Exception {
		watcher.immediateHalt();
		if (after != null)
			after.enact();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.general.Forkable#setBefore(io.github.
	 * trulyfree.modular.event.Event)
	 */
	@Override
	public boolean setBefore(Event event) {
		if (running) {
			return false;
		}
		this.before = event;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.general.Forkable#setAfter(io.github.trulyfree
	 * .modular.event.Event)
	 */
	@Override
	public boolean setAfter(Event event) {
		if (running) {
			return false;
		}
		this.after = event;
		return true;
	}

	/**
	 * @return
	 */
	private boolean waitForIO() {
		synchronized (this.lists) {
			while (activeIO) {
				try {
					this.lists.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 */
	private void notifyForIO() {
		synchronized (this.lists) {
			this.lists.notify();
		}
	}

	/**
	 * @author vtcakavsmoace
	 *
	 */
	private class ThreadedEventHandlerManager implements Forkable, Runnable {

		/**
		 * 
		 */
		private final Thread[] threads;
		
		/**
		 * 
		 */
		private final EventHandlingRunnable[] runnables;

		/**
		 * 
		 */
		private boolean halted;

		/**
		 * @param maxthreads
		 */
		public ThreadedEventHandlerManager(int maxthreads) {
			threads = new Thread[maxthreads];
			runnables = new EventHandlingRunnable[maxthreads];
			for (int i = 0; i < maxthreads; i++) {
				runnables[i] = new EventHandlingRunnable();
				threads[i] = new Thread(runnables[i]);
				threads[i].start();
			}
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			Iterator<PrioritizedEvent> iter = iterator();
			while (!halted) {
				if (iter.hasNext()) {
					assignEvent(iter.next());
				} else {
					synchronized (BackgroundPrioritizedEventHandler.this) {
						try {
							BackgroundPrioritizedEventHandler.this.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			waitForThreads();
			synchronized (BackgroundPrioritizedEventHandler.this) {
				BackgroundPrioritizedEventHandler.this.notifyAll();
			}
		}

		/**
		 * @param next
		 */
		private void assignEvent(PrioritizedEvent next) {
			while (!halted) {
				for (EventHandlingRunnable runnable : runnables) {
					if (runnable.handleEvent(next)) {
						return;
					}
				}
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						continue;
					}
				}
			}
		}

		/* (non-Javadoc)
		 * @see io.github.trulyfree.modular.general.Forkable#safeHalt()
		 */
		@Override
		public boolean safeHalt() {
			halted = true;
			synchronized (BackgroundPrioritizedEventHandler.this) {
				BackgroundPrioritizedEventHandler.this.notifyAll();
			}
			waitForThreads();
			return false;
		}

		/**
		 * 
		 */
		private void waitForThreads() {
			for (Thread thread : threads) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		/* (non-Javadoc)
		 * @see io.github.trulyfree.modular.general.Forkable#immediateHalt()
		 */
		@SuppressWarnings("deprecation")
		@Override
		public boolean immediateHalt() throws Exception {
			for (Thread thread : threads) {
				thread.stop();
			}
			return safeHalt();
		}

		/* (non-Javadoc)
		 * @see io.github.trulyfree.modular.general.Forkable#setBefore(io.github.trulyfree.modular.event.Event)
		 */
		@Override
		public boolean setBefore(Event event) {
			// Unused.
			return false;
		}

		/* (non-Javadoc)
		 * @see io.github.trulyfree.modular.general.Forkable#setAfter(io.github.trulyfree.modular.event.Event)
		 */
		@Override
		public boolean setAfter(Event event) {
			// Unused.
			return false;
		}

		/**
		 * @author vtcakavsmoace
		 *
		 */
		private class EventHandlingRunnable implements Runnable {

			private volatile boolean handling;
			private volatile Event event;

			@Override
			public void run() {
				do {
					waitForEvent();
					handling = true;
					event.enact();
					event = null;
					handling = false;
					synchronized (ThreadedEventHandlerManager.this) {
						ThreadedEventHandlerManager.this.notify();
					}
				} while (event != null);
			}

			/**
			 * @param event
			 * @return
			 */
			public synchronized boolean handleEvent(Event event) {
				if (isHandling()) {
					return false;
				} else {
					this.event = event;
					notify();
					return true;
				}
			}

			/**
			 * @return
			 */
			public boolean isHandling() {
				return handling;
			}

			/**
			 * 
			 */
			private void waitForEvent() {
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}
				}
			}

		}

	}

}
