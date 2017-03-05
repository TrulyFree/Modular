package io.github.trulyfree.modular.test.action;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.action.Action;

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
public class EventTest {

	private static int modified;

	private static Action event1;
	private static Action event2;

	@BeforeClass
	public static void setup() {
		modified = 0;

		event1 = new Action() {
			@Override
			public boolean enact() {
				modified = 1;
				return true;
			}
		};

		event2 = new Action() {
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

	@AfterClass
	public static void destroy() {
		modified = 0;
		event1 = null;
		event2 = null;
	}

}
