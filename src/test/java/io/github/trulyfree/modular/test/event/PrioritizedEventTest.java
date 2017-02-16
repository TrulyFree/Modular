package io.github.trulyfree.modular.test.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static io.github.trulyfree.modular.event.EventPriority.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.event.PrioritizedEvent;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrioritizedEventTest {

	private static int modified;

	private static PrioritizedEvent event1;
	private static PrioritizedEvent event2;

	@BeforeClass
	public static void setup() {
		modified = 0;

		event1 = new SimplePrioritizedEvent(DIRE) {
			@Override
			public boolean enact() {
				modified = 1;
				return true;
			}
		};

		event2 = new SimplePrioritizedEvent(AESTHETIC) {
			@Override
			public boolean enact() {
				modified = 0;
				return true;
			}
		};
	}

	@Test
	public void stage0_verifyNoAction() {
		assertEquals(0, modified);
	}

	@Test
	public void stage1_testEnact() {
		assertTrue(event1.enact());
	}

	@Test
	public void stage2_verifyEnact() {
		assertEquals(1, modified);
	}

	@Test
	public void stage3_testEnact() {
		assertTrue(event2.enact());
	}

	@Test
	public void stage4_verifyEnact() {
		assertEquals(0, modified);
	}
	
	@Test
	public void stage5_testGetPriority() {
		assertTrue(event1.getPriority() == DIRE);
		assertTrue(event2.getPriority() == AESTHETIC);
	}
	
	@Test
	public void stage6_testSetPriority() {
		assertTrue(event1.setPriority(HIGH));
		assertTrue(event2.setPriority(IMPORTANT));
	}
	
	@Test
	public void stage7_verifySetPriority() {
		assertTrue(event1.getPriority() == HIGH);
		assertTrue(event2.getPriority() == IMPORTANT);
	}
	
	@Test
	public void stage8_testCompareTo() {
		assertEquals(event1.compareTo(event2), 1);
		assertEquals(event2.compareTo(event1), -1);
		assertEquals(event1.compareTo(event1), 0);
	}

	@AfterClass
	public static void destroy() {
		modified = 0;
		event1 = null;
		event2 = null;
	}

}
