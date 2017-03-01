package io.github.trulyfree.modular.event.handlers;

import java.util.ArrayList;
import java.util.Collection;

import io.github.trulyfree.modular.event.Event;

public class GeneralizedEventHandler implements EventHandler<Event> {

	private ArrayList<Event> list;

	@Override
	public boolean addEvent(Event event) {
		if (event == null) {
			return false;
		}
		return list.add(event);
	}

	@Override
	public Event removeEvent(Event event) {
		int index = list.indexOf(event);
		if (index >= 0) {
			return list.remove(index);
		}
		return null;
	}

	@Override
	public boolean enactNextEvent() {
		if (!list.isEmpty()) {
			return list.remove(0).enact();
		}
		return false;
	}

	@Override
	public Collection<Event> getEvents() {
		return new ArrayList<Event>(list);
	}

	@Override
	public boolean setup() {
		list = new ArrayList<Event>();
		return true;
	}

	@Override
	public boolean isReady() {
		return list != null;
	}

	@Override
	public boolean destroy() {
		list = null;
		return true;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean enact() {
		while (list.size() > 0) {
			list.remove(0).enact();
		}
		return true;
	}

}
