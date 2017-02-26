package io.github.trulyfree.modular.test.display;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.display.DisplayableModule;
import io.github.trulyfree.modular.general.Priority;
import io.github.trulyfree.modular.test.display.impl.SimpleDisplayable;
import io.github.trulyfree.modular.test.display.impl.SimpleDisplayableModule;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DisplayableModuleTest {

	private static DisplayableModule module;

	@BeforeClass
	public static void setup() {
		Priority[] priorities = Priority.values();
		List<SimpleDisplayable> displayables = new ArrayList<SimpleDisplayable>(priorities.length);
		for (Priority priority : priorities) {
			displayables.add(new SimpleDisplayable(priority));
		}
		module = new SimpleDisplayableModule(displayables);
	}
	
	@Test
	public void stage0_verifyNoAction() {
		assertNotEquals(null, module);
	}
	
	@AfterClass
	public static void destroy() {
		module = null;
	}

}
