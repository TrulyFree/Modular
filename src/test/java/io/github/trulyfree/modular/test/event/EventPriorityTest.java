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
