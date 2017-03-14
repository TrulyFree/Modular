package io.github.trulyfree.modular8.test.action.handlers.impl;

import io.github.trulyfree.modular8.action.Action;
import io.github.trulyfree.modular8.action.handlers.ActionHandler;
import io.github.trulyfree.modular8.action.handlers.Handlerable;
import io.github.trulyfree.modular8.module.Module;

public class SimpleHandlerableModule implements Module, Handlerable {

	private ActionHandler<? extends Action> handler;

	@Override
	public <T extends Action> boolean setActionHandler(ActionHandler<T> handler) {
		this.handler = handler;
		return true;
	}

	@Override
	public ActionHandler<? extends Action> getActionHandler() {
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
