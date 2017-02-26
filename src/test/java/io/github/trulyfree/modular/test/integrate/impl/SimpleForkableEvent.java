package io.github.trulyfree.modular.test.integrate.impl;

import java.util.List;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.general.Forkable;
import io.github.trulyfree.modular.test.event.impl.SimpleEventGroup;

public class SimpleForkableEvent extends SimpleEventGroup<Event> implements Forkable, Event {

	private boolean halted;
	private Thread fork;

	public SimpleForkableEvent(List<Event> events) {
		super(events);
	}

	@Override
	public boolean safeHalt() {
		return halted = true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean immediateHalt() throws Exception {
		fork.stop();
		return true;
	}

	@Override
	public boolean enact() {
		fork = new Thread(new Runnable() {

			@Override
			public void run() {
				if (!getHalted()) {
					enactNextEvent();
				}
			}

		});
		return true;
	}

	private boolean getHalted() {
		return halted;
	}

}
