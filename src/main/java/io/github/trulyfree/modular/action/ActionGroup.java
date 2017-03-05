package io.github.trulyfree.modular.action;

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
 * ActionGroup interface. All events which are related in some way should be
 * packaged in an ActionGroup. Depending on the usage of EventHandlers, it may be
 * efficient to use an implementation which also implements "Action", by which
 * the "enact" method loops throughout the events contained within this event
 * group and enacts them all.
 * 
 * @author vtcakavsmoace
 *
 * @param <T>
 *            The type of Action supported by this ActionGroup.
 */
public interface ActionGroup<T extends Action> {

	/**
	 * Method to be called in order to enact the next Action contained by this
	 * ActionGroup. This method should be preferred over iterating through the
	 * children and executing them individually in an external class.
	 * 
	 * @return success A boolean representing the success of the event enacted.
	 */
	public boolean enactNextEvent();

	/**
	 * Method to be called in order to check the number of events held by this
	 * ActionGroup in total, including those already enacted.
	 * 
	 * @return size The number of events held by this ActionGroup.
	 */
	public int size();

	/**
	 * Method to be called in order to retrieve the events held by this
	 * ActionGroup, including those already enacted. The collection returned
	 * should NOT be capable of modifying the ActionGroup's internal Action
	 * collection. If the ActionGroup is intended to be modifiable, the user
	 * should implement the ModifiableActionGroup interface.
	 * 
	 * @return events The events of this ActionGroup. Note that modifications to
	 *         this collection should NOT modify the events of the ActionGroup.
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
