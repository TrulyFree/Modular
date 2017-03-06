package io.github.trulyfree.modular.test.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.action.Action;
import io.github.trulyfree.modular.action.ActionGroup;
import io.github.trulyfree.modular.test.action.impl.SimpleActionGroup;

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
public class ActionGroupTest {

	private static ActionGroup<ActionImpl> eg;

	private static ActionImpl action1;
	private static ActionImpl2 action2;

	@BeforeClass
	public static void setup() {
		action1 = new ActionImpl();

		action2 = new ActionImpl2();

		List<ActionImpl> actions = new ArrayList<ActionImpl>(2);
		actions.add(action1);
		actions.add(action2);

		eg = new SimpleActionGroup<ActionImpl>(actions);
	}

	@Test
	public void stage0_verifyNoAction() {
		for (ActionImpl action : eg.getActions()) {
			assertFalse(action.modified);
		}
	}

	@Test
	public void stage1_0_testEnactNextAction() {
		assertTrue(eg.enactNextAction());
	}

	@Test
	public void stage1_1_verifyEnactNextAction() {
		assertTrue(action1.modified);
	}

	@Test
	public void stage2_0_testEnactNextAction() {
		stage1_0_testEnactNextAction();
	}

	@Test
	public void stage2_1_verifyEnactNextAction() {
		assertTrue(action2.modified);
	}
	
	@Test
	public void stage3_0_setupStage3() {
		action1.modified = false;
		action2.modified = false;
	}
	
	@Test
	public void stage3_1_testEnactAllOfType() {
		eg.enactAllOfType(ActionImpl2.class);
	}
	
	@Test
	public void stage3_2_verifyEnactAllOfType() {
		assertFalse(action1.modified);
		assertTrue(action2.modified);
	}
	
	@Test
	public void stage4_0_setupStage4() {
		stage3_0_setupStage3();
	}
	
	@Test
	public void stage4_1_testEnactAll() {
		eg.enactAll();
	}
	
	@Test
	public void stage4_2_verifyEnactAll() {
		assertTrue(action1.modified);
		assertTrue(action2.modified);
	}

	@AfterClass
	public static void destroy() {
		action1 = null;
		action2 = null;
		eg = null;
	}

	private static class ActionImpl implements Action {

		public boolean modified;
		
		@Override
		public boolean enact() {
			modified = true;
			return true;
		}
		
	}
	
	private static class ActionImpl2 extends ActionImpl {}
	
}
