package io.github.trulyfree.modular8.test.action.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular8.action.Action;
import io.github.trulyfree.modular8.action.PrioritizedAction;
import io.github.trulyfree.modular8.action.handlers.BackgroundPrioritizedActionHandler;
import io.github.trulyfree.modular8.general.Priority;

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
public class BackgroundPrioritizedActionHandlerTest {

	private static BackgroundPrioritizedActionHandler handler;
	private static ArrayList<ArrayList<ActionImpl>> actions;

	private static StringBuffer check;

	@BeforeClass
	public static void setup() {
		handler = new BackgroundPrioritizedActionHandler((byte) 5);
		actions = new ArrayList<>();
		for (@SuppressWarnings("unused")
		Priority priority : Priority.values()) {
			actions.add(new ArrayList<ActionImpl>());
		}

		check = new StringBuffer();
	}

	@Test
	public void stage0_nonStaticSetup() {
		for (ArrayList<ActionImpl> list : actions) {
			for (Priority priority : Priority.values()) {
				list.add(new ActionImpl(priority, this));
			}
		}
	}

	@Test
	public void stage1_0_verifySetup() {
		assertTrue(handler.isReady());
		assertEquals(0, handler.getActions().size());
		for (ArrayList<ActionImpl> list : actions) {
			for (ActionImpl action : list) {
				assertFalse(handler.getActions().contains(action));
			}
		}
	}

	@Test
	public void stage2_0_testAddAction() {
		for (ArrayList<ActionImpl> list : actions) {
			for (ActionImpl action : list) {
				assertTrue(handler.addAction(action));
			}
		}
	}

	@Test
	public void stage2_1_verifyAddAction() {
		for (ArrayList<ActionImpl> list : actions) {
			for (Action action : list) {
				assertTrue(handler.getActions().contains(action));
			}
		}
	}

	@Test
	public void stage2_2_checkAddAction() {
		assertFalse(handler.addAction(null));
		assertFalse(handler.addAction(new ActionImpl(null, null)));
	}

	@Test
	public void stage3_0_testAndVerifyEachAction() {
		for (Action action : handler.getActions()) {
			assertTrue(action.enact());
		}
		assertEquals(Priority.values().length * Priority.values().length, check.length());
	}

	@Test
	public void stage3_1_testAndVerifyEnactEachAction() {
		check = new StringBuffer();
		while (handler.enactNextAction()) {
		}
		assertEquals(Priority.values().length * Priority.values().length, check.length());
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
		check = new StringBuffer();
	}

	@Test
	public void stage5_1_testEnact() {
		assertTrue(handler.enact());
	}

	@Test
	public void stage5_2_verifyEnact() {
		for (int i = 0; i < Priority.values().length; i++) {
			for (int k = 0; k < Priority.values().length; k++) {
				assertEquals(i * Priority.values().length + k, check.length());
				handler.addAction(actions.get(i).get(k));
				synchronized (this) {
					try {
						wait(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Test
	public void stage6_0_setupStage6() {
		check = new StringBuffer();
	}

	@Test
	public void stage6_1_verifyConcurrentEnact() {
		Thread[] threads = new Thread[4];
		final int between = Priority.values().length / threads.length;
		for (int i = 0; i < threads.length; i++) {
			final int intermediate = i * between;
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int k = intermediate; k < intermediate + between; k++) {
						for (Priority priority : Priority.values()) {
							handler.add(new ActionImpl(priority, this));
							synchronized (this) {
								try {
									this.wait(1);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			});
		}
		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				fail();
			}
		}
		assertEquals(Priority.values().length * Priority.values().length, check.length());
	}

	@Test
	public void stage7_0_testSafeHalt() {
		handler.safeHalt();
	}

	@AfterClass
	public static void destroy() {
		handler = null;
		actions = null;
		check = null;
	}

	public void modify(Object obj) {
		check.append(" ");
		synchronized (obj) {
			obj.notify();
		}
	}

	private class ActionImpl implements PrioritizedAction {
		private final Priority priority;
		private final Object obj;

		public ActionImpl(Priority priority, Object obj) {
			this.priority = priority;
			this.obj = obj;
		}

		@Override
		public boolean enact() {
			modify(obj);
			return true;
		}

		@Override
		public Priority getPriority() {
			return priority;
		}
	}

}
