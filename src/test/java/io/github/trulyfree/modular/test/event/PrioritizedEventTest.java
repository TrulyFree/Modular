package io.github.trulyfree.modular.test.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.event.EventPriority;
import io.github.trulyfree.modular.event.PrioritizedEvent;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrioritizedEventTest {

	private static int modified;

	private static PrioritizedEvent event1;
	private static PrioritizedEvent event2;

	@BeforeClass
	public static void setup() {
		modified = 0;

		event1 = new PrioritizedEvent() {
			@Override
			public boolean enact() {
				modified = 1;
				return true;
			}

			@Override
			public EventPriority getPriority() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean setPriority(EventPriority priority) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		event2 = new PrioritizedEvent() {
			@Override
			public boolean enact() {
				modified = 0;
				return true;
			}

			@Override
			public EventPriority getPriority() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean setPriority(EventPriority priority) {
				// TODO Auto-generated method stub
				return false;
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

	@AfterClass
	public static void destroy() {
		modified = 0;
		event1 = null;
		event2 = null;
	}

}
