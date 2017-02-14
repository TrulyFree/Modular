package io.github.trulyfree.modular.module;

import java.util.List;

public interface ParentModule extends Module {

	List<Module> getChildren();	
	
}
