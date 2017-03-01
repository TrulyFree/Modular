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
public class PrioritizedEventHandlerTest {

	private static EventHandler<PrioritizedEvent> handler;
	private static ArrayList<EventImpl> events;
		
	private static int expected;
	
	@BeforeClass
	public static void setup() {
		handler = new PrioritizedEventHandler();
		events = new ArrayList<EventImpl>();
		
		
		for (int i = 1; i <= 10; i++) {
			events.add(new EventImpl(i));
		}
		expected = 1;
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
	}
	
	@Test
	public void stage3_0_testAndVerifyEachEvent() {
		for (Event event : handler.getEvents()) {
			assertTrue(event.enact());
		}
	}
	
	@Test
	public void stage3_1_testAndVerifyEnactEachEvent() {
		expected = 1;
		while (handler.enactNextEvent()) {}
		assertEquals(11, expected);
	}
	
	@Test
	public void stage3_2_verifyEnactEachEventRemoval() {
		assertEquals(0, handler.size());
	}
	
	@Test
	public void stage4_0_setupStage4() {
		stage2_0_testAddEvent();
		expected = 1;
	}
	
	@Test
	public void stage4_1_testEnact() {
		handler.enact();
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
		assertEquals(expected++, val);
	}
	
	private static class EventImpl implements PrioritizedEvent {
		private final int val;
		
		public EventImpl(int val) {
			this.val = val;
		}
			
		@Override
		public boolean enact() {
			modify(val);
			return true;
		}

		@Override
		public Priority getPriority() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
}
