package io.github.trulyfree.modular.test.integrate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.event.Event;
import io.github.trulyfree.modular.test.integrate.impl.SimpleForkableEvent;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ForkableEventTest {

	private static SimpleForkableEvent sfe;

	public static volatile boolean finished;

	@Test
	public void stage0_0_setup() {
		finished = false;
		List<Event> events = new ArrayList<Event>();
		events.add(new Event() {

			@Override
			public boolean enact() {
				sfe.increment();
				return true;
			}

		});
		sfe = new SimpleForkableEvent(events);
	}

	@Test
	public void stage0_1_verifyNoAction() {
		assertFalse(finished);
		assertEquals(0, sfe.value);
	}

	@Test
	public void stage1_0_testSafeHalt() {
		assertTrue(sfe.enact());
		synchronized (sfe) {
			try {
				sfe.wait();
				assertTrue(sfe.safeHalt());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void stage1_1_verifySafeHalt() {
		assertTrue(sfe.value > 10000);
		assertTrue(finished);
	}
	
	@Test
	public void stage2_0_setupStage2() {
		stage0_0_setup();
	}
	
	@Test
	public void stage2_1_testImmediateHalt() {
		assertTrue(sfe.enact());
		synchronized (sfe) {
			try {
				sfe.wait();
				assertTrue(sfe.immediateHalt());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void stage2_2_verifyImmediateHalt() {
		assertTrue(sfe.value > 10000);
		assertFalse(finished);
	}

}
