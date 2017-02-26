package io.github.trulyfree.modular.test.integrate.impl;

import java.util.List;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.general.Forkable;
import io.github.trulyfree.modular.test.event.impl.SimpleEventGroup;
import io.github.trulyfree.modular.test.integrate.ForkableEventTest;

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
