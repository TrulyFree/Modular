package io.github.trulyfree.modular.display;

import io.github.trulyfree.modular.display.except.DisplayableException;
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
 * Display interface. All Modular programs with displays should define an
 * implementation of this interface. The task of the implementation is to parse
 * and display DisplayableModules provided by the module.
 * 
 * @author vtcakavsmoace
 *
 * @param <T>
 *            The type of displayable supported by the display.
 */
public interface Display<T extends Displayable> extends Module {

	/**
	 * Method to be called in order to get the current DisplayableModule being
	 * displayed by this display.
	 * 
	 * @return displayableModule The DisplayableModule currently being displayed
	 *         by this display.
	 */
	public DisplayableModule<T> getDisplayableModule();

	/**
	 * Method to be called in order to set the current DisplayableModule being
	 * displayaed by this display. This method should single-handledly perform
	 * parsing and rendering for the display. If this method fails, it should
	 * revert to the previous setup to praction unresolvable module or screen
	 * problems.
	 * 
	 * @param module
	 *            The DisplayableModule which is attempting to be displayed.
	 * @return success The success of this operation. This should return true
	 *         unless the display is locked into a DisplayableModule.
	 * @throws DisplayableException
	 *             if the parsing operation throws a throwable.
	 */
	public boolean setDisplayableModule(DisplayableModule<T> module) throws DisplayableException;

}
