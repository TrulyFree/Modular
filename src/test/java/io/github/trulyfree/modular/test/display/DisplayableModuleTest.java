package io.github.trulyfree.modular.test.display;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.display.Displayable;
import io.github.trulyfree.modular.general.Priority;
import io.github.trulyfree.modular.test.display.impl.SimpleDisplayable;
import io.github.trulyfree.modular.test.display.impl.SimpleDisplayableModule;

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
public class DisplayableModuleTest {

	private static SimpleDisplayableModule module;
	private static List<SimpleDisplayable> displayables;

	@BeforeClass
	public static void setup() {
		Priority[] priorities = Priority.values();
		displayables = new ArrayList<SimpleDisplayable>(priorities.length);
		for (Priority priority : priorities) {
			displayables.add(new SimpleDisplayable(priority, 0, 0));
		}
		module = new SimpleDisplayableModule(displayables);
	}

	@Test
	public void stage0_verifyNoAction() {
		assertNotEquals(null, module);
		assertNotEquals(null, displayables);
	}

	@Test
	public void stage1_0_testGetDisplayables() {
		final Collection<? extends Displayable> reported = module.getDisplayables();
		for (Displayable displayable : reported) {
			assertTrue(displayables.contains(displayable));
		}
	}

	@Test
	public void stage1_1_verifyGetDisplayables() {
		final List<SimpleDisplayable> unmodified = module.getDisplayables();
		final List<SimpleDisplayable> modified = module.getDisplayables();
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

	@AfterClass
	public static void destroy() {
		module = null;
	}

}
