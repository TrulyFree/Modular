package io.github.trulyfree.modular.event;

import java.util.ArrayList;
import java.util.Collection;

import io.github.trulyfree.modular.event.Event;

/* Modular library by TrulyFree: A general-use Event-building library.
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

public interface ModifiableEventGroup<T extends Event> extends EventGroup<T> {

	public boolean addEvent(T event);

	public T removeEvent(T event);

	public default Collection<T> removeEventByType(Class<? extends Event> type) {
		Collection<T> toReturn = new ArrayList<T>(this.getEvents().size());
		for (T event : this.getEvents()) {
			if (type.isInstance(event)) {
				toReturn.add(this.removeEvent(event));
			}
		}
		return toReturn;
	}

	public default void clear() {
		removeEventByType(Event.class);
	}

}
