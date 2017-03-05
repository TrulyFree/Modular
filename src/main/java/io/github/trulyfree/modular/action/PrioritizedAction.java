package io.github.trulyfree.modular.action;

import io.github.trulyfree.modular.general.Priority;

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
 * PrioritizedAction interface. All events with an assigned priority should
 * implement this interface. This interface provides all methods necessary for
 * comparing two events of separate priority, but further implementation is
 * permitted.
 * 
 * @author vtcakavsmoace
 *
 */
public interface PrioritizedAction extends Action, Comparable<Action> {

	/**
	 * Method to be called in order to check the priority of this event.
	 * 
	 * @return priority The current priority level of the Action.
	 */
	public Priority getPriority();

	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public default int compareTo(Action action) {
		if (action instanceof PrioritizedAction) {
			return getPriority().compareTo(((PrioritizedAction) action).getPriority());
		} else {
			return 1;
		}
	}

}
