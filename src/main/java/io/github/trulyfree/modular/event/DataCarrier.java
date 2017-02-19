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
 * DataCarrier interface. This interface should be implemented by anything that
 * carries data between one module/event to another asynchronously.
 * 
 * @author vtcakavsmoace
 *
 * @param <T>
 *            The type of data being passed.
 */
public interface DataCarrier<T> {

	/**
	 * Method to be called in order to gather the data carried by this instance.
	 * 
	 * @return data The data carried by this instance.
	 */
	public T getData();

	/**
	 * Method to be called in order to set the data carried by this instance.
	 * Note that this may have no effect depending on the implmenation.
	 * 
	 * @param data
	 *            The data to be carried by this instance.
	 * @return success A boolean representing whether or not the data was set to
	 *         the passed value. This should return false if the data may not be
	 *         altered.
	 */
	public boolean setData(T data);

}
