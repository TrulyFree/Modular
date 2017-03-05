package io.github.trulyfree.modular.test.action.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.action.Action;
import io.github.trulyfree.modular.action.PrioritizedAction;
import io.github.trulyfree.modular.action.handlers.ActionHandler;
import io.github.trulyfree.modular.action.handlers.BackgroundPrioritizedActionHandler;
import io.github.trulyfree.modular.general.Priority;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BackgroundPrioritizedEventHandlerTest {

	private static ActionHandler<PrioritizedAction> handler;
	private static ArrayList<ArrayList<EventImpl>> events;

	private static StringBuffer check;

	private static Thread targetThread;

	@BeforeClass
	public static void setup() {
		handler = new BackgroundPrioritizedActionHandler(1);
		events = new ArrayList<>();
		for (@SuppressWarnings("unused")
		Priority priority : Priority.values()) {
			events.add(new ArrayList<EventImpl>());
		}

		for (ArrayList<EventImpl> list : events) {
			for (Priority priority : Priority.values()) {
				list.add(new EventImpl(priority));
			}
		}

		check = new StringBuffer();

		try {
			Field f = handler.getClass().getDeclaredField("watcherThread"); // NoSuchFieldException
			f.setAccessible(true);
			targetThread = (Thread) f.get(handler);
		} catch (Exception e) {
			fail();
		}
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
		for (ArrayList<EventImpl> list : events) {
			for (EventImpl event : list) {
				assertFalse(handler.getEvents().contains(event));
			}
		}
	}

	@Test
	public void stage2_0_testAddEvent() {
		for (ArrayList<EventImpl> list : events) {
			for (EventImpl event : list) {
				assertTrue(handler.addEvent(event));
			}
		}
	}

	@Test
	public void stage2_1_verifyAddEvent() {
		for (ArrayList<EventImpl> list : events) {
			for (Action action : list) {
				assertTrue(handler.getEvents().contains(action));
			}
		}
	}

	@Test
	public void stage2_2_checkAddEvent() {
		assertFalse(handler.addEvent(null));
		assertFalse(handler.addEvent(new EventImpl(null)));
	}

	@Test
	public void stage3_0_testAndVerifyEachEvent() {
		for (Action action : handler.getEvents()) {
			assertTrue(action.enact());
		}
		assertEquals(Priority.values().length * Priority.values().length, check.length());
	}

	@Test
	public void stage3_1_testAndVerifyEnactEachEvent() {
		check = new StringBuffer();
		while (handler.enactNextEvent()) {
		}
		assertEquals(Priority.values().length * Priority.values().length, check.length());
	}

	@Test
	public void stage3_2_verifyEnactEachEventRemoval() {
		assertEquals(0, handler.size());
	}

	@Test
	public void stage4_0_setupStage4() {
		stage2_0_testAddEvent();
		check = new StringBuffer();
	}

	@Test
	public void stage4_1_testEnact() {
		assertTrue(handler.enact());
	}

	@Test
	public void stage4_2_verifyEnactRemoval() {
		sleepUntilWaiting();
		stage3_2_verifyEnactEachEventRemoval();
	}

	@Test
	public void stage4_3_verifyEnact() {
		assertEquals(Priority.values().length * Priority.values().length, check.length());
	}

	@Test
	public void stage5_0_testConcurrentAdd() {
		handler.addEvent(new EventImpl(Priority.AESTHETIC));
	}

	@Test
	public void stage5_1_verifyConcurrentAdd() {
		sleepUntilWaiting();
		assertEquals(Priority.values().length * Priority.values().length + 1, check.length());
	}

	@Test
	public void stage6_0_setupStage4() {
		stage2_0_testAddEvent();
	}

	@Test
	public void stage6_1_testClear() {
		handler.clear();
	}

	@Test
	public void stage6_2_verifyClear() {
		stage3_2_verifyEnactEachEventRemoval();
	}

	@AfterClass
	public static void destroy() {
		((BackgroundPrioritizedActionHandler) handler).safeHalt();
		handler = null;
		events = null;
		check = null;
	}

	private static void sleepUntilWaiting() {
		while (targetThread.getState() != Thread.State.WAITING)
			;
	}

	private static class EventImpl implements PrioritizedAction {
		private final Priority priority;

		public EventImpl(Priority priority) {
			this.priority = priority;
		}

		@Override
		public boolean enact() {
			check.append(" ");
			System.out.println("Double check.");
			return true;
		}

		@Override
		public Priority getPriority() {
			return priority;
		}
	}

}
