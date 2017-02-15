package io.github.trulyfree.modular.event;

public abstract class EventHandler {
	
	private final DependentEventGroup deg;
	
	public EventHandler(DependentEventGroup deg) {
		this.deg = deg;
	}

}
