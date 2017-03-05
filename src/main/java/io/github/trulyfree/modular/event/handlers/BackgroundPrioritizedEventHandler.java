package io.github.trulyfree.modular.event.handlers;

import java.util.ArrayList;
import java.util.Collection;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.event.PrioritizedEvent;
import io.github.trulyfree.modular.general.Priority;

import io.github.trulyfree.modular.general.Forkable;

public class BackgroundPrioritizedEventHandler extends PrioritizedEventHandler implements Forkable {

	private volatile Boolean running;

	private volatile boolean[] working;
	private Forkable[] forks;

	private Forkable watcher;

	private Event before;
	private Event after;

	public BackgroundPrioritizedEventHandler(int threads) {
		this.working = new boolean[threads];
		this.forks = new Forkable[threads];
	}

	private BackgroundPrioritizedEventHandler(BackgroundPrioritizedEventHandler handler) {

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
		this.working = new boolean[working.length];
		this.forks = new Forkable[forks.length];
		return super.destroy();
	}

	@Override
	public boolean add(PrioritizedEvent ero) {
		ArrayList<PrioritizedEvent> list = this.lists.get(ero.getPriority().ordinal());
		return super.add(ero);
	}

	@Override
	public synchronized int size() {
		// TODO Auto-generated method stub
		return super.size();
	}

	@Override
	public synchronized boolean isEmpty() {
		// TODO Auto-generated method stub
		return super.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return super.contains(o);
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return super.toArray();
	}

	@Override
	public <U> U[] toArray(U[] a) {
		// TODO Auto-generated method stub
		return super.toArray(a);
	}

	@Override
	public PrioritizedEvent get(int ord, int index) {
		// TODO Auto-generated method stub
		return super.get(ord, index);
	}

	@Override
	public PrioritizedEvent get(int index) {
		// TODO Auto-generated method stub
		return super.get(index);
	}

	@Override
	public PrioritizedEvent get(Priority val, int index) {
		// TODO Auto-generated method stub
		return super.get(val, index);
	}

	@Override
	public int[] getLocPair(int index) {
		// TODO Auto-generated method stub
		return super.getLocPair(index);
	}

	@Override
	public PrioritizedEvent remove(int ord, int index) {
		// TODO Auto-generated method stub
		return super.remove(ord, index);
	}

	@Override
	public PrioritizedEvent remove(int index) {
		// TODO Auto-generated method stub
		return super.remove(index);
	}

	@Override
	public PrioritizedEvent remove(Priority val, int index) {
		// TODO Auto-generated method stub
		return super.remove(val, index);
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return super.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return super.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends PrioritizedEvent> c) {
		// TODO Auto-generated method stub
		return super.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return super.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return super.retainAll(c);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		super.clear();
	}

	@Override
	public boolean addEvent(PrioritizedEvent event) {
		// TODO Auto-generated method stub
		return super.addEvent(event);
	}

	@Override
	public PrioritizedEvent removeEvent(PrioritizedEvent event) {
		// TODO Auto-generated method stub
		return super.removeEvent(event);
	}

	@Override
	public boolean enactNextEvent() {
		return super.enactNextEvent();
	}

	@Override
	public Collection<PrioritizedEvent> getEvents() {
		// TODO Auto-generated method stub
		return super.getEvents();
	}

	@Override
	public boolean safeHalt() {
		for (Forkable fork : forks) {
			fork.safeHalt();
		}
		synchronized (running) {
			try {
				while (threadsAreActive()) {
					running.wait(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
		}
		after.enact();
		return true;
	}

	@Override
	public boolean immediateHalt() throws Exception {
		for (Forkable fork : forks) {
			fork.immediateHalt();
		}
		watcher.immediateHalt();
		running.notifyAll();
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

	private boolean threadsAreActive() {
		for (boolean on : working) {
			if (on) {
				return true;
			}
		}
		return false;
	}

	private class ForkableHandler implements Forkable, Event {

		private PrioritizedEvent event;

		private Thread thread;

		@Override
		public boolean safeHalt() {
			synchronized (thread) {
				try {
					if (thread.isAlive()) {
						thread.wait();
					}
				} catch (InterruptedException e) {
					return false;
				}
			}
			return true;
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean immediateHalt() throws Exception {
			thread.stop();
			return true;
		}

		@Override
		public boolean setBefore(Event event) {
			// Disallowed
			return false;
		}

		@Override
		public boolean setAfter(Event event) {
			// Disallowed.
			return false;
		}

		@Override
		public boolean enact() {
			if (event == null)
				return false;
			thread = new Thread(new Runnable() {

				@Override
				public void run() {
					event.enact();
					synchronized (thread) {
						thread.notifyAll();
					}
				}

			});
			thread.start();
			return true;
		}
		
		public boolean setEvent(PrioritizedEvent event) {
			if (thread.isAlive()) {
				return false;
			}
			this.event = event;
			return true;
		}

	}

}
