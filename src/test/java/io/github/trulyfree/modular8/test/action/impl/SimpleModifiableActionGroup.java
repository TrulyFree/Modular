package io.github.trulyfree.modular8.test.action.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.trulyfree.modular8.action.Action;
import io.github.trulyfree.modular8.action.ModifiableActionGroup;

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

public class SimpleModifiableActionGroup<T extends Action> implements ModifiableActionGroup<T> {

	private int current;

	private List<T> actions;

	public SimpleModifiableActionGroup() {
		current = 0;
		actions = new ArrayList<T>();
	}

	@Override
	public synchronized boolean enactNextAction() {
		return actions.get(next()).enact();
	}

	@Override
	public synchronized int size() {
		return actions.size();
	}

	@Override
	public synchronized Collection<T> getActions() {
		Collection<T> actions = new ArrayList<T>(size());
		for (T action : this.actions) {
			actions.add(action);
		}
		return actions;
	}

	@Override
	public synchronized boolean addAction(T action) {
		return actions.add(action);
	}

	@Override
	public synchronized boolean removeAction(T action) {
		return actions.remove(action);
	}

	@Override
	public synchronized void enactAllOfType(Class<? extends T> type) {
		for (T action : actions) {
			if (type.isInstance(action)) {
				action.enact();
			}
		}
	}

	@Override
	public synchronized void enactAll() {
		for (T action : actions) {
			action.enact();
		}
	}

	private int next() {
		final int intermediary = current;
		current++;
		current %= this.size();
		return intermediary;
	}

}
