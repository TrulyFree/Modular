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
import io.github.trulyfree.modular.test.action.impl.SimpleModifiableEventGroup;

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

	private static ModifiableActionGroup<EventImpl> meg;
	private static EventImpl[] eventsToAdd;

	private static Collection<EventImpl> removedEvents;

	@BeforeClass
	public static void setup() {
		meg = new SimpleModifiableEventGroup<EventImpl>();

		eventsToAdd = new EventImpl[] { new EventImpl(), new EventImpl(), new EventImpl2() };
	}

	@Test
	public void stage0_verifyNoAction() {
		assertEquals(0, meg.getEvents().size());
		for (EventImpl event : eventsToAdd) {
			assertFalse(meg.getEvents().contains(event));
			assertFalse(event.modified);
		}
		assertEquals(null, removedEvents);
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
		for (EventImpl event : eventsToAdd) {
			assertTrue(meg.addEvent(event));
		}
	}

	@Test
	public void stage2_1_verifyAddEvent() {
		for (Action action : eventsToAdd) {
			assertTrue(meg.getEvents().contains(action));
		}
	}

	@Test
	public void stage3_0_testAndVerifyEachEvent() {
		for (EventImpl event : meg.getEvents()) {
			assertTrue(event.enact());
			assertTrue(event.modified);
		}
	}

	@Test
	public void stage3_1_testAndVerifyEnactEachEvent() {
		for (int index = 0; index < meg.size(); index++) {
			assertTrue(meg.enactNextEvent());
			assertTrue(eventsToAdd[index].modified);
		}
	}

	@Test
	public void stage4_0_testRemoveEvent() {
		for (EventImpl event : meg.getEvents()) {
			assertEquals(event, meg.removeEvent(event));
		}
	}

	@Test
	public void stage4_1_verifyRemoveEvent() {
		for (Action action : eventsToAdd) {
			assertFalse(meg.getEvents().contains(action));
		}
	}

	@Test
	public void stage5_0_setupStage5() {
		removedEvents = null;
		meg.clear();
		stage2_0_testAddEvent();
		stage2_1_verifyAddEvent();
		for (EventImpl event : eventsToAdd) {
			event.modified = false;
		}
	}

	@Test
	public void stage5_1_testClear() {
		meg.clear();
	}

	@Test
	public void stage5_2_verifyClear() {
		for (Action action : eventsToAdd) {
			assertFalse(meg.getEvents().contains(action));
		}
	}

	@Test
	public void stage6_0_setupStage6() {
		stage5_0_setupStage5();
	}

	@Test
	public void stage6_1_testRemoveEventByType() {
		removedEvents = meg.removeEventByType(EventImpl2.class);
	}

	@Test
	public void stage6_2_verifyRemoveEventByType() {
		assertTrue(removedEvents.contains(eventsToAdd[2]));
		assertFalse(meg.getEvents().contains(eventsToAdd[2]));
	}
	
	@Test
	public void stage7_0_setupStage7() {
		stage5_0_setupStage5();
	}

	@Test
	public void stage7_1_testEnactAllOfType() {
		meg.enactAllOfType(EventImpl2.class);
	}

	@Test
	public void stage7_2_verifyEnactAllOfType() {
		assertFalse(eventsToAdd[0].modified);
		assertFalse(eventsToAdd[1].modified);
		assertTrue(eventsToAdd[2].modified);
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
		for (EventImpl event : eventsToAdd) {
			assertTrue(event.modified);
		}
	}

	@AfterClass
	public static void destroy() {
		meg = null;
		eventsToAdd = null;
	}

	private static class EventImpl implements Action {

		public boolean modified;

		@Override
		public boolean enact() {
			modified = true;
			return true;
		}

	}

	private static class EventImpl2 extends EventImpl {
	}

}
