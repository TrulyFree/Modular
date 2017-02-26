package io.github.trulyfree.modular.display.except;

import io.github.trulyfree.modular.display.Displayable;

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
 * UnsupportedDisplayableException class. This exception should be thrown
 * whenever a Display does not support a Displayable instance provided by the
 * DisplayableModule that is below the DisplayableModule's priority threshold OR
 * when a Displayable instance fails to be rendered. You MUST provide a
 * Displayable instance in order to throw this Exception.
 * 
 * @author vtcakavsmoace
 *
 */
public class DisplayableException extends Exception {

	/**
	 * Because I'm supposed to.
	 */
	private static final long serialVersionUID = -6212968576724328187L;

	/**
	 * Standard displayable constructor, which instantiates with the message
	 * "Unknown or unsupported displayable (displayable type)", where
	 * (displayable type) is the fully qualified name for the displayable's
	 * class.
	 * 
	 * @param disp
	 *            The displayable which caused the exception.
	 */
	public DisplayableException(Displayable disp) {
		this("Unknown or unsupported displayable", disp);
	}

	/**
	 * @param message
	 *            The message detailing the event leading to the exception.
	 * @param disp
	 *            The displayable which caused the exception.
	 */
	public DisplayableException(String message, Displayable disp) {
		super(messagify(message, disp));
	}

	/**
	 * @param cause
	 *            The throwable which caused the exception to be thrown.
	 * @param disp
	 *            The displayable which caused the exception.
	 */
	public DisplayableException(Throwable cause, Displayable disp) {
		this("Unknown or unsupported displayable", cause, disp);
	}

	/**
	 * @param message
	 *            The message detailing the event leading to the exception.
	 * @param cause
	 *            The throwable which caused the exception to be thrown.
	 * @param disp
	 *            The displayable which caused the exception.
	 */
	public DisplayableException(String message, Throwable cause, Displayable disp) {
		super(messagify(message, disp), cause);
	}

	/**
	 * @param message
	 *            The message detailing the event leading to the exception.
	 * @param cause
	 *            The throwable which caused the exception to be thrown.
	 * @param enableSuppression
	 *            whether or not suppression is enabled or disabled
	 * @param writableStackTrace
	 *            whether or not the stack trace should be writable
	 * @param disp
	 *            The displayable which caused the exception.
	 */
	public DisplayableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
			Displayable disp) {
		super(messagify(message, disp), cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 *            The message detailing the event leading to the exception.
	 * @param disp
	 *            The displayable which caused the exception.
	 * @return fullMessage The message to be used by the Exception.
	 */
	public static String messagify(String message, Displayable disp) {
		StringBuilder sb = new StringBuilder();
		sb.append(message);
		sb.append(": ");
		sb.append(disp.toString());
		sb.append(" (");
		sb.append(disp.getClass().getName());
		sb.append(")");
		return sb.toString();
	}

}
