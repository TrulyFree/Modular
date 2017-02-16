package io.github.trulyfree.modular.test.event;

import io.github.trulyfree.modular.event.EventPriority;
import io.github.trulyfree.modular.event.PrioritizedEvent;

public abstract class SimplePrioritizedEvent implements PrioritizedEvent {
	
	private EventPriority priority;
	
	public SimplePrioritizedEvent(EventPriority priority) {
		setPriority(priority);
	}

	@Override
	public boolean setPriority(EventPriority priority) {
		this.priority = priority;
		return true;
	}

	@Override
	public EventPriority getPriority() {
		return priority;
	}

}
