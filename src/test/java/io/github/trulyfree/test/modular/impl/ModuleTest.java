package io.github.trulyfree.test.modular.impl;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.module.Module;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModuleTest {
	
	private static Module module;
	
	@BeforeClass
	public static void setup() {
		module = new SimpleModule();
	}
	
	@Test
	public void stage0_verifyNoAction() {
		assertEquals(SimpleModule.someValue, 0);
		assertFalse(module.isReady());
	}
	
	@Test
	public void stage1_testSetup() {
		assertTrue(module.setup());
	}
	
	@Test
	public void stage2_verifySetup() {
		assertEquals(SimpleModule.someValue, 1);
		assertTrue(module.isReady());
	}
	
	@Test
	public void stage3_testDestroy() {
		assertTrue(module.destroy());
	}
	
	@Test
	public void stage4_verifyDestroy() {
		assertEquals(SimpleModule.someValue, 0);
		assertFalse(module.isReady());
	}
	
	@AfterClass
	public static void destroy() {
		SimpleModule.someValue = 0;
	}

}
