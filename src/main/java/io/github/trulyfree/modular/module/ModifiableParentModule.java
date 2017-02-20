package io.github.trulyfree.modular.module;

import java.util.ArrayList;
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
 * ModifiableParentModule interface. All modules with multiple child modules
 * which may be swapped in or out during execution should implement this
 * interface. If the child modules should not be modifiable during runtime,
 * consider using the ParentModule interface instead. It is highly recommended
 * that all implementations of this class use only non-final instance fields as
 * to assure the usefulness of the setup and destroy methods of the Module
 * interface from which this interface extends.
 * 
 * @author vtcakavsmoace
 *
 * @param <T>
 *            The module type supported by this ParentModule. It is likely that
 *            most implementations will generalize this to Module, but
 *            implementations may vary.
 */
public interface ModifiableParentModule<T extends Module> extends ParentModule<T> {

	/**
	 * Method to be called in order to add a child module to this parent module.
	 * 
	 * @param module
	 *            The module to add as a child of this parent module.
	 * @return success A boolean representing the success of the adding of the
	 *         module as a child of this parent module. If the adding process
	 *         should fail in any way, return false; otherwise, return true.
	 */
	public boolean addModule(T module);

	/**
	 * Method to be called in order to remove a specific child module from this
	 * parent module.
	 * 
	 * @param module
	 *            The module or a module equal to the module being removed.
	 * @return removed The module which was removed by this operation. If no
	 *         module was removed, this should return null.
	 */
	public T removeModule(T module);

	/**
	 * Method to be called in order to remove all of a type of module from the
	 * children of this parent module. A suggested implementation has been
	 * provided, but implementations may differ on the approach chosen.
	 * 
	 * @param type
	 *            The type of module to remove. Note that this should respect
	 *            polymorphism.
	 * @return removed The module(s) removed by this operation. If no module is
	 *         removed, this should return an empty collection of modules.
	 */
	public default Collection<T> removeModuleByType(Class<? extends Module> type) {
		Collection<T> toReturn = new ArrayList<T>(this.getChildren().size());
		for (T module : this.getChildren()) {
			if (type.isInstance(module)) {
				toReturn.add(this.removeModule(module));
			}
		}
		return toReturn;
	}

	/**
	 * Method to be called in order to remove all children of this parent
	 * module. A suggested implementation has been provided which acts as a
	 * simple call of removeAllByType when passed Method.class. Implementations
	 * may differ according to need. The return type chosen is intended as a
	 * quick-and-dirty way to convert this modifiable parent module into a
	 * unmodifiable parent module by constructing an implementation of
	 * ParentModule to with the collection returned.
	 */
	public default void clear() {
		for (T module : this.getChildren()) {
			this.removeModule(module);
		}
	}

}
