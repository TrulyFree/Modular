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
 * wish to use this handler type. Thread orientation is in the
 * "Producers-Distributor-Consumers" analogy; 1 to many independent threads may
 * produce actions for this action handler, non-blockingly. The distributor
 * thread will handle the distribution of the actions to the consumer threads,
 * blocking until actions are available to be distributed. The distributor will
 * distribute higher priority actions first. The consumers will then enact all
 * the submitted actions.
 * 
 * @author vtcakavsmoace
 *
 */
public class BackgroundPrioritizedActionHandler extends PrioritizedActionHandler implements Forkable {

	/**
	 * A boolean representing whether the lists are being interacted with. This
	 * prevents concurrent modification problems or repeatedly enacting the same
	 * action.
	 */
	private volatile boolean activeIO;

	/**
	 * The "distributor" in the analogy; this is the runnable associated with
	 * the distributor thread, which distributes actions to the consumers.
	 */
	private final ActionDistributor distributor;

	/**
	 * The thread which holds the distributor runnable.
	 */
	private final Thread distributorThread;

	/**
	 * The action to be enacted before the action handler is started. This will
	 * generally remain unused.
	 */
	private Action before;

	/**
	 * The action to be enacted after the action handler is halted. This will
	 * generally remain unused.
	 */
	private Action after;

	/**
	 * Standard constructor for BackgroundPrioritizedActionHandler.
	 * 
	 * @param maxthreads
	 *            An byte defining the maximum number of consumer threads.
	 */
	public BackgroundPrioritizedActionHandler(byte maxthreads) {
		distributor = new ActionDistributor(maxthreads);
		distributorThread = new Thread(distributor);
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
		if (distributorThread.isAlive()) {
			return false;
		}
		if (before != null)
			before.enact();
		distributorThread.start();
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
		if (distributorThread.isAlive()) {
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
		return distributorThread.isAlive() || super.isReady();
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
	 * io .github.trulyfree.modular.action.PrioritizedAction)
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
		distributor.safeHalt();
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
		distributor.immediateHalt();
		if (after != null)
			after.enact();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.github.trulyfree.modular.general.Forkable#setBefore(io.github.
	 * trulyfree.modular.action.Action)
	 */
	@Override
	public boolean setBefore(Action action) {
		if (distributorThread.isAlive()) {
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
	 * .modular.action.Action)
	 */
	@Override
	public boolean setAfter(Action action) {
		if (distributorThread.isAlive()) {
			return false;
		}
		this.after = action;
		return true;
	}

	/**
	 * Method to call in order to wait until IO becomes available again. If the
	 * thread is interrupted while waiting for IO, this method will return
	 * false.
	 * 
	 * @return success A boolean representing the success of waiting for IO.
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
	 * Method to call in order to notify the next add operation in the IO queue
	 * that they are clear to make an IO action.
	 */
	private void notifyForIO() {
		synchronized (this.lists) {
			this.lists.notify();
		}
	}

	/**
	 * ActionDistributor class. This class defines the properties of the action
	 * distributor which handles the distribution of actions to consumers.
	 * 
	 * @author vtcakavsmoace
	 */
	private class ActionDistributor implements Forkable, Runnable {

		/**
		 * The consumer pool, which actions are distributed to.
		 */
		private final ExecutorService pool;

		/**
		 * Standard ActionDistributor constructor.
		 * 
		 * @param maxthreads
		 *            A byte representing the number of consumer threads to use.
		 */
		public ActionDistributor(byte maxthreads) {
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
					assignAction(iter.next());
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
		 * Method to call in order to assign an action to a consumer.
		 * 
		 * @param next
		 *            The action to assign to a consumer.
		 */
		private void assignAction(final PrioritizedAction next) {
			if (!pool.isShutdown()) {
				Callable<Boolean> task = new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						boolean result = next.enact();
						synchronized (ActionDistributor.this) {
							ActionDistributor.this.notify();
						}
						return result;
					}
				};
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
		 * Method to call in order to wait for threads to terminate. This will
		 * wait a maximum of 100 milliseconds for threads to terminate.
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
