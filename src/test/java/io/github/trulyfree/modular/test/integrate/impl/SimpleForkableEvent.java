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

	private volatile boolean halted;
	private volatile boolean running;
	private Thread fork;
	
	private volatile Boolean alteringBefore;
	private volatile Boolean alteringAfter;

	private Event before;
	private Event after;

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
		after.enact();
		running = false;
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean immediateHalt() throws Exception {
		fork.stop();
		after.enact();
		running = false;
		return true;
	}

	@Override
	public boolean enact() {
		running = true;
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
	public boolean setBefore(Event event) {
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
		this.before = event;
		
		synchronized (alteringBefore) {
			alteringBefore.notifyAll();
		}
		return true;
	}

	@Override
	public boolean setAfter(Event event) {
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
		this.after = event;
		
		synchronized (alteringAfter) {
			alteringAfter.notifyAll();
		}
		return true;
	}

}
