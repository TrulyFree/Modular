package io.github.trulyfree.modular.test.display.impl;

import io.github.trulyfree.modular.display.Display;
import io.github.trulyfree.modular.display.DisplayableModule;
import io.github.trulyfree.modular.display.except.DisplayableException;

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
		SimpleDisplayable cause = null;
		try {
			this.module = module;
			this.screen = new boolean[10][10];
			for (SimpleDisplayable displayable : module.getDisplayables()) {
				cause = displayable;
				this.screen[displayable.getX()][displayable.getY()] = true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			this.module = backupModule;
			this.screen = backupScreen;
			throw new DisplayableException(e, cause);
		}
		return true;
	}

}
