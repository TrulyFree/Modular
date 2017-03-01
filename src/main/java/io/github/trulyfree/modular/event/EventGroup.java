package io.github.trulyfree.modular.event;

import java.util.Collection;

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
 * EventGroup interface. All events which are related in some way should be
 * packaged in an EventGroup. Depending on the usage of EventHandlers, it may be
 * efficient to use an implementation which also implements "Event", by which
 * the "enact" method loops throughout the events contained within this event
 * group and enacts them all.
 * 
 * @author vtcakavsmoace
 *
 * @param <T>
 *            The type of Event supported by this EventGroup.
 */
public interface EventGroup<T extends Event> {

	/**
	 * Method to be called in order to enact the next Event contained by this
	 * EventGroup. This method should be preferred over iterating through the
	 * children and executing them individually in an external class.
	 * 
	 * @return success A boolean representing the success of the event enacted.
	 */
	public boolean enactNextEvent();

	/**
	 * Method to be called in order to check the number of events held by this
	 * EventGroup in total, including those already enacted.
	 * 
	 * @return size The number of events held by this EventGroup.
	 */
	public int size();

	/**
	 * Method to be called in order to retrieve the events held by this
	 * EventGroup, including those already enacted. The collection returned
	 * should NOT be capable of modifying the EventGroup's internal Event
	 * collection. If the EventGroup is intended to be modifiable, the user
	 * should implement the ModifiableEventGroup interface.
	 * 
	 * @return events The events of this EventGroup. Note that modifications to
	 *         this collection should NOT modify the events of the EventGroup.
	 */
	public Collection<T> getEvents();
	
	public default void enactAllOfType(Class<? extends T> type) {
		for (T event : getEvents()) {
			if (type.isInstance(event)) {
				event.enact();
			}
		}
	}

	public default void enactAll() {
		for (T event : getEvents()) {
			event.enact();
		}
	}

}
