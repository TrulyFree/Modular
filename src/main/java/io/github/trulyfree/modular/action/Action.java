package io.github.trulyfree.modular.action;

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
 * Action main interface. All operations which do not need to be enacted
 * immediately should be a simple implementation of this interface, and should
 * be handled by a ActionHandler.
 * 
 * @author vtcakavsmoace
 *
 */
public interface Action {

	/**
	 * Method to be called in order to enact the operations desired. Think of
	 * this method as synonymous with Runnable's "run", except that this returns
	 * a boolean if the operation is successful. This method should not cause
	 * any throwables to be thrown. Instead, all throwables should be handled
	 * within this method. If the event is cancelled, throws a fatal throwable,
	 * or is for any other reason not successfully completed, it should catch
	 * any thrown throwables and return false.
	 * 
	 * @return success The success of this operation. If the event fails to be
	 *         enacted for any reason, this should return false.
	 */
	public boolean enact();

}
