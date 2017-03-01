package io.github.trulyfree.modular.event.handlers;

import io.github.trulyfree.modular.event.Event;

public interface Handlerable {

	public <T extends Event> boolean setEventHandler(EventHandler<T> handler);

	public EventHandler<? extends Event> getEventHandler();

}
