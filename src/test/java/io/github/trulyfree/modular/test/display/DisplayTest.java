package io.github.trulyfree.modular.test.display;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.test.display.impl.SimpleDisplay;
import io.github.trulyfree.modular.test.display.impl.SimpleDisplayableModule;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DisplayTest {

	private static SimpleDisplay display;
	private static SimpleDisplayableModule successful;
	private static SimpleDisplayableModule failure;

	@BeforeClass
	public static void setup() {
		display = new SimpleDisplay();
	}

}
