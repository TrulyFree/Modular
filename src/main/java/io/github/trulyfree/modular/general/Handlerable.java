package io.github.trulyfree.modular.general;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.event.EventHandler;

public interface Handlerable {

	public <T extends Event> boolean setEventHandler(EventHandler<T> handler);

	public EventHandler<? extends Event> getEventHandler();

}
