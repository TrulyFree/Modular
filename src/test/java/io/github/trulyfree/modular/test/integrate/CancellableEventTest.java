package io.github.trulyfree.modular.test.integrate;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.test.integrate.impl.SimpleCancellableEvent;

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
public class CancellableEventTest {

	private static int modified;

	private static SimpleCancellableEvent event;

	@BeforeClass
	public static void setup() {
		modified = 0;

		event = new SimpleCancellableEvent() {
			@Override
			public boolean enact() {
				final boolean enacted = !isCancelled();
				if (enacted)
					modified = 1;
				return enacted;
			}
		};
	}

	@Test
	public void stage0_0_verifyNoAction() {
		assertEquals(0, modified);
		assertFalse(event.isCancelled());
	}

	@Test
	public void stage0_1_testEnact() {
		assertTrue(event.enact());
	}

	@Test
	public void stage0_2_verifyEnact() {
		assertEquals(1, modified);
	}

	@Test
	public void stage0_3_resetModified() {
		modified = 0;
		assertEquals(0, modified);
	}

	@Test
	public void stage0_4_testCancelled() {
		assertTrue(event.setCancelled(true));
	}

	@Test
	public void stage0_5_verifyCancelled() {
		assertTrue(event.isCancelled());
	}

	@Test
	public void stage0_6_testEnactFailure() {
		assertFalse(event.enact());
	}

	@Test
	public void stage0_7_verifyEnactFailure() {
		assertEquals(0, modified);
	}

	@Test
	public void stage0_8_testCancelled() {
		assertTrue(event.setCancelled(false));
	}

	@Test
	public void stage0_9_verifyCancelled() {
		assertFalse(event.isCancelled());
	}

	@Test
	public void stage1_0_verifyNoAction() {
		stage0_0_verifyNoAction();
	}

	@Test
	public void stage1_1_testEnact() {
		stage0_1_testEnact();
	}

	@Test
	public void stage1_2_verifyEnact() {
		stage0_2_verifyEnact();
	}

	@AfterClass
	public static void destroy() {
		modified = 0;
		event = null;
	}

}
