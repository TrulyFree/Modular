package io.github.trulyfree.modular.test.module.impl;

import java.util.ArrayList;
import java.util.List;

import io.github.trulyfree.modular.module.ParentModule;

/* Modular library by TrulyFree: A general-use module-building library.
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

public class SimpleParentModule extends SimpleModule implements ParentModule<SimpleModule> {

	public SimpleParentModule(int value) {
		super(value);
	}

	public static ArrayList<SimpleModule> children;

	@Override
	public boolean setup() {
		children = new ArrayList<SimpleModule>();
		children.add(new SimpleModule(1));
		children.add(new SimpleModule(2));
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
		return new ArrayList<SimpleModule>(children);
	}

}
