package io.github.trulyfree.modular.test.event;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.event.EventPriority;
import io.github.trulyfree.modular.event.PrioritizedEvent;
import io.github.trulyfree.modular.test.event.impl.SimplePrioritizedEvent;

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
public class PrioritizedEventGroupTest {

	private static int modified = 0;
	private static List<PrioritizedEvent> eventList, compare;

	@BeforeClass
	public static void setup() {
		eventList = new ArrayList<PrioritizedEvent>(EventPriority.values().length);
		compare = new ArrayList<PrioritizedEvent>(EventPriority.values().length);

		for (int i = 0; i < eventList.size(); i++) {
			final int intermediary = i;
			eventList.add(new SimplePrioritizedEvent(EventPriority.values()[i]) {
				@Override
				public boolean enact() {
					modified = intermediary + 1;
					return true;
				}
			});
			compare.add(new SimplePrioritizedEvent(EventPriority.values()[i]) {
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
		for (int i = 0; i < eventList.size(); i++) {
			eventList.get(i).enact();
			assertEquals(i + 1, modified);
		}
	}

	@Test
	public void stage2_shuffleEvents() {
		Collections.reverse(eventList);
	}

	@Test
	public void stage3_verifyShuffleEvents() {
		boolean succeed;
		for (PrioritizedEvent event1 : compare) {
			succeed = false;
			for (PrioritizedEvent event2 : eventList) {
				if (event1.compareTo(event2) == 0) {
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
	public void stage4_orderEvents() {
		Collections.sort(eventList);
	}
	
	@Test
	public void stage5_verifyOrderEvents() {
		for (int i = 0; i < eventList.size(); i++) {
			assertEquals(0, eventList.get(i).compareTo(compare.get(i)));
		}
	}
	
	@Test
	public void stage6_testAndVerifyEnactEach() {
		stage1_testAndVerifyEnactEach();
	}
	
	@AfterClass
	public static void destroy() {
		eventList = null;
		compare = null;
		modified = 0;
	}

}
