package io.github.trulyfree.test.modular.impl;

import io.github.trulyfree.modular.module.ParentModule;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/* Modular library by TrulyFree: A general-use module-building library.
 * Copyright (C) 2016  VTCAKAVSMoACE
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParentModuleTest {
	
	private static ParentModule<SimpleModule> module;
	
	@BeforeClass
	public static void setup() {
		module = new SimpleParentModule();
	}
	
	@Test
	public void stage0_verifyNoAction() {
		assertEquals(SimpleModule.someValue, 0);
		assertEquals(SimpleParentModule.children, null);
		assertFalse(module.isReady());
	}
	
	@Test
	public void stage1_testSetup() {
		assertTrue(module.setup());
	}
	
	@Test
	public void stage2_verifySetup() {
		assertEquals(SimpleModule.someValue, 1);
		assertNotEquals(SimpleParentModule.children, null);
		assertTrue(module.isReady());
	}
	
	@Test
	public void stage3_testGetChildren() {
		assertEquals(SimpleParentModule.children, ((ParentModule<SimpleModule>) module).getChildren());
	}
	
	@Test
	public void stage4_testDestroy() {
		assertTrue(module.destroy());
	}
	
	@Test
	public void stage5_verifyDestroy() {
		assertEquals(SimpleModule.someValue, 0);
		assertEquals(SimpleParentModule.children, null);
		assertFalse(module.isReady());
	}
	
	@AfterClass
	public static void destroy() {
		SimpleModule.someValue = 0;
		SimpleParentModule.children = null;
	}
	
}
