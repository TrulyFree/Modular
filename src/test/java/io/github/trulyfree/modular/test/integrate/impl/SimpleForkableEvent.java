package io.github.trulyfree.modular.test.integrate.impl;

import java.util.List;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.general.Forkable;
import io.github.trulyfree.modular.test.event.impl.SimpleEventGroup;
import io.github.trulyfree.modular.test.integrate.ForkableEventTest;

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

public class SimpleForkableEvent extends SimpleEventGroup<Event> implements Forkable, Event {

	private boolean halted;
	private Thread fork;

	public int value = 0;

	public SimpleForkableEvent(List<Event> events) {
		super(events);
		fork = new Thread(new Runnable() {

			@Override
			public void run() {
				for (;;) {
					if (!getHalted()) {
						enactNextEvent();
					} else {
						synchronized (SimpleForkableEvent.this) {
							SimpleForkableEvent.this.notify();
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
		ForkableEventTest.finished = true;
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean immediateHalt() throws Exception {
		fork.stop();
		return true;
	}

	@Override
	public boolean enact() {
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

}
