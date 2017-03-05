package io.github.trulyfree.modular.action;

import java.util.ArrayList;
import java.util.Collection;

import io.github.trulyfree.modular.action.Action;

/* Modular library by TrulyFree: A general-use Action-building library.
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
 * ModifiableActionGroup interface. Groups of Events which occur with relevancy
 * to each other and may alter in count should be packaged together in a
 * ModifiableActionGroup. Depending on the usage of EventHandlers, it may be
 * efficient to use an implementation shich also implements "Action", by which
 * the "enact" method loops throughout the Events contained within this Action
 * group and enacts them all. Such a method for ModifiableEventGroups is highly
 * suggested to be created with the "synchronous" keyword.
 * 
 * @author vtcakavsmoace
 *
 * @param <T>
 *            The type of Action supported by this ActionGroup.
 */
public interface ModifiableActionGroup<T extends Action> extends ActionGroup<T> {

	/**
	 * Method to be called in order to add an Action to this ActionGroup.
	 * Implementations which extend existing List implementations should find
	 * this method to be a single call.
	 * 
	 * @param event
	 *            The Action to add to the ActionGroup.
	 * @return success A boolean representing the success of the add operation.
	 *         Note that if a throwable is thrown by this method, it should be
	 *         caught and the method return false.
	 */
	public boolean addEvent(T event);

	/**
	 * Method to be called in order to remove an Action from this ActionGroup.
	 * Implementations which extend existing List implementations should find
	 * this method to be very simple to implement.
	 * 
	 * @param event
	 *            The Action to remove from the ActionGroup.
	 * @return removed The Action removed by this operation.
	 */
	public T removeEvent(T event);

	/**
	 * Method to be called in order to remove Events of a specific type from
	 * this ActionGroup. A suggested implementation has been provided, but
	 * implementations may differ on the approach chosen.
	 * 
	 * @param type
	 *            The type of Action to remove. Note that this should respect
	 *            polymorphism.
	 * @return removed The Action(s) removed by this operation. If no events are
	 *         removed, this should return an empty collection of Events.
	 */
	public default Collection<T> removeEventByType(Class<? extends Action> type) {
		Collection<T> toReturn = new ArrayList<T>(this.getEvents().size());
		for (T event : this.getEvents()) {
			if (type.isInstance(event)) {
				toReturn.add(this.removeEvent(event));
			}
		}
		return toReturn;
	}

	/**
	 * Method to be called in order to remove all Events from this ActionGroup. A
	 * suggested implementation has been provided. Implementations may differ
	 * according to need.
	 */
	public default void clear() {
		for (T event : this.getEvents()) {
			this.removeEvent(event);
		}
	}

}
