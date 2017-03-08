package io.github.trulyfree.modular8.test.integrate.impl;

import java.util.List;

import io.github.trulyfree.modular8.action.Action;
import io.github.trulyfree.modular8.general.Forkable;
import io.github.trulyfree.modular8.test.action.impl.SimpleActionGroup;
import io.github.trulyfree.modular8.test.integrate.ForkableActionTest;

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

public class SimpleForkableAction extends SimpleActionGroup<Action> implements Forkable, Action {

	private volatile boolean halted;
	private volatile boolean running;
	private Thread fork;

	private volatile Boolean alteringBefore;
	private volatile Boolean alteringAfter;

	private Action before;
	private Action after;

	public int value = 0;

	public SimpleForkableAction(List<Action> actions) {
		super(actions);
		fork = new Thread(new Runnable() {

			@Override
			public void run() {
				for (;;) {
					if (!getHalted()) {
						enactNextAction();
					} else {
						synchronized (SimpleForkableAction.this) {
							SimpleForkableAction.this.notify();
						}
						break;
					}
				}
			}

		});
	}

	@Override
	public synchronized boolean safeHalt() {
		try {
			halted = true;
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ForkableActionTest.finished = true;
		if (after != null)
			after.enact();
		running = false;
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean immediateHalt() throws Exception {
		fork.stop();
		if (after != null)
			after.enact();
		running = false;
		return true;
	}

	@Override
	public boolean enact() {
		running = true;
		if (before != null)
			before.enact();
		fork.start();
		return true;
	}

	public Thread getFork() {
		return fork;
	}

	private boolean getHalted() {
		return halted;
	}

	public synchronized void increment() {
		value++;
		if (value > 10000) {
			notifyAll();
		}
	}

	@Override
	public boolean setBefore(Action action) {
		synchronized (alteringBefore) {
			while (alteringBefore) {
				try {
					alteringBefore.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		alteringBefore = true;
		if (running) {
			return false;
		}
		this.before = action;

		synchronized (alteringBefore) {
			alteringBefore.notifyAll();
		}
		return true;
	}

	@Override
	public boolean setAfter(Action action) {
		synchronized (alteringAfter) {
			while (alteringAfter) {
				try {
					alteringAfter.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		alteringAfter = true;
		if (running) {
			return false;
		}
		this.after = action;

		synchronized (alteringAfter) {
			alteringAfter.notifyAll();
		}
		return true;
	}

}
