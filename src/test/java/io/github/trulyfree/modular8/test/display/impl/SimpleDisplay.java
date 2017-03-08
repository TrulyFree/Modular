package io.github.trulyfree.modular8.test.display.impl;

import io.github.trulyfree.modular8.display.Display;
import io.github.trulyfree.modular8.display.DisplayableModule;
import io.github.trulyfree.modular8.display.except.DisplayableException;

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

public class SimpleDisplay implements Display<SimpleDisplayable> {

	private DisplayableModule<SimpleDisplayable> module;

	private boolean[][] screen;

	@Override
	public boolean setup() {
		screen = new boolean[10][10];
		return true;
	}

	@Override
	public boolean isReady() {
		return screen != null;
	}

	@Override
	public boolean destroy() {
		screen = null;
		return true;
	}

	@Override
	public DisplayableModule<SimpleDisplayable> getDisplayableModule() {
		return module;
	}

	@Override
	public boolean setDisplayableModule(DisplayableModule<SimpleDisplayable> module) throws DisplayableException {
		DisplayableModule<SimpleDisplayable> backupModule = this.module;
		boolean[][] backupScreen = this.screen;
		this.module = module;
		this.screen = new boolean[10][10];
		for (SimpleDisplayable displayable : module.getDisplayables()) {
			try {
				this.screen[displayable.getX()][displayable.getY()] = true;
			} catch (ArrayIndexOutOfBoundsException e) {
				if (displayable.getPriority().compareTo(module.getPriorityThreshold()) >= 0) {
					this.module = backupModule;
					this.screen = backupScreen;
					throw new DisplayableException(e, displayable);
				}
			}
		}
		return true;
	}

	public boolean[][] getScreen() {
		return screen;
	}

}
