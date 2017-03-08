package io.github.trulyfree.modular8.test.display;

import static io.github.trulyfree.modular8.general.Priority.AESTHETIC;
import static io.github.trulyfree.modular8.general.Priority.MAX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular8.display.except.DisplayableException;
import io.github.trulyfree.modular8.test.display.impl.SimpleDisplay;
import io.github.trulyfree.modular8.test.display.impl.SimpleDisplayable;
import io.github.trulyfree.modular8.test.display.impl.SimpleDisplayableModule;

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
public class DisplayTest {

	private static SimpleDisplay display;
	private static SimpleDisplayableModule successful;
	private static SimpleDisplayableModule failure;

	@BeforeClass
	public static void setup() {
		display = new SimpleDisplay();
		List<SimpleDisplayable> displayables1 = new ArrayList<SimpleDisplayable>(11);
		List<SimpleDisplayable> displayables2 = new ArrayList<SimpleDisplayable>(11);
		for (int x = 0; x < 11; x++) {
			displayables1.add(new SimpleDisplayable(AESTHETIC, x, 0));
			displayables2.add(new SimpleDisplayable(MAX, x, 0));
		}
		successful = new SimpleDisplayableModule(displayables1);
		failure = new SimpleDisplayableModule(displayables2);
		successful.setPriorityThreshold(MAX);
		failure.setPriorityThreshold(MAX);
	}

	@Test
	public void stage0_verifyNoAction() {
		assertNotEquals(null, display);
		assertNotEquals(null, successful);
		assertNotEquals(null, failure);
		assertEquals(11, successful.getDisplayables().size());
		assertEquals(11, failure.getDisplayables().size());
		assertEquals(null, display.getDisplayableModule());
	}

	@Test
	public void stage1_0_testSetupDisplay() {
		assertTrue(display.setup());
	}

	@Test
	public void stage1_1_verifySetupDisplay() {
		assertTrue(display.isReady());
		boolean[][] screen = display.getScreen();
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				assertFalse(screen[x][y]);
			}
		}
	}

	@Test
	public void stage2_0_testSetDisplayableModule() {
		try {
			assertTrue(display.setDisplayableModule(successful));
		} catch (DisplayableException e) {
			fail();
		}
	}

	@Test
	public void stage2_1_verifySetDisplayableModule() {
		boolean[][] screen = display.getScreen();
		for (int x = 0; x < 10; x++) {
			assertTrue(screen[x][0]);
		}
		for (int x = 0; x < 10; x++) {
			for (int y = 1; y < 10; y++) {
				assertFalse(screen[x][y]);
			}
		}
		assertEquals(successful, display.getDisplayableModule());
	}

	@Test
	public void stage3_0_testSetDisplayableModuleFailure() {
		try {
			display.setDisplayableModule(failure);
			fail();
		} catch (DisplayableException e) {
		}
	}

	@Test
	public void stage3_1_verifySetDisplayableModuleReversion() {
		stage2_1_verifySetDisplayableModule();
	}

	@AfterClass
	public static void destroy() {
		display = null;
		successful = null;
		failure = null;
	}

}
