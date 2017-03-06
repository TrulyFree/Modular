package io.github.trulyfree.modular.action.handlers;

import io.github.trulyfree.modular.action.Action;

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
 * Handlerable interface. Any object to which a handler may be applied should
 * implement this interface, and the modular program should set the action
 * handler of the target object.
 * 
 * @author vtcakavsmoace
 */
public interface Handlerable {

	/**
	 * Method to call in order to set the event handler of the target
	 * Handerable. The Handlerable should submit all following actions to this
	 * action handler.
	 * 
	 * @param handler
	 *            The new action handler.
	 * @return success A boolean representing the success of the set operation.
	 */
	public <T extends Action> boolean setEventHandler(ActionHandler<T> handler);

	/**
	 * Method to call in order to get the current event handler.
	 * 
	 * @return handler The current event handler.
	 */
	public ActionHandler<? extends Action> getEventHandler();

}
