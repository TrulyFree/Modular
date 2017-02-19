package io.github.trulyfree.modular.test.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import io.github.trulyfree.modular.module.ModifiableParentModule;
import io.github.trulyfree.modular.module.ParentModule;
import io.github.trulyfree.modular.test.module.impl.SimpleModifiableParentModule;
import io.github.trulyfree.modular.test.module.impl.SimpleModule;
import io.github.trulyfree.modular.test.module.impl.SimpleParentModule;

public class ModifiableParentModuleTest {
	
	private static ModifiableParentModule<SimpleModule> module;
	
	@BeforeClass
	public static void setup() {
		module = new SimpleModifiableParentModule<SimpleModule>();
		SimpleModule[] modules = new SimpleModule[] {
				
		};
	}
		
	
	@AfterClass
	public static void destroy() {
		SimpleModule.someValue = 0;
		SimpleParentModule.children = null;
	}

}
