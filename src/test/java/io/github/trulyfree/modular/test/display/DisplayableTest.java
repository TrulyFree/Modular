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

	private static Displayable disp;

	@BeforeClass
	public static void setup() {
		disp = new SimpleDisplayable(AESTHETIC);
	}
	
	@Test
	public void stage0_verifyNoAction() {
		assertEquals(AESTHETIC, disp.getPriority());
	}
	
	@Test
	public void stage1_0_testSetPriority() {
		assertTrue(disp.setPriority(LOW));
	}
	
	@Test
	public void stage1_1_verifySetPriority() {
		assertEquals(LOW, disp.getPriority());
	}
	
	@AfterClass
	public static void destroy() {
		disp = null;
	}

}
