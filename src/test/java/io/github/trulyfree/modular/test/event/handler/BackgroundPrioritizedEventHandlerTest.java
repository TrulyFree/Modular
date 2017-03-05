package io.github.trulyfree.modular.test.event.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.event.PrioritizedEvent;
import io.github.trulyfree.modular.event.handlers.EventHandler;
import io.github.trulyfree.modular.event.handlers.PrioritizedEventHandler;
import io.github.trulyfree.modular.general.Priority;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BackgroundPrioritizedEventHandlerTest {

	private static EventHandler<PrioritizedEvent> handler;
	private static ArrayList<EventImpl> events;
		
	private static int expected;
	
	@BeforeClass
	public static void setup() {
		handler = new PrioritizedEventHandler();
		events = new ArrayList<EventImpl>();
		
		for (Priority priority : Priority.values()) {
			events.add(new EventImpl(priority.ordinal(), priority));
		}
		expected = Integer.MAX_VALUE;
	}
	
	@Test
	public void stage0_verifyNoAction() {
		assertFalse(handler.isReady());
	}
	
	@Test
	public void stage1_0_testSetup() {
		assertTrue(handler.setup());
	}
	
	@Test
	public void stage1_1_verifySetup() {
		assertTrue(handler.isReady());
		assertEquals(0, handler.getEvents().size());
		for (EventImpl event : events) {
			assertFalse(handler.getEvents().contains(event));
		}
	}
	
	@Test
	public void stage2_0_testAddEvent() {
		for (EventImpl event : events) {
			assertTrue(handler.addEvent(event));
		}
	}
	
	@Test
	public void stage2_1_verifyAddEvent() {
		for (Event event : events) {
			assertTrue(handler.getEvents().contains(event));
		}
	}
	
	@Test
	public void stage2_2_checkAddEvent() {
		assertFalse(handler.addEvent(null));
		assertFalse(handler.addEvent(new EventImpl(0, null)));
	}
	
	@Test
	public void stage3_0_testAndVerifyEachEvent() {
		for (Event event : handler.getEvents()) {
			assertTrue(event.enact());
		}
		assertEquals(Priority.AESTHETIC.ordinal(), expected);
	}
	
	@Test
	public void stage3_1_testAndVerifyEnactEachEvent() {
		expected = Integer.MAX_VALUE;
		while (handler.enactNextEvent()) {}
		assertEquals(Priority.AESTHETIC.ordinal(), expected);
	}
	
	@Test
	public void stage3_2_verifyEnactEachEventRemoval() {
		assertEquals(0, handler.size());
	}
	
	@Test
	public void stage4_0_setupStage4() {
		stage2_0_testAddEvent();
		expected = Integer.MAX_VALUE;
	}
	
	@Test
	public void stage4_1_testEnact() {
		assertTrue(handler.enact());
	}
	
	@Test
	public void stage4_2_verifyEnactRemoval() {
		stage3_2_verifyEnactEachEventRemoval();
	}
	
	@Test
	public void stage5_0_setupStage4() {
		stage2_0_testAddEvent();
	}
	
	@Test
	public void stage5_1_testClear() {
		handler.clear();
	}
	
	@Test
	public void stage5_2_verifyClear() {
		stage3_2_verifyEnactEachEventRemoval();
	}
	
	@AfterClass
	public static void destroy() {
		handler = null;
		events = null;
		expected = 0;
	}
	
	private static void modify(int val) {
		assertTrue(val < expected);
		expected = val;
	}
	
	private static class EventImpl implements PrioritizedEvent {
		private final int val;
		private final Priority priority;
		
		public EventImpl(int val, Priority priority) {
			this.val = val;
			this.priority = priority;
		}
			
		@Override
		public boolean enact() {
			modify(val);
			return true;
		}

		@Override
		public Priority getPriority() {
			return priority;
		}
	}
	
}
