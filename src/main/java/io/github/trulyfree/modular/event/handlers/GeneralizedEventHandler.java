package io.github.trulyfree.modular.event.handlers;

import java.util.ArrayList;
import java.util.Collection;

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

/**
 * @author vtcakavsmoace
 *
 */
public class GeneralizedEventHandler implements EventHandler<Event> {

	/**
	 * 
	 */
	private ArrayList<Event> list;

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.event.ModifiableEventGroup#addEvent(io.github.trulyfree.modular.event.Event)
	 */
	@Override
	public boolean addEvent(Event event) {
		if (event == null) {
			return false;
		}
		return list.add(event);
	}

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.event.ModifiableEventGroup#removeEvent(io.github.trulyfree.modular.event.Event)
	 */
	@Override
	public Event removeEvent(Event event) {
		int index = list.indexOf(event);
		if (index >= 0) {
			return list.remove(index);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.event.EventGroup#enactNextEvent()
	 */
	@Override
	public boolean enactNextEvent() {
		if (!list.isEmpty()) {
			return list.remove(0).enact();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.event.EventGroup#getEvents()
	 */
	@Override
	public Collection<Event> getEvents() {
		return new ArrayList<Event>(list);
	}

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.module.Module#setup()
	 */
	@Override
	public boolean setup() {
		list = new ArrayList<Event>();
		return true;
	}

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.module.Module#isReady()
	 */
	@Override
	public boolean isReady() {
		return list != null;
	}

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.module.Module#destroy()
	 */
	@Override
	public boolean destroy() {
		list = null;
		return true;
	}

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.event.EventGroup#size()
	 */
	@Override
	public int size() {
		return list.size();
	}

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.event.ModifiableEventGroup#clear()
	 */
	@Override
	public void clear() {
		list.clear();
	}

	/* (non-Javadoc)
	 * @see io.github.trulyfree.modular.event.Event#enact()
	 */
	@Override
	public boolean enact() {
		while (!list.isEmpty()) {
			enactNextEvent();
		}
		return true;
	}

}
