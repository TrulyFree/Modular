package io.github.trulyfree.modular8.test.action;

import static io.github.trulyfree.modular8.general.Priority.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular8.action.Action;
import io.github.trulyfree.modular8.action.PrioritizedAction;
import io.github.trulyfree.modular8.test.action.impl.SimplePrioritizedAction;

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
public class PrioritizedActionTest {

	private static int modified;

	private static Action action0;
	private static PrioritizedAction action1;
	private static PrioritizedAction action2;

	@BeforeClass
	public static void setup() {
		modified = 0;

		action0 = new Action() {
			@Override
			public boolean enact() {
				return true;
			}
		};

		action1 = new SimplePrioritizedAction(DIRE) {
			@Override
			public boolean enact() {
				modified = 1;
				return true;
			}
		};

		action2 = new SimplePrioritizedAction(AESTHETIC) {
			@Override
			public boolean enact() {
				modified = 0;
				return true;
			}
		};
	}

	@Test
	public void stage0_verifyNoAction() {
		assertEquals(0, modified);
	}

	@Test
	public void stage1_0_testEnact() {
		assertTrue(action1.enact());
	}

	@Test
	public void stage1_1_verifyEnact() {
		assertEquals(1, modified);
	}

	@Test
	public void stage2_0_testEnact() {
		assertTrue(action2.enact());
	}

	@Test
	public void stage2_1_verifyEnact() {
		assertEquals(0, modified);
	}

	@Test
	public void stage3_testGetPriority() {
		assertTrue(action1.getPriority() == DIRE);
		assertTrue(action2.getPriority() == AESTHETIC);
	}

	@Test
	public void stage5_testCompareToOtherPrioritized() {
		assertTrue(action1.compareTo(action2) > 0);
		assertTrue(action2.compareTo(action1) < 0);
		assertTrue(action1.compareTo(action1) == 0);
	}

	@Test
	public void stage6_testCompareToStandardAction() {
		assertEquals(action1.compareTo(action0), 1);
		assertEquals(action2.compareTo(action0), 1);
	}

	@AfterClass
	public static void destroy() {
		modified = 0;
		action1 = null;
		action2 = null;
	}

}
