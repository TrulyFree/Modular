package io.github.trulyfree.modular.general;

/* Modular library by TrulyFree: A general-use module-building library.
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

/**
 * Forkable interface. All modules or events which will run strictly on separate
 * threads should implement this interface. It is highly suggested that Forkable
 * Event implementations be an EventGroup in order to have an easy
 * implementation for safeHalt. Forkable Cancellable Events should not end
 * execution after being enacted if they are set to cancelled mid-execution.
 * 
 * @author vtcakavsmoace
 *
 */
public interface Forkable {

	public boolean safeHalt();

	public boolean immediateHalt();

}
