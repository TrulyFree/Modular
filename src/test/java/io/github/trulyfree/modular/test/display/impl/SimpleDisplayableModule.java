package io.github.trulyfree.modular.test.display.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.trulyfree.modular.display.Displayable;
import io.github.trulyfree.modular.display.DisplayableModule;
import io.github.trulyfree.modular.general.Priority;

public class SimpleDisplayableModule implements DisplayableModule {

	List<SimpleDisplayable> displayables;
	
	private Priority threshold;
	
	private boolean setup;
	
	public SimpleDisplayableModule(Collection<? extends SimpleDisplayable> displayables) {
		this.displayables = new ArrayList<SimpleDisplayable>(displayables);
	}

	@Override
	public boolean setup() {
		return true;
	}

	@Override
	public boolean isReady() {
		return setup && (displayables != null);
	}

	@Override
	public boolean destroy() {
		setup = false;
		return true;
	}

	@Override
	public Collection<? extends Displayable> getDisplayables() {
		return new ArrayList<SimpleDisplayable>(displayables);
	}

	@Override
	public boolean setPriorityThreshold(Priority priority) {
		threshold = priority;
		return true;
	}

	@Override
	public Priority getPriorityThreshold() {
		return threshold;
	}

}
