package io.github.trulyfree.modular.test.event.handler.impl;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.event.handlers.EventHandler;
import io.github.trulyfree.modular.event.handlers.Handlerable;
import io.github.trulyfree.modular.module.Module;

public class SimpleHandlerableModule implements Module, Handlerable {

	private EventHandler<? extends Event> handler;

	@Override
	public <T extends Event> boolean setEventHandler(EventHandler<T> handler) {
		this.handler = handler;
		return true;
	}

	@Override
	public EventHandler<? extends Event> getEventHandler() {
		return handler;
	}

	@Override
	public boolean setup() {
		return true;
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public boolean destroy() {
		return true;
	}

}
