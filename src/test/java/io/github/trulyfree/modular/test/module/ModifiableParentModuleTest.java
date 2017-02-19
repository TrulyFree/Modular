package io.github.trulyfree.modular.test.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.module.ModifiableParentModule;
import io.github.trulyfree.modular.test.module.impl.SimpleModifiableParentModule;
import io.github.trulyfree.modular.test.module.impl.SimpleModule;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModifiableParentModuleTest {
	
	private static ModifiableParentModule<SimpleModule> parentModule;
	private static SimpleModule[] modulesToAdd;
	
	private static Collection<SimpleModule> removedModules;
	
	@BeforeClass
	public static void setup() {
		parentModule = new SimpleModifiableParentModule<SimpleModule>();
		
		modulesToAdd = new SimpleModule[] {
				new SimpleModule(1),
				new SimpleModule(2),
				new SimpleModule(3)
		};
	}
		
	@Test
	public void stage0_verifyNoAction() {
		assertEquals(0, parentModule.getChildren().size());
		assertFalse(parentModule.isReady());
		for (SimpleModule module : modulesToAdd) {
			assertFalse(parentModule.getChildren().contains(module));
			assertFalse(module.isReady());
		}
		assertEquals(null, removedModules);
	}
	
	@Test
	public void stage1_0_testSetup() {
		assertTrue(parentModule.setup());
	}
	
	@Test
	public void stage1_1_verifySetup() {
		assertTrue(parentModule.isReady());
	}
	
	@Test
	public void stage2_0_testAddModule() {
		for (SimpleModule module : modulesToAdd) {
			assertTrue(parentModule.addModule(module));
		}
	}
	
	@Test
	public void stage2_1_verifyAddModule() {
		for (SimpleModule module : modulesToAdd) {
			assertTrue(parentModule.getChildren().contains(module));
		}
	}
	
	@Test
	public void stage3_testAndVerifyEachModule() {
		int expected = 1;
		for (SimpleModule module : parentModule.getChildren()) {
			assertTrue(module.setup());
			assertEquals(expected, SimpleModule.someValue);
			expected++;
		}
	}
	
	@Test
	public void stage4_0_testRemoveModule() {
		for (SimpleModule module : parentModule.getChildren()) {
			assertEquals(module, parentModule.removeModule(module));
		}
	}
	
	@Test
	public void stage4_1_verifyRemoveModule() {
		for (SimpleModule module : modulesToAdd) {
			assertFalse(parentModule.getChildren().contains(module));
		}
	}
	
	@Test
	public void stage5_0_setupStage5() {
		stage2_0_testAddModule();
		stage2_1_verifyAddModule();
	}
	
	@Test
	public void stage5_1_testRemoveModuleByType() {
		removedModules = parentModule.removeModuleByType(modulesToAdd[0].getClass());
	}
	
	@Test
	public void stage5_2_verifyRemoveModuleByType() {
		for (SimpleModule module : modulesToAdd) {
			assertTrue(removedModules.contains(module));
			assertFalse(parentModule.getChildren().contains(module));
		}
	}
	
	@Test
	public void stage6_0_setupStage6() {
		removedModules = null;
		stage2_0_testAddModule();
		stage2_1_verifyAddModule();
	}
	
	@Test
	public void stage6_1_testClear() {
		parentModule.clear();
	}
	
	@Test
	public void stage6_2_verifyClear() {
		for (SimpleModule module : modulesToAdd) {
			assertFalse(parentModule.getChildren().contains(module));
		}
	}
	
	@AfterClass
	public static void destroy() {
		SimpleModule.someValue = 0;
		parentModule = null;
	}

}
