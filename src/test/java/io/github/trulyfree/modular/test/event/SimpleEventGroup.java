package io.github.trulyfree.modular.test.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.event.EventGroup;

public class SimpleEventGroup implements EventGroup<Event> {

	private List<Event> events;
	
	public SimpleEventGroup(List<Event> events) {
		this.events = events;
	}
	
	@Override
	public boolean enactNextEvent() {		
		return events.remove(0).enact();
	}

	@Override
	public int size() {
		return events.size();
	}

	@Override
	public Collection<Event> events() {
		Collection<Event> events = new ArrayList<Event>(size());
		for (Event event : this.events) {
			events.add(event);
		}
		return events;
	}

}
