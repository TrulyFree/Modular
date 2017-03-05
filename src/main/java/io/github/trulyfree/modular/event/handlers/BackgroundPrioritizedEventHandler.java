package io.github.trulyfree.modular.event.handlers;

import java.util.Collection;
import java.util.Iterator;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.event.PrioritizedEvent;
import io.github.trulyfree.modular.general.Forkable;

public class BackgroundPrioritizedEventHandler extends PrioritizedEventHandler implements Forkable {

	private volatile boolean running;
	private volatile boolean activeIO;

	private final ThreadedEventHandlerManager watcher;

	private Event before;
	private Event after;

	public BackgroundPrioritizedEventHandler(int maxthreads) {
		watcher = new ThreadedEventHandlerManager(maxthreads);
	}

	@Override
	public boolean enact() {
		if (running) {
			return false;
		}
		running = true;
		before.enact();
		// TODO
		return true;
	}

	@Override
	public boolean setup() {
		if (running) {
			return false;
		}
		// TODO working and threads
		return super.setup();
	}

	@Override
	public boolean isReady() {
		return running || super.isReady();
	}

	@Override
	public boolean destroy() {
		safeHalt();
		return super.destroy();
	}

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

	@Override
	public boolean enactNextEvent() {
		return super.enactNextEvent();
	}

	@Override
	public boolean safeHalt() {
		watcher.safeHalt();
		after.enact();
		return true;
	}

	@Override
	public boolean immediateHalt() throws Exception {
		watcher.immediateHalt();
		after.enact();
		return true;
	}

	@Override
	public boolean setBefore(Event event) {
		if (running) {
			return false;
		}
		this.before = event;
		return true;
	}

	@Override
	public boolean setAfter(Event event) {
		if (running) {
			return false;
		}
		this.after = event;
		return true;
	}

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

	private void notifyForIO() {
		synchronized (this.lists) {
			this.lists.notify();
		}
	}

	private class ThreadedEventHandlerManager implements Forkable, Runnable {

		private final Thread[] threads;
		private final EventHandlingRunnable[] runnables;

		private boolean halted;

		public ThreadedEventHandlerManager(int maxthreads) {
			threads = new Thread[maxthreads];
			runnables = new EventHandlingRunnable[maxthreads];
			for (int i = 0; i < maxthreads; i++) {
				runnables[i] = new EventHandlingRunnable();
				threads[i] = new Thread(runnables[i]);
				threads[i].start();
			}
		}

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

		private boolean threadsAreActive() {
			for (Thread thread : threads) {
				if (thread != null) {
					if (thread.isAlive()) {
						return true;
					}
				}
			}
			return false;
		}

		@Override
		public boolean safeHalt() {
			halted = true;
			synchronized (BackgroundPrioritizedEventHandler.this) {
				BackgroundPrioritizedEventHandler.this.notifyAll();
			}
			waitForThreads();
			return false;
		}

		private void waitForThreads() {
			while (threadsAreActive()) {
				synchronized (BackgroundPrioritizedEventHandler.this) {
					try {
						BackgroundPrioritizedEventHandler.this.wait(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean immediateHalt() throws Exception {
			for (Thread thread : threads) {
				thread.stop();
			}
			return safeHalt();
		}

		@Override
		public boolean setBefore(Event event) {
			// Unused.
			return false;
		}

		@Override
		public boolean setAfter(Event event) {
			// Unused.
			return false;
		}

		private class EventHandlingRunnable implements Runnable {

			private volatile boolean handling;
			private volatile Event event;

			@Override
			public void run() {
				do {
					handling = true;
					event.enact();
					event = null;
					handling = false;
					synchronized (ThreadedEventHandlerManager.this) {
						ThreadedEventHandlerManager.this.notify();
					}
					synchronized (this) {
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
							return;
						}
					}
				} while (event != null);
			}

			public synchronized boolean handleEvent(Event event) {
				if (isHandling()) {
					return false;
				} else {
					this.event = event;
					notify();
					return true;
				}
			}

			public boolean isHandling() {
				return handling;
			}

		}

	}

}
