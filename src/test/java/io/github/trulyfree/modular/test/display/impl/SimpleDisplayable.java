package io.github.trulyfree.modular.test.display.impl;

import io.github.trulyfree.modular.display.Displayable;
import io.github.trulyfree.modular.general.Priority;

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

public class SimpleDisplayable implements Displayable {

	private Priority priority;
	
	public SimpleDisplayable(Priority priority) {
		this.priority = priority;
	}

	@Override
	public Priority getPriority() {
		return priority;
	}

	@Override
	public boolean setPriority(Priority priority) {
		this.priority = priority;
		return true;
	}

}
