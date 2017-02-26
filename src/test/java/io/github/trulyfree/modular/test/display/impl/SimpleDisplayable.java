package io.github.trulyfree.modular.test.display.impl;

import io.github.trulyfree.modular.display.Displayable;
import io.github.trulyfree.modular.general.Priority;

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
