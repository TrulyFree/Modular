package io.github.trulyfree.modular.test.general;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.general.Sortable;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SortableTest {

	private static SimpleSortable sortable;

	@BeforeClass
	public static void setup() {
		sortable = new SimpleSortable();
		ArrayList<Integer> numbers = new ArrayList<Integer>(10);
		for (int i = 0; i < 10; i++) {
			numbers.add(i);
		}
		Collections.shuffle(numbers);
		sortable.addAll(numbers);
	}
	
	@Test
	public void stage0_verifyNoAction() {
		assertNotEquals(null, sortable);
		for (int i = 0; i < 10; i++) {
			assertTrue(sortable.contains(new Integer(i)));
		}
	}
	
	@Test
	public void stage1_0_testSort() {
		assertTrue(sortable.sort());
	}
	
	@Test
	public void stage1_1_verifySetData() {
		for (int i = 0; i < 10; i++) {
			assertEquals(i, sortable.get(i).intValue());
		}
	}
	
	@AfterClass
	public static void destroy() {
		sortable = null;
	}
	
	@SuppressWarnings("serial")
	private static class SimpleSortable extends ArrayList<Integer> implements Sortable {

		@Override
		public boolean sort() {
			Collections.sort(this);
			return true;
		}
		
	}

}
