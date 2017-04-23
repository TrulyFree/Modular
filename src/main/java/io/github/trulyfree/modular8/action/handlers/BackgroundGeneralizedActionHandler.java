package io.github.trulyfree.modular8.action.handlers;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.github.trulyfree.modular8.action.Action;

/* Modular8 library by TrulyFree: A general-use module-building library.
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

public class BackgroundGeneralizedActionHandler extends GeneralizedActionHandler {

	/**
	 * Executor pool which will execute all actions handled by this handler.
	 */
	private ExecutorService pool;

	/**
	 * Number of threads within the pool. This is held to prevent adding actions
	 * which may later be removed.
	 */
	private final byte threads;

	/**
	 * Standard constructor for the BackgroundGeneralizedActionHandler class.
	 * 
	 * @param threads
	 *            Number of threads to dedicate to the executor pool.
	 */
	public BackgroundGeneralizedActionHandler(byte threads) {
		this.threads = threads;
	}

	public synchronized boolean addAction(Action action) {
		boolean toReturn = super.addAction(action);
		this.notify();
		return toReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular6.action.handlers.GeneralizedActionHandler#
	 * setup()
	 */
	public boolean setup() {
		super.setup();
		this.pool = Executors.newFixedThreadPool(threads);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular6.action.handlers.GeneralizedActionHandler#
	 * isReady()
	 */
	public boolean isReady() {
		return super.isReady() && this.pool != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular6.action.handlers.GeneralizedActionHandler#
	 * destroy()
	 */
	public boolean destroy() {
		super.destroy();
		pool.shutdownNow();
		try {
			return pool.awaitTermination(0, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.trulyfree.modular6.action.handlers.GeneralizedActionHandler#
	 * enact()
	 */
	@Override
	public boolean enact() {
		ArrayList<Callable<Boolean>> submitted = new ArrayList<Callable<Boolean>>(threads);
		for (int i = 0; i < threads; i++) {
			submitted.add(getCallable());
		}
		for (Callable<Boolean> toSubmit : submitted) {
			pool.submit(toSubmit);
		}
		return true;
	}

	/**
	 * Generates a callable instance that may be used by the executor pool from
	 * an action.
	 * 
	 * @param action
	 *            The action to be performed by the executor pool.
	 * @return callable The callable instance to be submitted to the executor
	 *         pool.
	 */
	private Callable<Boolean> getCallable() {
		Callable<Boolean> task = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				while (!pool.isShutdown()) {
					while (BackgroundGeneralizedActionHandler.this.list.isEmpty()) {
						synchronized (BackgroundGeneralizedActionHandler.this) {
							try {
								BackgroundGeneralizedActionHandler.this.wait();
							} catch (InterruptedException e) {
								return true;
							}
						}
					}
					enactNextAction();
				}
				return true;
			}
		};
		return task;
	}

}
