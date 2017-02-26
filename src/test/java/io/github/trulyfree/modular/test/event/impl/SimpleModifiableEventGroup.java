package io.github.trulyfree.modular.test.event.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.trulyfree.modular.event.ModifiableEventGroup;
import io.github.trulyfree.modular.event.Event;

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

public class SimpleModifiableEventGroup<T extends Event> implements ModifiableEventGroup<T> {

	private int current;
	
	private List<T> events;
	
	public SimpleModifiableEventGroup() {
		current = 0;
		events = new ArrayList<T>();
	}
	
	@Override
	public synchronized boolean enactNextEvent() {
		return events.get(next()).enact();
	}

	@Override
	public synchronized int size() {
		return events.size();
	}

	@Override
	public synchronized Collection<T> getEvents() {
		Collection<T> events = new ArrayList<T>(size());
		for (T event : this.events) {
			events.add(event);
		}
		return events;
	}

	@Override
	public synchronized boolean addEvent(T event) {
		return events.add(event);
	}

	@Override
	public synchronized T removeEvent(T event) {
		int index = events.indexOf(event);
		if (index == -1) {
			return null;
		} else {
			T toReturn = events.get(index);
			events.remove(index);
			return toReturn;
		}
	}

	@Override
	public synchronized void enactAllOfType(Class<? extends T> type) {
		for (T event : events) {
			if (type.isInstance(event)) {
				event.enact();
			}
		}
	}

	@Override
	public synchronized void enactAll() {
		for (T event : events) {
			event.enact();
		}
	}
	
	private int next() {
		final int intermediary = current;
		current++;
		current %= this.size();
		return intermediary;
	}

}
