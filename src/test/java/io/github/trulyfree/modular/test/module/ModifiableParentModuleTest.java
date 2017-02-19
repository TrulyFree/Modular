package io.github.trulyfree.modular.test.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import io.github.trulyfree.modular.module.ModifiableParentModule;
import io.github.trulyfree.modular.test.module.impl.SimpleModifiableParentModule;
import io.github.trulyfree.modular.test.module.impl.SimpleModule;

public class ModifiableParentModuleTest {
	
	private static ModifiableParentModule<SimpleModule> module;
	private static SimpleModule[] modulesToAdd;
	
	@BeforeClass
	public static void setup() {
		module = new SimpleModifiableParentModule<SimpleModule>();
		modulesToAdd = new SimpleModule[] {
				new SimpleModule(1),
				new SimpleModule(2),
				new SimpleModule(3)
		};
	}
		
	@Test
	public void stage0_verifyNoAction() {
		assertEquals(0, module.getChildren().size());
		assertFalse(module.isReady());
	}
	
	@AfterClass
	public static void destroy() {
		SimpleModule.someValue = 0;
		module = null;
	}

}
