package io.github.trulyfree.modular.display;

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
 * Displayable interface. Anonymous implementations of this interface are
 * strongly recommended against, as the parameter of target Displays should be a
 * specific implementation of this interface. Developers should provide a
 * library for their Modular project which provides an implementation of this
 * interface that third-party developers may extend to create Displayables which
 * support the project.
 * 
 * @author vtcakavsmoace
 *
 */
public interface Displayable extends Comparable<Displayable> {

	/**
	 * Method to be called in order to get the target priority of this
	 * displayable. This priority should determine whether or not this
	 * displayable will be suppressed should it throw a DisplayableException
	 * during rendering. If the priority returned is lower than the priority
	 * threshold of the DisplayableModule, it will be suppressed, otherwise a
	 * DisplayableException will be thrown (uncaught).
	 * 
	 * @return priority The priority level of this Displayable.
	 */
	public Priority getPriority();

	/**
	 * Method to be called in order to set the target priority of this
	 * displayable. This will likely be unused, but some implementations may
	 * have variable priorities according to different environments.
	 * 
	 * @param priority
	 *            The new priority level of this Displayable.
	 * @return success A boolean representing the success or failure of this
	 *         operation.
	 */
	public boolean setPriority(Priority priority);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public default int compareTo(Displayable displayable) {
		return this.getPriority().compareTo(displayable.getPriority());
	}

}
