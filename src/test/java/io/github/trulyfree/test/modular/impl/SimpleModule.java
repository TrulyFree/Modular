package io.github.trulyfree.test.modular.impl;

import io.github.trulyfree.modular.module.Module;

public class SimpleModule implements Module {

	public static int someValue;
	
	private boolean ready;
	
	@Override
	public boolean setup() {
		someValue = 1;
		ready = true;
		return true;
	}

	@Override
	public boolean isReady() {
		return ready;
	}

	@Override
	public boolean destroy() {
		someValue = 0;
		ready = false;
		return true;
	}
}
