package io.github.trulyfree.modular.test.event;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.trulyfree.modular.event.EventPriority;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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
public class EventPriorityTest {
	
	private static List<EventPriority> priorities;
	
	@BeforeClass
	public static void setup() {
		priorities = new ArrayList<EventPriority>(EventPriority.values().length);
		
		for (EventPriority priority : EventPriority.values()) {
			priorities.add(priority);
		}
	}
	
	@Test
	public void stage0_verifyNoAction() {
		for (int i = 0; i < priorities.size(); i++) {
			assertTrue(EventPriority.values()[i] == priorities.get(i));
		}
	}
	
	@Test
	public void stage1_shufflePriorities() {
		Collections.reverse(priorities); // We don't want exact list
	}
	
	@Test
	public void stage2_verifyShufflePriorities() {
		for (EventPriority priority : EventPriority.values()) {
			assertTrue(priorities.contains(priority));
		}
	}
	
	@Test
	public void stage3_orderPriorities() {
		Collections.sort(priorities);
	}
	
	@Test
	public void stage4_verifyOrderPriorities() {
		stage0_verifyNoAction();
	}
	
	@AfterClass
	public static void destroy() {
		priorities = null;
	}

}
