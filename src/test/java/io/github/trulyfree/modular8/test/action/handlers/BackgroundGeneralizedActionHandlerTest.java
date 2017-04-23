package io.github.trulyfree.modular8.test.action.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular8.action.Action;
import io.github.trulyfree.modular8.action.handlers.BackgroundGeneralizedActionHandler;

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
public class BackgroundGeneralizedActionHandlerTest {

	private static BackgroundGeneralizedActionHandler handler;
	private static ArrayList<ActionImpl> actions;

	private static volatile int check;

	@BeforeClass
	public static void setup() {
		handler = new BackgroundGeneralizedActionHandler((byte) 5);
		actions = new ArrayList<ActionImpl>();

		check = 0;
	}

	@Test
	public void stage0_nonStaticSetup() {
		for (int i = 0; i < 25; i++) {
			actions.add(new ActionImpl(this, 25));
		}
	}

	@Test
	public void stage1_0_verifySetup() {
		assertTrue(handler.setup());
		assertEquals(0, handler.getActions().size());
		for (ActionImpl action : actions) {
			assertFalse(handler.getActions().contains(action));
		}
	}

	@Test
	public void stage2_0_testAddAction() {
		for (ActionImpl action : actions) {
			assertTrue(handler.addAction(action));
		}
	}

	@Test
	public void stage2_1_verifyAddAction() {
		for (Action action : actions) {
			assertTrue(handler.getActions().contains(action));
		}
	}

	@Test
	public void stage2_2_checkAddAction() {
		assertFalse(handler.addAction(null));
	}

	@Test
	public void stage3_0_testAndVerifyEachAction() {
		for (Action action : handler.getActions()) {
			assertTrue(action.enact());
		}
		assertEquals(25, check);
	}

	@Test
	public void stage3_1_testAndVerifyEnactEachAction() {
		check = 0;
		for (int i = 0; i < 25; i++) {
			assertTrue(handler.enactNextAction());
		}
	}

	@Test
	public void stage3_2_verifyEnactEachActionRemoval() {
		assertEquals(0, handler.size());
	}

	@Test
	public void stage4_0_setupStage5() {
		stage2_0_testAddAction();
	}

	@Test
	public void stage4_1_testClear() {
		handler.clear();
	}

	@Test
	public void stage4_2_verifyClear() {
		stage3_2_verifyEnactEachActionRemoval();
	}

	@Test
	public void stage5_0_setupStage4() {
		check = 0;
	}

	@Test
	public void stage5_1_testEnact() {
		assertTrue(handler.enact());
	}

	@Test
	public void stage5_2_verifyEnact() {
		for (int k = 0; k < 25; k++) {
			handler.addAction(actions.get(k));
		}
		synchronized (this) {
			try {
				wait(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		assertEquals(25, check);
	}

	@Test
	public void stage6_0_setupStage6() {
		check = 0;
	}

	@Test
	public void stage6_1_verifyConcurrentEnact() {
		final Thread[] threads = new Thread[4];
		for (int i = 0; i < threads.length; i++) {
			final int intermediary = i;
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int u = 0; u < 5; u++) {
						handler.addAction(new ActionImpl(threads[intermediary], 20));
					}
				}
			});
		}
		for (Thread thread : threads) {
			thread.start();
		}
		synchronized (this) {
			try {
				this.wait(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		assertEquals(20, check);
	}

	@Test
	public void stage7_0_testDestroy() {
		handler.destroy();
	}

	@AfterClass
	public static void destroy() {
		handler = null;
		actions = null;
		check = 0;
	}

	private class ActionImpl implements Action {
		private final Object lock;
		private final int max;

		public ActionImpl(Object lock, int max) {
			this.lock = lock;
			this.max = max;
		}

		@Override
		public boolean enact() {
			check++;
			if (max == -1 || check >= max) {
				synchronized (lock) {
					lock.notify();
				}
			}
			return true;
		}
	}

}
