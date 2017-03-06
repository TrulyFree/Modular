package io.github.trulyfree.modular.test.action.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.action.Action;
import io.github.trulyfree.modular.action.handlers.ActionHandler;
import io.github.trulyfree.modular.action.handlers.GeneralizedActionHandler;

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
public class GeneralizedActionHandlerTest {

	private static ActionHandler<Action> handler;
	private static ArrayList<ActionImpl> actions;

	private static int expected;

	@BeforeClass
	public static void setup() {
		handler = new GeneralizedActionHandler();
		actions = new ArrayList<ActionImpl>();

		for (int i = 1; i <= 10; i++) {
			actions.add(new ActionImpl(i));
		}
		expected = 1;
	}

	@Test
	public void stage0_verifyNoAction() {
		assertFalse(handler.isReady());
	}

	@Test
	public void stage1_0_testSetup() {
		assertTrue(handler.setup());
	}

	@Test
	public void stage1_1_verifySetup() {
		assertTrue(handler.isReady());
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
		assertEquals(11, expected);
	}

	@Test
	public void stage3_1_testAndVerifyEnactEachAction() {
		expected = 1;
		while (handler.enactNextAction()) {
		}
		assertEquals(11, expected);
	}

	@Test
	public void stage3_2_verifyEnactEachActionRemoval() {
		assertEquals(0, handler.size());
	}

	@Test
	public void stage4_0_setupStage4() {
		stage2_0_testAddAction();
		expected = 1;
	}

	@Test
	public void stage4_1_testEnact() {
		assertTrue(handler.enact());
	}

	@Test
	public void stage4_2_verifyEnactRemoval() {
		stage3_2_verifyEnactEachActionRemoval();
	}

	@Test
	public void stage5_0_setupStage4() {
		stage2_0_testAddAction();
	}

	@Test
	public void stage5_1_testClear() {
		handler.clear();
	}

	@Test
	public void stage5_2_verifyClear() {
		stage3_2_verifyEnactEachActionRemoval();
	}

	@AfterClass
	public static void destroy() {
		handler = null;
		actions = null;
		expected = 0;
	}

	private static void modify(int val) {
		assertEquals(expected++, val);
	}

	private static class ActionImpl implements Action {
		private final int val;

		public ActionImpl(int val) {
			this.val = val;
		}

		@Override
		public boolean enact() {
			modify(val);
			return true;
		}
	}

}
