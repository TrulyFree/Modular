package io.github.trulyfree.modular8.module;

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
 * Module main interface. All modules based off of Modular should implement this
 * interface. It is highly recommended that all implementations of this class
 * use only non-final instance fields as to assure the usefulness of the setup
 * and destroy methods.
 * 
 * @author vtcakavsmoace
 *
 */
public interface Module {

	/**
	 * Method to be called in order to set up the module for proper use. All
	 * implementations should catch all throwables within the implementation of
	 * setup and return false if they are thrown unintentionally. This allows
	 * for usage of the module in various projects, should that be intended.
	 * Furthermore, all set up for the module instance should occur in this
	 * method in preference to the module implementation's constructor or
	 * through the use of anonymous methods.
	 * 
	 * @return success A boolean representing the success of the setup. Should
	 *         be false if a throwable is thrown or if the setup is in any other
	 *         way unsuccessful.
	 */
	boolean setup();

	/**
	 * Method to be called in order to verify that the module is ready for
	 * proper use. It is highly recommended that all implementations return
	 * false for this method should the method setup not have been called and/or
	 * the method destroy has been called.
	 * 
	 * @return ready A boolean representing the readiness of the instance. If
	 *         the setup method has not been called and/or the destroy method
	 *         has been called, or if for any other reason the module is not
	 *         ready to be used, this should return false.
	 */
	boolean isReady();

	/**
	 * Method to be called in order to destroy, or clean up, the module to
	 * prevent unnecessary background processing or RAM usage. It is suggested
	 * that all instance values should be set to null or zero values when this
	 * method is called. It is also suggested that this method should not return
	 * false, but instead continue to attempt the destruction of the module
	 * before any further action continues on the thread.
	 * 
	 * @return success A boolean representing the success of destruction.
	 */
	boolean destroy();

}
