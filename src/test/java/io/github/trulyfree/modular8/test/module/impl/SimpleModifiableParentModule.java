package io.github.trulyfree.modular8.test.module.impl;

import java.util.ArrayList;
import java.util.List;

import io.github.trulyfree.modular8.module.ModifiableParentModule;
import io.github.trulyfree.modular8.module.Module;

/* Modular8 library by TrulyFree: A general-use module-building library.
 * Copyright (C) 2016  VTCAKAVSMoACE
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

@SuppressWarnings("serial")
public class SimpleModifiableParentModule<T extends Module> extends ArrayList<T> implements ModifiableParentModule<T> {

	private boolean ready;

	@Override
	public List<T> getChildren() {
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
		for (T module : this.getChildren()) {
			this.removeModule(module);
		}
	}

}
