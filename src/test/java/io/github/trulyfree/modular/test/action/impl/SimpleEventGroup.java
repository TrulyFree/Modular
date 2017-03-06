package io.github.trulyfree.modular.test.action.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.trulyfree.modular.action.Action;
import io.github.trulyfree.modular.action.ActionGroup;

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

public class SimpleEventGroup<T extends Action> implements ActionGroup<T> {

	private List<T> events;
	
	private int current;

	public SimpleEventGroup(List<T> events) {
		this.events = events;
		current = 0;
	}

	@Override
	public boolean enactNextEvent() {
		return events.get(next()).enact();
	}

	@Override
	public int size() {
		return events.size();
	}

	@Override
	public Collection<T> getActions() {
		Collection<T> events = new ArrayList<T>(size());
		for (T event : this.events) {
			events.add(event);
		}
		return events;
	}
	
	private int next() {
		final int intermediary = current;
		current++;
		current %= this.size();
		return intermediary;
	}

}
