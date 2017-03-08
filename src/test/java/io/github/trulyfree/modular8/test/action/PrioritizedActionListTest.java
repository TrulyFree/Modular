package io.github.trulyfree.modular8.test.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular8.action.PrioritizedAction;
import io.github.trulyfree.modular8.general.Priority;
import io.github.trulyfree.modular8.test.action.impl.SimplePrioritizedAction;

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
public class PrioritizedActionListTest {

	private static int modified = 0;
	private static List<PrioritizedAction> actionList, compare;

	@BeforeClass
	public static void setup() {
		actionList = new ArrayList<PrioritizedAction>(Priority.values().length);
		compare = new ArrayList<PrioritizedAction>(Priority.values().length);

		for (int i = 0; i < actionList.size(); i++) {
			final int intermediary = i;
			actionList.add(new SimplePrioritizedAction(Priority.values()[i]) {
				@Override
				public boolean enact() {
					modified = intermediary + 1;
					return true;
				}
			});
			compare.add(new SimplePrioritizedAction(Priority.values()[i]) {
				@Override
				public boolean enact() {
					return true;
				}
			});
		}
	}

	@Test
	public void stage0_verifyNoAction() {
		assertEquals(0, modified);
	}

	@Test
	public void stage1_testAndVerifyEnactEach() {
		for (int i = 0; i < actionList.size(); i++) {
			actionList.get(i).enact();
			assertEquals(i + 1, modified);
		}
	}

	@Test
	public void stage2_0_shuffleActions() {
		Collections.reverse(actionList);
	}

	@Test
	public void stage2_1_verifyShuffleActions() {
		boolean succeed;
		for (PrioritizedAction action1 : compare) {
			succeed = false;
			for (PrioritizedAction action2 : actionList) {
				if (action1.compareTo(action2) == 0) {
					succeed = true;
					break;
				}
			}
			if (!succeed) {
				fail();
			}
		}
	}

	@Test
	public void stage3_0_orderActions() {
		Collections.sort(actionList);
	}

	@Test
	public void stage3_1_verifyOrderActions() {
		for (int i = 0; i < actionList.size(); i++) {
			assertEquals(0, actionList.get(i).compareTo(compare.get(i)));
		}
	}

	@Test
	public void stage4_testAndVerifyEnactEach() {
		stage1_testAndVerifyEnactEach();
	}

	@AfterClass
	public static void destroy() {
		actionList = null;
		compare = null;
		modified = 0;
	}

}
