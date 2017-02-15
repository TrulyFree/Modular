package io.github.trulyfree.test.modular.impl;

import io.github.trulyfree.modular.module.Module;

public class SimpleModule implements Module {

	public static int someValue;
	
	@Override
	public boolean setup() {
		someValue = 1;
		return true;
	}

	@Override
	public boolean destroy() {
		someValue = 0;
		return true;
	}
	
}
