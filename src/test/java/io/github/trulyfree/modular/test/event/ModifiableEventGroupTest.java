package io.github.trulyfree.modular.test.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.event.ModifiableEventGroup;
import io.github.trulyfree.modular.test.event.impl.SimpleModifiableEventGroup;

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
public class ModifiableEventGroupTest {

	private static ModifiableEventGroup<Event> meg;
	private static Event[] eventsToAdd;

	private static int modified;

	private static Collection<Event> removedEvents;

	@BeforeClass
	public static void setup() {
		meg = new SimpleModifiableEventGroup<Event>();

		eventsToAdd = new Event[] { new EventImpl(1), new EventImpl(2), new EventImpl(3) };
	}

	@Test
	public void stage0_verifyNoAction() {
		assertEquals(0, meg.getEvents().size());
		for (Event event : eventsToAdd) {
			assertFalse(meg.getEvents().contains(event));
		}
		assertEquals(null, removedEvents);
		assertEquals(0, modified);
	}

	@Test
	public void stage1_0_testSize() {
		assertEquals(0, meg.size());
	}

	@Test
	public void stage1_1_verifySize() {
		assertEquals(meg.size(), meg.getEvents().size());
	}

	@Test
	public void stage2_0_testAddEvent() {
		for (Event event : eventsToAdd) {
			assertTrue(meg.addEvent(event));
		}
	}

	@Test
	public void stage2_1_verifyAddEvent() {
		for (Event event : eventsToAdd) {
			assertTrue(meg.getEvents().contains(event));
		}
	}

	@Test
	public void stage3_0_testAndVerifyEachEvent() {
		int expected = 1;
		for (Event event : meg.getEvents()) {
			assertTrue(event.enact());
			assertEquals(expected, modified);
			expected++;
		}
	}

	@Test
	public void stage3_1_testAndVerifyEnactEachEvent() {
		for (int expected = 1; expected < meg.size() + 1; expected++) {
			assertTrue(meg.enactNextEvent());
			assertEquals(expected, modified);
		}
	}

	@Test
	public void stage4_0_testRemoveEvent() {
		for (Event event : meg.getEvents()) {
			assertEquals(event, meg.removeEvent(event));
		}
	}

	@Test
	public void stage4_1_verifyRemoveEvent() {
		for (Event event : eventsToAdd) {
			assertFalse(meg.getEvents().contains(event));
		}
	}

	@Test
	public void stage5_0_setupStage5() {
		stage2_0_testAddEvent();
		stage2_1_verifyAddEvent();
	}

	@Test
	public void stage5_1_testRemoveEventByType() {
		removedEvents = meg.removeEventByType(eventsToAdd[0].getClass());
	}

	@Test
	public void stage5_2_verifyRemoveEventByType() {
		for (Event event : eventsToAdd) {
			assertTrue(removedEvents.contains(event));
			assertFalse(meg.getEvents().contains(event));
		}
	}

	@Test
	public void stage6_0_setupStage6() {
		removedEvents = null;
		stage2_0_testAddEvent();
		stage2_1_verifyAddEvent();
	}

	@Test
	public void stage6_1_testClear() {
		meg.clear();
	}

	@Test
	public void stage6_2_verifyClear() {
		for (Event event : eventsToAdd) {
			assertFalse(meg.getEvents().contains(event));
		}
	}

	@AfterClass
	public static void destroy() {
		modified = 0;
		meg = null;
	}

	public static class EventImpl implements Event {

		private final int val;

		public EventImpl(int val) {
			this.val = val;
		}

		@Override
		public boolean enact() {
			modified = val;
			return true;
		}

	}

}
