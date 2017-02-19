package io.github.trulyfree.modular.module;

import java.util.Collection;

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
 * ParentModule interface. All modules with multiple child modules should
 * implement this interface. It is highly recommended that all implementations
 * of this class use only non-final instance fields as to assure the usefulness
 * of the setup and destroy methods of the Module interface from which this
 * interface extends.
 * 
 * @author vtcakavsmoace
 *
 * @param <T>
 *            The module type supported by this ParentModule. It is likely that
 *            most implementations will generalize this to Module, but
 *            implementations may vary.
 */
public interface ParentModule<T extends Module> extends Module {

	/**
	 * Method to be called in order to retrieve the children of this interface.
	 * The collection returned should NOT be capable of modifying the module's
	 * child modules.
	 * 
	 * @return children The child modules of this module. Note that
	 *         modifications this collection should NOT modify the children of
	 *         the parent module. Instead, should this behavior be intended, the
	 *         user should implement the ModifiableParentModule interface.
	 */
	Collection<T> getChildren();

}
