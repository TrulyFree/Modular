package io.github.trulyfree.modular.test.module.impl;

import java.util.ArrayList;
import java.util.Collection;

import io.github.trulyfree.modular.module.ModifiableParentModule;
import io.github.trulyfree.modular.module.Module;

@SuppressWarnings("serial")
public class SimpleModifiableParentModule<T extends Module> extends ArrayList<T> implements ModifiableParentModule<T> {

	private boolean ready;
	
	@Override
	public Collection<T> getChildren() {
		return new ArrayList<T>(this);
	}

	@Override
	public boolean setup() {
		ready = true;
		return true;
	}

	@Override
	public boolean isReady() {
		return ready;
	}

	@Override
	public boolean destroy() {
		clear();
		ready = false;
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
	
	@Override
	public void clear() {
		removeModuleByType(Module.class); // Force ModifiableParentModule implementation
	}

}
