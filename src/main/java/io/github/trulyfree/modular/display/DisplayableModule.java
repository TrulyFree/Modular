package io.github.trulyfree.modular.display;

import java.util.Collection;

import io.github.trulyfree.modular.module.Module;

public interface DisplayableModule extends Module {

	public Collection<? extends Displayable> getDisplayables();
	
}
