package io.github.trulyfree.modular.test.event;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.event.EventPriority;
import io.github.trulyfree.modular.event.PrioritizedEvent;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrioritizedEventGroupTest {

	private static int modified = 0;
	private static List<PrioritizedEvent> eventList, compare;

	@BeforeClass
	public static void setup() {
		eventList = new ArrayList<PrioritizedEvent>(EventPriority.values().length);
		compare = new ArrayList<PrioritizedEvent>(EventPriority.values().length);

		for (int i = 0; i < eventList.size(); i++) {
			final int intermediary = i;
			eventList.add(new SimplePrioritizedEvent(EventPriority.values()[i]) {
				@Override
				public boolean enact() {
					modified = intermediary + 1;
					return true;
				}
			});
			compare.add(new SimplePrioritizedEvent(EventPriority.values()[i]) {
				@Override
				public boolean enact() {
					return true;
				}
			});
		}
	}

	@Test
	public void stage0_verifyNoAction() {
		assertEquals(0, modified);
	}

	@Test
	public void stage1_testAndVerifyEnactEach() {
		for (int i = 0; i < eventList.size(); i++) {
			eventList.get(i).enact();
			assertEquals(i + 1, modified);
		}
	}

	@Test
	public void stage2_shuffleEvents() {
		Collections.reverse(eventList);
	}

	@Test
	public void stage3_verifyShuffleEvents() {
		boolean succeed;
		for (PrioritizedEvent event1 : compare) {
			succeed = false;
			for (PrioritizedEvent event2 : eventList) {
				if (event1.compareTo(event2) == 0) {
					succeed = true;
					break;
				}
			}
			if (!succeed) {
				fail();
			}
		}
	}
	
	@Test
	public void stage4_orderEvents() {
		Collections.sort(eventList);
	}
	
	@Test
	public void stage5_verifyOrderEvents() {
		for (int i = 0; i < eventList.size(); i++) {
			assertEquals(0, eventList.get(i).compareTo(compare.get(i)));
		}
	}
	
	@Test
	public void stage6_testAndVerifyEnactEach() {
		stage1_testAndVerifyEnactEach();
	}
	
	@AfterClass
	public static void destroy() {
		eventList = null;
		compare = null;
		modified = 0;
	}

}
