package io.github.trulyfree.modular.test.event.impl;

import java.util.Collection;

import io.github.trulyfree.modular.event.ModifiableEventGroup;
import io.github.trulyfree.modular.event.Event;

public class SimpleModifiableEventGroup<T extends Event> implements ModifiableEventGroup<T> {

	private int current;
		
	public SimpleModifiableEventGroup() {
		current = 0;
	}
	
	@Override
	public boolean enactNextEvent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<T> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addEvent(T event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T removeEvent(T event) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private int next() {
		final int intermediary = current;
		current++;
		current %= this.size();
		return intermediary;
	}

}
