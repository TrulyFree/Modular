package io.github.trulyfree.modular.event;

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
 * PrioritizedEvent interface. All events with an assigned priority should
 * implement this interface. This interface provides all methods necessary for
 * comparing two events of separate priority, but further implementation is
 * permitted.
 * 
 * @author vtcakavsmoace
 *
 */
public interface PrioritizedEvent extends Event, Comparable<Event> {

	/**
	 * Method to be called in order to set the priority of this event.
	 * Generally, this should not be used after instantiation, but some
	 * implementations may find it valuable.
	 * 
	 * @param priority
	 *            The target priority level for the Event.
	 * @return success A boolean representing the success of the priority
	 *         setting operation.
	 */
	public boolean setPriority(EventPriority priority);

	/**
	 * Method to be called in order to check the priority of this event.
	 * 
	 * @return priority The current priority level of the Event.
	 */
	public EventPriority getPriority();

	@Override
	public default int compareTo(Event event) {
		if (event instanceof PrioritizedEvent) {
			return getPriority().compareTo(((PrioritizedEvent) event).getPriority());
		} else {
			return 1;
		}
	}

}