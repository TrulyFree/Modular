package io.github.trulyfree.modular.event;

public interface PrioritizedEvent extends Event {

	public EventPriority getPriority();
	
}
