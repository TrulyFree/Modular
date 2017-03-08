package io.github.trulyfree.modular8.test.display;

import static io.github.trulyfree.modular8.general.Priority.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular8.display.Displayable;
import io.github.trulyfree.modular8.test.display.impl.SimpleDisplayable;

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
public class DisplayableTest {

	private static Displayable displayable;

	@BeforeClass
	public static void setup() {
		displayable = new SimpleDisplayable(AESTHETIC, 0, 0);
	}
	
	@Test
	public void stage0_verifyNoAction() {
		assertEquals(AESTHETIC, displayable.getPriority());
	}
	
	@Test
	public void stage1_0_testSetPriority() {
		assertTrue(displayable.setPriority(LOW));
	}
	
	@Test
	public void stage1_1_verifySetPriority() {
		assertEquals(LOW, displayable.getPriority());
	}
	
	@Test
	public void stage2_0_testCompareTo() {
		Displayable comparableDisplay = new SimpleDisplayable(AESTHETIC, 0, 0);
		assertTrue(comparableDisplay.compareTo(displayable) < 0);
		assertTrue(displayable.compareTo(comparableDisplay) > 0);
		assertTrue(comparableDisplay.setPriority(LOW));
		assertEquals(0, displayable.compareTo(comparableDisplay));
	}
	
	@AfterClass
	public static void destroy() {
		displayable = null;
	}

}
