package io.github.trulyfree.modular.test.action.handler.impl;

import io.github.trulyfree.modular.action.Action;
import io.github.trulyfree.modular.action.handlers.ActionHandler;
import io.github.trulyfree.modular.action.handlers.Handlerable;
import io.github.trulyfree.modular.module.Module;

public class SimpleHandlerableModule implements Module, Handlerable {

	private ActionHandler<? extends Action> handler;

	@Override
	public <T extends Action> boolean setEventHandler(ActionHandler<T> handler) {
		this.handler = handler;
		return true;
	}

	@Override
	public ActionHandler<? extends Action> getEventHandler() {
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
