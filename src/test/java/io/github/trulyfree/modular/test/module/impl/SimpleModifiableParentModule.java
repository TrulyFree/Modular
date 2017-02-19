package io.github.trulyfree.modular.test.module.impl;

import java.util.ArrayList;
import java.util.Collection;

import io.github.trulyfree.modular.module.ModifiableParentModule;
import io.github.trulyfree.modular.module.Module;

@SuppressWarnings("serial")
public class SimpleModifiableParentModule extends ArrayList<Module> implements ModifiableParentModule<Module> {

	@Override
	public Collection<Module> getChildren() {
		return this;
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
		clear();
		return true;
	}

	@Override
	public boolean addModule(Module module) {
		return this.add(module);
	}

	@Override
	public Module removeModule(Module module) {
		int index = this.indexOf(module);
		if (index == -1) {
			return null;
		} else {
			Module toReturn = this.get(index);
			this.remove(index);
			return toReturn;
		}
	}

}
