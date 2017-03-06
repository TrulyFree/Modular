package io.github.trulyfree.modular.general;

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
 * Cancellable interface. All cancellable actions or modules should implement
 * this interface. It is highly recommended that all implementations of this
 * class prevent any form of interaction with the instance while the instance is
 * in the cancelled state.
 * 
 * @author vtcakavsmoace
 *
 */
public interface Cancellable {

	/**
	 * Method to be called in order to set the cancelled state of this instance.
	 * Should be called with cancelled set to true to cancel the instance, and
	 * false as not cancelled.
	 * 
	 * @param cancelled
	 *            A boolean representing the cancelled state of this instance.
	 * @return success A boolean representing the success of this operation.
	 *         This has essentially no reason to be false.
	 */
	public boolean setCancelled(boolean cancelled);

	/**
	 * Method to be called in order to check the cancelled state of this
	 * instance. Should return true if the instance is cancelled, false if
	 * otherwise.
	 * 
	 * @return cancelled A boolean representing the cancelled state of this
	 *         instance.
	 */
	public boolean isCancelled();

}
