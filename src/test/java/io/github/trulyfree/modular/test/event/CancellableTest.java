package io.github.trulyfree.modular.test.event;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular.event.Cancellable;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CancellableTest {
	
	private static Cancellable cancellable;
	
	@BeforeClass
	public static void setup() {
		cancellable = new Cancellable() {
			private boolean cancelled;
			
			@Override
			public boolean setCancelled(boolean cancelled) {
				this.cancelled = cancelled;
				return true;
			}

			@Override
			public boolean isCancelled() {
				return cancelled;
			}
		};
	}
	
	@Test
	public void stage0_verifyNoAction() {
		assertFalse(cancellable.isCancelled());
	}
	
	@Test
	public void stage1_testSetCancelled() {
		assertTrue(cancellable.setCancelled(true));
	}
	
	@Test
	public void stage2_verifySetCancelled() {
		assertTrue(cancellable.isCancelled());
	}
	
	@Test
	public void stage3_testSetCancelledFalse() {
		assertTrue(cancellable.setCancelled(false));
	}
	
	@Test
	public void stage4_verifySetCancelledFalse() {
		stage0_verifyNoAction();
	}
	
	@AfterClass
	public static void destroy() {
		cancellable = null;
	}
	
}
