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
import io.github.trulyfree.modular.test.action.impl.SimpleEventGroup;

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
public class EventGroupTest {

	private static ActionGroup<EventImpl> eg;

	private static EventImpl event1;
	private static EventImpl2 event2;

	@BeforeClass
	public static void setup() {
		event1 = new EventImpl();

		event2 = new EventImpl2();

		List<EventImpl> events = new ArrayList<EventImpl>(2);
		events.add(event1);
		events.add(event2);

		eg = new SimpleEventGroup<EventImpl>(events);
	}

	@Test
	public void stage0_verifyNoAction() {
		for (EventImpl event : eg.getActions()) {
			assertFalse(event.modified);
		}
	}

	@Test
	public void stage1_0_testEnactNextEvent() {
		assertTrue(eg.enactNextEvent());
	}

	@Test
	public void stage1_1_verifyEnactNextEvent() {
		assertTrue(event1.modified);
	}

	@Test
	public void stage2_0_testEnactNextEvent() {
		stage1_0_testEnactNextEvent();
	}

	@Test
	public void stage2_1_verifyEnactNextEvent() {
		assertTrue(event2.modified);
	}
	
	@Test
	public void stage3_0_setupStage3() {
		event1.modified = false;
		event2.modified = false;
	}
	
	@Test
	public void stage3_1_testEnactAllOfType() {
		eg.enactAllOfType(EventImpl2.class);
	}
	
	@Test
	public void stage3_2_verifyEnactAllOfType() {
		assertFalse(event1.modified);
		assertTrue(event2.modified);
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
		assertTrue(event1.modified);
		assertTrue(event2.modified);
	}

	@AfterClass
	public static void destroy() {
		event1 = null;
		event2 = null;
		eg = null;
	}

	private static class EventImpl implements Action {

		public boolean modified;
		
		@Override
		public boolean enact() {
			modified = true;
			return true;
		}
		
	}
	
	private static class EventImpl2 extends EventImpl {}
	
}
