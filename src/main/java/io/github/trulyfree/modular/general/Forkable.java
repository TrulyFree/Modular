package io.github.trulyfree.modular.general;

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
 * Forkable interface. All modules or events which will run strictly on separate
 * threads should implement this interface. It is highly suggested that Forkable
 * Event implementations be an EventGroup in order to have an easy
 * implementation for safeHalt. Forkable Cancellable Events should not end
 * execution after being enacted if they are set to cancelled mid-execution.
 * 
 * @author vtcakavsmoace
 *
 */
public interface Forkable {

	/**
	 * Method to be called in order to safely halt the Forkable instance at the
	 * next convienient process interval. It is suggested that Forkable Event
	 * implementations be EventGroups such that the safeHalt method cause the
	 * Forkable instance to halt between events, where events would represent a
	 * "single action". Consider: <code>if (!halted) enactNextEvent();</code>
	 * 
	 * @return success A boolean representing whether or not the halting
	 *         operation succeeded. This should always halt the current thread
	 *         until the forked thread is suspended. False should only be
	 *         returned if an throwable is thrown within this method, which the
	 *         method should catch.
	 */
	public boolean safeHalt();

	/**
	 * Method to be called in order to immediately halt the Forkable instance,
	 * regardless of its current operation. This method is inherently unsafe,
	 * and should not be called except in situations of DIRE or MAX priority.
	 * 
	 * @return success A boolean representing whether or not the halting
	 *         operation succeeded. This should only return false if an
	 *         throwable is thrown before the forked operation is halted, in
	 *         which case it should be caught and the method return false.
	 * @throws Exception
	 *             Any exception thrown by the halting process itself should NOT
	 *             be caught and instead should be transferred to the method
	 *             which calls this.
	 */
	public boolean immediateHalt() throws Exception;

	/**
	 * Method to be called in order to set the action to occur before the
	 * forkable is forked. This should not be changed while the forkable is
	 * forked, and if there is an attempt to call this method while the fork is
	 * active, no action should occur and the method should return false.
	 * 
	 * @return success A boolean representing whether or not the before event
	 *         was set.
	 */
	public boolean setBefore(Event event);

	/**
	 * Method to be called in order to set the action to occur after the
	 * forkable is forked. This should not be changed while the forkable is
	 * forked, and if there is an attempt to call this method while the fork is
	 * active, no action should occur and the method should return false.
	 * 
	 * @return success A boolean representing whether or not the after event was
	 *         set.
	 */
	public boolean setAfter(Event event);

}
