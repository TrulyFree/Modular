package io.github.trulyfree.modular.test.module.impl;

import java.util.ArrayList;
import java.util.Collection;

import io.github.trulyfree.modular.module.ModifiableParentModule;
import io.github.trulyfree.modular.module.Module;

@SuppressWarnings("serial")
public class SimpleModifiableParentModule<T extends Module> extends ArrayList<T> implements ModifiableParentModule<T> {

	@Override
	public Collection<T> getChildren() {
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
	public boolean addModule(T module) {
		return this.add(module);
	}

	@Override
	public T removeModule(Module module) {
		int index = this.indexOf(module);
		if (index == -1) {
			return null;
		} else {
			T toReturn = this.get(index);
			this.remove(index);
			return toReturn;
		}
	}

}
