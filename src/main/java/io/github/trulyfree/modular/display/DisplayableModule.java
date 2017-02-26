package io.github.trulyfree.modular.display;

import java.util.Collection;

import io.github.trulyfree.modular.general.Priority;
import io.github.trulyfree.modular.module.Module;

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
 * DisplayableModule interface. Implementations of this interface will act as a
 * "parent" for all the Displayables which they monitor, modify, and contain.
 * DisplayableModules which may also appear as subdisplays (i.e. a LinearLayout
 * inside of a LinearLayout in Android) should also implement Displayable.
 * 
 * @author vtcakavsmoace
 *
 * @param <T>
 *            The type of displayable supported by the target display or an
 *            extension thereof.
 */
public interface DisplayableModule<T extends Displayable> extends Module {

	/**
	 * Method to be called in order to retrieve the displayables contained by
	 * this module. The collection returned should NOT be capable of modifying
	 * the module's contained displayables. However, the individual displayables
	 * may be modified with changes that do affect those located within this
	 * module.
	 * 
	 * @return displayables The displayables held by this module.
	 */
	public Collection<T> getDisplayables();

	/**
	 * Method to be called in order to set the intended threshold of priority
	 * for displayables held within this class. This priority should determine
	 * whether or not a displayable will be suppressed should it throw a
	 * DisplayableException during rendering. If the priority threshold returned
	 * is greater than the priority of the displayable, it will be suppressed,
	 * otherwise a DisplayableException will be thrown (uncaught).
	 * 
	 * @param priority
	 *            The new priority threshold of this displayable module.
	 * @return success A boolean representing the success or failure of the set
	 *         operation.
	 */
	public boolean setPriorityThreshold(Priority priority);

	/**
	 * Method to be called in order to get the intended threshold of priority
	 * for displayables held within this class. This priority should determine
	 * whether or not a displayable will be suppressed should it throw a
	 * DisplayableException during rendering. If the priority threshold returned
	 * is greater than the priority of the displayable, it will be suppressed,
	 * otherwise a DisplayableException will be thrown (uncaught).
	 * 
	 * @return threshold The priority threshold of this displayable module.
	 */
	public Priority getPriorityThreshold();

}
