package io.github.trulyfree.modular.test.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.action.Action;
import io.github.trulyfree.modular.action.ModifiableActionGroup;
import io.github.trulyfree.modular.test.action.impl.SimpleModifiableActionGroup;

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
public class ModifiableActionGroupTest {

	private static ModifiableActionGroup<ActionImpl> meg;
	private static ActionImpl[] actionsToAdd;

	private static Collection<ActionImpl> removedActions;

	@BeforeClass
	public static void setup() {
		meg = new SimpleModifiableActionGroup<ActionImpl>();

		actionsToAdd = new ActionImpl[] { new ActionImpl(), new ActionImpl(), new ActionImpl2() };
	}

	@Test
	public void stage0_verifyNoAction() {
		assertEquals(0, meg.getActions().size());
		for (ActionImpl action : actionsToAdd) {
			assertFalse(meg.getActions().contains(action));
			assertFalse(action.modified);
		}
		assertEquals(null, removedActions);
	}

	@Test
	public void stage1_0_testSize() {
		assertEquals(0, meg.size());
	}

	@Test
	public void stage1_1_verifySize() {
		assertEquals(meg.size(), meg.getActions().size());
	}

	@Test
	public void stage2_0_testAddAction() {
		for (ActionImpl action : actionsToAdd) {
			assertTrue(meg.addAction(action));
		}
	}

	@Test
	public void stage2_1_verifyAddAction() {
		for (Action action : actionsToAdd) {
			assertTrue(meg.getActions().contains(action));
		}
	}

	@Test
	public void stage3_0_testAndVerifyEachAction() {
		for (ActionImpl action : meg.getActions()) {
			assertTrue(action.enact());
			assertTrue(action.modified);
		}
	}

	@Test
	public void stage3_1_testAndVerifyEnactEachAction() {
		for (int index = 0; index < meg.size(); index++) {
			assertTrue(meg.enactNextAction());
			assertTrue(actionsToAdd[index].modified);
		}
	}

	@Test
	public void stage4_0_testRemoveAction() {
		for (ActionImpl action : meg.getActions()) {
			assertEquals(true, meg.removeAction(action));
		}
	}

	@Test
	public void stage4_1_verifyRemoveAction() {
		for (Action action : actionsToAdd) {
			assertFalse(meg.getActions().contains(action));
		}
	}

	@Test
	public void stage5_0_setupStage5() {
		removedActions = null;
		meg.clear();
		stage2_0_testAddAction();
		stage2_1_verifyAddAction();
		for (ActionImpl action : actionsToAdd) {
			action.modified = false;
		}
	}

	@Test
	public void stage5_1_testClear() {
		meg.clear();
	}

	@Test
	public void stage5_2_verifyClear() {
		for (Action action : actionsToAdd) {
			assertFalse(meg.getActions().contains(action));
		}
	}

	@Test
	public void stage6_0_setupStage6() {
		stage5_0_setupStage5();
	}

	@Test
	public void stage6_1_testRemoveActionByType() {
		removedActions = meg.removeActionByType(ActionImpl2.class);
	}

	@Test
	public void stage6_2_verifyRemoveActionByType() {
		assertTrue(removedActions.contains(actionsToAdd[2]));
		assertFalse(meg.getActions().contains(actionsToAdd[2]));
	}
	
	@Test
	public void stage7_0_setupStage7() {
		stage5_0_setupStage5();
	}

	@Test
	public void stage7_1_testEnactAllOfType() {
		meg.enactAllOfType(ActionImpl2.class);
	}

	@Test
	public void stage7_2_verifyEnactAllOfType() {
		assertFalse(actionsToAdd[0].modified);
		assertFalse(actionsToAdd[1].modified);
		assertTrue(actionsToAdd[2].modified);
	}

	@Test
	public void stage8_0_setupStage8() {
		stage5_0_setupStage5();
	}

	@Test
	public void stage4_1_testEnactAll() {
		meg.enactAll();
	}

	@Test
	public void stage4_2_verifyEnactAll() {
		for (ActionImpl action : actionsToAdd) {
			assertTrue(action.modified);
		}
	}

	@AfterClass
	public static void destroy() {
		meg = null;
		actionsToAdd = null;
	}

	private static class ActionImpl implements Action {

		public boolean modified;

		@Override
		public boolean enact() {
			modified = true;
			return true;
		}

	}

	private static class ActionImpl2 extends ActionImpl {
	}

}
