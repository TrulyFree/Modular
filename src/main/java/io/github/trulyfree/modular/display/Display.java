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

public interface Display<T extends DisplayableModule> extends Module {

	public T getDisplayableModule();
	
	public boolean setDisplayableModule(T displayable) throws DisplayableException;
	
}
