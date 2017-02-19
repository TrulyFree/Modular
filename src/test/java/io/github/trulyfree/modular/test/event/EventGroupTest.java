package io.github.trulyfree.modular.test.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.event.EventGroup;

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

	private static int modified;

	private static EventGroup<Event> eg;

	@BeforeClass
	public static void setup() {
		modified = 0;

		Event event1, event2;
		
		event1 = new Event() {
			@Override
			public boolean enact() {
				modified = 1;
				return true;
			}
		};

		event2 = new Event() {
			@Override
			public boolean enact() {
				modified = 0;
				return true;
			}
		};
		
		List<Event> events = new ArrayList<Event>(2);
		events.add(event1);
		events.add(event2);
		
		eg = new SimpleEventGroup(events);
	}

	@Test
	public void stage0_verifyNoAction() {
		assertEquals(0, modified);
	}

	@Test
	public void stage1_testEnactNextEvent() {
		assertTrue(eg.enactNextEvent());
	}

	@Test
	public void stage2_verifyEnactNextEvent() {
		assertEquals(1, modified);
	}

	@Test
	public void stage3_testEnactNextEvent() {
		stage1_testEnactNextEvent();
	}

	@Test
	public void stage4_verifyEnactNextEvent() {
		assertEquals(0, modified);
	}

	@AfterClass
	public static void destroy() {
		modified = 0;
		eg = null;
	}
	
}
