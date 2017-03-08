package io.github.trulyfree.modular8.test.general;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular8.general.Cancellable;

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
public class CancellableTest {

	private static Cancellable cancellable;

	@BeforeClass
	public static void setup() {
		cancellable = new Cancellable() {
			private boolean cancelled;

			@Override
			public boolean setCancelled(boolean cancelled) {
				this.cancelled = cancelled;
				return true;
			}

			@Override
			public boolean isCancelled() {
				return cancelled;
			}
		};
	}

	@Test
	public void stage0_verifyNoAction() {
		assertFalse(cancellable.isCancelled());
	}

	@Test
	public void stage1_0_testSetCancelled() {
		assertTrue(cancellable.setCancelled(true));
	}

	@Test
	public void stage1_1_verifySetCancelled() {
		assertTrue(cancellable.isCancelled());
	}

	@Test
	public void stage2_0_testSetCancelledFalse() {
		assertTrue(cancellable.setCancelled(false));
	}

	@Test
	public void stage2_0_verifySetCancelledFalse() {
		stage0_verifyNoAction();
	}

	@AfterClass
	public static void destroy() {
		cancellable = null;
	}

}
