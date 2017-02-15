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
		System.out.println(0);
		assertEquals(SimpleModule.someValue, 0);
	}
	
	@Test
	public void stage1_testSetup() {
		System.out.println(1);
		assertTrue(module.setup());
	}
	
	@Test
	public void stage2_verifySetup() {
		System.out.println(2);
		assertEquals(SimpleModule.someValue, 1);
	}
	
	@Test
	public void stage3_testDestroy() {
		System.out.println(3);
		assertTrue(module.destroy());
	}
	
	@Test
	public void stage4_verifyDestroy() {
		System.out.println(4);
		assertEquals(SimpleModule.someValue, 0);
	}

}
