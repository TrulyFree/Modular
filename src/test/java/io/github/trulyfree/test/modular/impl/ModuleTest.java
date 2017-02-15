package io.github.trulyfree.test.modular.impl;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.module.Module;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModuleTest {
	
	private final Module module;
	
	public ModuleTest() {
		module = new SimpleModule();
	}
	
	@Test
	public void stage0_verifyNoAction() {
		assertEquals(castModule().someValue, 0);
	}
	
	@Test
	public void stage1_testSetup() {
		assertTrue(module.setup());
	}
	
	@Test
	public void stage2_verifySetup() {
		assertEquals(castModule().someValue, 1);
	}
	
	@Test
	public void stage3_testDestroy() {
		assertTrue(module.destroy());
	}
	
	@Test
	public void stage4_verifyDestroy() {
		assertEquals(castModule().someValue, 0);
	}
	
	private SimpleModule castModule() {
		return SimpleModule.class.cast(module);
	}

}
