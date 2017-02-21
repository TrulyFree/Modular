package io.github.trulyfree.modular.test.event.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.trulyfree.modular.event.ModifiableEventGroup;
import io.github.trulyfree.modular.event.Event;

public class SimpleModifiableEventGroup<T extends Event> implements ModifiableEventGroup<T> {

	private int current;
	
	private List<T> events;
	
	public SimpleModifiableEventGroup() {
		current = 0;
		events = new ArrayList<T>();
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
	public Collection<T> getEvents() {
		Collection<T> events = new ArrayList<T>(size());
		for (T event : this.events) {
			events.add(event);
		}
		return events;
	}

	@Override
	public boolean addEvent(T event) {
		return events.add(event);
	}

	@Override
	public T removeEvent(T event) {
		int index = events.indexOf(event);
		if (index == -1) {
			return null;
		} else {
			T toReturn = events.get(index);
			events.remove(index);
			return toReturn;
		}
	}
	
	private int next() {
		final int intermediary = current;
		current++;
		current %= this.size();
		return intermediary;
	}

}
