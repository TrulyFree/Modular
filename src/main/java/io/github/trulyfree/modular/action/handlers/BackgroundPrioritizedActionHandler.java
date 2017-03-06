package io.github.trulyfree.modular.action.handlers;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.github.trulyfree.modular.action.Action;
import io.github.trulyfree.modular.action.PrioritizedAction;
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
 * BackgroundPrioritizedActionHandler class. Most modular programs will likely
 * wish to use this handler type.
 * 
 * @author vtcakavsmoace
 *
 */
public class BackgroundPrioritizedActionHandler extends PrioritizedActionHandler implements Forkable {

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
	private final Thread watcherThread;

	/**
	 * 
	 */
	private Action before;

	/**
	 * 
	 */
	private Action after;

	/**
	 * @param maxthreads
	 */
	public BackgroundPrioritizedActionHandler(int maxthreads) {
		watcher = new ThreadedEventHandlerManager(maxthreads);
		watcherThread = new Thread(watcher);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.action.handlers.PrioritizedActionHandler#
	 * enact( )
	 */
	@Override
	public boolean enact() {
		if (running) {
			return false;
		}
		running = true;
		if (before != null)
			before.enact();
		watcherThread.start();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.action.handlers.PrioritizedActionHandler#
	 * setup( )
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
	 * @see
	 * io.github.trulyfree.modular.action.handlers.PrioritizedActionHandler#
	 * isReady()
	 */
	@Override
	public boolean isReady() {
		return running || super.isReady();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.action.handlers.PrioritizedActionHandler#
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
	 * io.github.trulyfree.modular.action.handlers.PrioritizedActionHandler#add(
	 * io .github.trulyfree.modular.event.PrioritizedEvent)
	 */
	@Override
	public boolean add(PrioritizedAction ero) {
		if (!waitForIO()) {
			return false;
		}
		activeIO = true;
		boolean toReturn = super.add(ero);
		activeIO = false;
		notifyForIO();
		synchronized (this) {
			this.notify();
		}
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.action.handlers.PrioritizedActionHandler#size
	 * ()
	 */
	@Override
	public int size() {
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
	 * @see
	 * io.github.trulyfree.modular.action.handlers.PrioritizedActionHandler#
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
	 * @see
	 * io.github.trulyfree.modular.action.handlers.PrioritizedActionHandler#
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
	 * @see
	 * io.github.trulyfree.modular.action.handlers.PrioritizedActionHandler#
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
	 * io.github.trulyfree.modular.action.handlers.PrioritizedActionHandler#get(
	 * int, int)
	 */
	@Override
	public PrioritizedAction get(int ord, int index) {
		if (!waitForIO()) {
			return null;
		}
		activeIO = true;
		PrioritizedAction toReturn = super.get(ord, index);
		activeIO = false;
		notifyForIO();
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.action.handlers.PrioritizedActionHandler#
	 * remove (int, int)
	 */
	@Override
	public PrioritizedAction remove(int ord, int index) {
		if (!waitForIO()) {
			return null;
		}
		activeIO = true;
		PrioritizedAction toReturn = super.remove(ord, index);
		activeIO = false;
		notifyForIO();
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular.action.handlers.PrioritizedActionHandler#
	 * remove (java.lang.Object)
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
	 * @see
	 * io.github.trulyfree.modular.action.handlers.PrioritizedActionHandler#
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
	 * io.github.trulyfree.modular.action.handlers.PrioritizedActionHandler#
	 * clear( )
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
	 * trulyfree.modular.event.Action)
	 */
	@Override
	public boolean setBefore(Action action) {
		if (running) {
			return false;
		}
		this.before = action;
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
	public boolean setAfter(Action action) {
		if (running) {
			return false;
		}
		this.after = action;
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
		private final ExecutorService pool;

		/**
		 * @param maxthreads
		 */
		public ThreadedEventHandlerManager(int maxthreads) {
			pool = Executors.newFixedThreadPool(maxthreads);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			Iterator<PrioritizedAction> iter = iterator();
			while (!pool.isShutdown()) {
				if (iter.hasNext()) {
					assignEvent(iter.next());
				} else {
					synchronized (BackgroundPrioritizedActionHandler.this) {
						try {
							BackgroundPrioritizedActionHandler.this.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		/**
		 * @param next
		 */
		private void assignEvent(final PrioritizedAction next) {
			Callable<Boolean> task = new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					boolean result = next.enact();
					synchronized (ThreadedEventHandlerManager.this) {
						ThreadedEventHandlerManager.this.notify();
					}
					return result;
				}
			};
			if (!pool.isShutdown()) {
				pool.submit(task);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see io.github.trulyfree.modular.general.Forkable#safeHalt()
		 */
		@Override
		public boolean safeHalt() {
			pool.shutdown();
			synchronized (BackgroundPrioritizedActionHandler.this) {
				BackgroundPrioritizedActionHandler.this.notifyAll();
			}
			synchronized (this) {
				this.notifyAll();
			}
			safelyEndAllThreads();
			return true;
		}

		private void safelyEndAllThreads() {
			waitForThreads();
		}

		/**
		 * 
		 */
		private void waitForThreads() {
			try {
				pool.awaitTermination(100, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see io.github.trulyfree.modular.general.Forkable#immediateHalt()
		 */
		@Override
		public boolean immediateHalt() throws Exception {
			pool.shutdownNow();
			return safeHalt();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * io.github.trulyfree.modular.general.Forkable#setBefore(io.github.
		 * trulyfree.modular.action.Action)
		 */
		@Override
		public boolean setBefore(Action action) {
			// Unused.
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see io.github.trulyfree.modular.general.Forkable#setAfter(io.github.
		 * trulyfree.modular.action.Action)
		 */
		@Override
		public boolean setAfter(Action action) {
			// Unused.
			return false;
		}

	}

}
