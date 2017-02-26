package io.github.trulyfree.modular.test.display.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.trulyfree.modular.display.DisplayableModule;
import io.github.trulyfree.modular.general.Priority;

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

public class SimpleDisplayableModule implements DisplayableModule<SimpleDisplayable> {

	List<SimpleDisplayable> displayables;
	
	private Priority threshold;
	
	private boolean setup;
	
	public SimpleDisplayableModule(Collection<? extends SimpleDisplayable> displayables) {
		this.displayables = new ArrayList<SimpleDisplayable>(displayables);
	}

	@Override
	public boolean setup() {
		return true;
	}

	@Override
	public boolean isReady() {
		return setup && (displayables != null);
	}

	@Override
	public boolean destroy() {
		setup = false;
		return true;
	}

	@Override
	public List<SimpleDisplayable> getDisplayables() {
		return new ArrayList<SimpleDisplayable>(displayables);
	}

	@Override
	public boolean setPriorityThreshold(Priority priority) {
		threshold = priority;
		return true;
	}

	@Override
	public Priority getPriorityThreshold() {
		return threshold;
	}

}
