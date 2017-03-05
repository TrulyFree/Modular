package io.github.trulyfree.modular.test.action;

import static io.github.trulyfree.modular.general.Priority.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.action.Action;
import io.github.trulyfree.modular.action.PrioritizedAction;
import io.github.trulyfree.modular.test.action.impl.SimplePrioritizedEvent;

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
public class PrioritizedEventTest {

	private static int modified;

	private static Action event0;
	private static PrioritizedAction event1;
	private static PrioritizedAction event2;

	@BeforeClass
	public static void setup() {
		modified = 0;

		event0 = new Action() {
			@Override
			public boolean enact() {
				return true;
			}
		};

		event1 = new SimplePrioritizedEvent(DIRE) {
			@Override
			public boolean enact() {
				modified = 1;
				return true;
			}
		};

		event2 = new SimplePrioritizedEvent(AESTHETIC) {
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
		assertTrue(event1.enact());
	}

	@Test
	public void stage1_1_verifyEnact() {
		assertEquals(1, modified);
	}

	@Test
	public void stage2_0_testEnact() {
		assertTrue(event2.enact());
	}

	@Test
	public void stage2_1_verifyEnact() {
		assertEquals(0, modified);
	}

	@Test
	public void stage3_testGetPriority() {
		assertTrue(event1.getPriority() == DIRE);
		assertTrue(event2.getPriority() == AESTHETIC);
	}

	@Test
	public void stage5_testCompareToOtherPrioritized() {
		assertTrue(event1.compareTo(event2) > 0);
		assertTrue(event2.compareTo(event1) < 0);
		assertTrue(event1.compareTo(event1) == 0);
	}

	@Test
	public void stage6_testCompareToStandardEvent() {
		assertEquals(event1.compareTo(event0), 1);
		assertEquals(event2.compareTo(event0), 1);
	}

	@AfterClass
	public static void destroy() {
		modified = 0;
		event1 = null;
		event2 = null;
	}

}
