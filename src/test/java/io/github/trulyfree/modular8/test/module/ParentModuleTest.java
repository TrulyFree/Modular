package io.github.trulyfree.modular8.test.module;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular8.test.module.impl.SimpleModule;
import io.github.trulyfree.modular8.test.module.impl.SimpleParentModule;

/* Modular8 library by TrulyFree: A general-use module-building library.
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

	private static SimpleParentModule parentModule;

	@BeforeClass
	public static void setup() {
		parentModule = new SimpleParentModule(1);
	}

	@Test
	public void stage0_verifyNoAction() {
		assertEquals(SimpleModule.someValue, 0);
		assertEquals(SimpleParentModule.children, null);
		assertFalse(parentModule.isReady());
	}

	@Test
	public void stage1_0_testSetup() {
		assertTrue(parentModule.setup());
	}

	@Test
	public void stage1_1_verifySetup() {
		assertEquals(1, SimpleModule.someValue);
		assertNotEquals(null, SimpleParentModule.children);
		assertTrue(parentModule.isReady());
	}

	@Test
	public void stage2_testGetChildren() {
		final List<SimpleModule> unmodified = parentModule.getChildren();
		final List<SimpleModule> modified = parentModule.getChildren();
		for (int i = 0; i < unmodified.size(); i++) {
			assertEquals(unmodified.get(i), modified.get(i));
		}
		Collections.reverse(modified);
		boolean same = true;
		for (int i = 0; i < unmodified.size(); i++) {
			if (!unmodified.get(i).equals(modified.get(i))) {
				same = false;
			}
		}
		assertFalse(same);
	}

	@Test
	public void stage3_0_testDestroy() {
		assertTrue(parentModule.destroy());
	}

	@Test
	public void stage3_1_verifyDestroy() {
		assertEquals(0, SimpleModule.someValue);
		assertEquals(null, SimpleParentModule.children);
		assertFalse(parentModule.isReady());
	}

	@AfterClass
	public static void destroy() {
		SimpleModule.someValue = 0;
		SimpleParentModule.children = null;
	}

}
