package io.github.trulyfree.modular8.test.general;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import io.github.trulyfree.modular8.general.DataCarrier;

/* Modular8 library by TrulyFree: A general-use module-building library.
 * Copyright (C) 2016  VTCAKAVSMoACE
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataCarrierTest {

	private static DataCarrier<Integer> dc;

	@BeforeClass
	public static void setup() {
		dc = new DataCarrier<Integer>() {
			private Integer data = new Integer(0);

			@Override
			public Integer getData() {
				return data;
			}

			@Override
			public boolean setData(Integer data) {
				this.data = data;
				return true;
			}
		};
	}
	
	@Test
	public void stage0_verifyNoAction() {
		assertNotEquals(null, dc);
	}
	
	@Test
	public void stage1_0_testGetData() {
		assertEquals(0, dc.getData().intValue());
	}
	
	@Test
	public void stage2_0_testSetData() {
		assertTrue(dc.setData(new Integer(1)));
	}
	
	@Test
	public void stage2_1_verifySetData() {
		assertEquals(1, dc.getData().intValue());
	}
	
	@AfterClass
	public static void destroy() {
		dc = null;
	}

}
