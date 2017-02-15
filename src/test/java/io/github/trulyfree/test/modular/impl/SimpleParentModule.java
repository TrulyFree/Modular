package io.github.trulyfree.test.modular.impl;

import java.util.ArrayList;
import java.util.List;

import io.github.trulyfree.modular.module.ParentModule;

public class SimpleParentModule extends SimpleModule implements ParentModule<SimpleModule> {

	public static List<SimpleModule> children;
	
	@Override
	public boolean setup() {
		children = new ArrayList<SimpleModule>();
		super.setup();
		return true;
	}

	@Override
	public boolean destroy() {
		children = null;
		super.destroy();
		return true;
	}

	@Override
	public List<SimpleModule> getChildren() {
		return children;
	}

}
