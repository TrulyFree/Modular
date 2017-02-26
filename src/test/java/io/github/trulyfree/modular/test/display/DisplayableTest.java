package io.github.trulyfree.modular.test.display;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.display.Displayable;
import io.github.trulyfree.modular.test.display.impl.SimpleDisplayable;

import static io.github.trulyfree.modular.general.Priority.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DisplayableTest {

	private static Displayable displayable;

	@BeforeClass
	public static void setup() {
		displayable = new SimpleDisplayable(AESTHETIC);
	}
	
	@Test
	public void stage0_verifyNoAction() {
		assertEquals(AESTHETIC, displayable.getPriority());
	}
	
	@Test
	public void stage1_0_testSetPriority() {
		assertTrue(displayable.setPriority(LOW));
	}
	
	@Test
	public void stage1_1_verifySetPriority() {
		assertEquals(LOW, displayable.getPriority());
	}
	
	@Test
	public void stage2_0_testCompareTo() {
		Displayable comparableDisplay = new SimpleDisplayable(AESTHETIC);
		assertTrue(comparableDisplay.compareTo(displayable) < 0);
		assertTrue(displayable.compareTo(comparableDisplay) > 0);
		assertTrue(comparableDisplay.setPriority(LOW));
		assertEquals(0, displayable.compareTo(comparableDisplay));
	}
	
	@AfterClass
	public static void destroy() {
		displayable = null;
	}

}
