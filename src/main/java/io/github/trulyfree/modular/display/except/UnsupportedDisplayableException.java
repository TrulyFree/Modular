package io.github.trulyfree.modular.display.except;

import io.github.trulyfree.modular.display.Displayable;

/**
 * UnsupportedDisplayableException class. This exception should be thrown
 * whenever a Display does not support a Displayable instance provided by the
 * DisplayableModule that is marked as "necessary". You MUST provide a
 * Displayable instance in order to throw this Exception.
 * 
 * @author vtcakavsmoace
 *
 */
public class UnsupportedDisplayableException extends Exception {

	/**
	 * Standard displayable constructor, which instantiates with the message
	 * "Unknown or unsupported displayable (displayable type)", where
	 * (displayable type) is the fully qualified name for the displayable's
	 * class.
	 * 
	 * @param disp
	 *            The displayable which caused the exception.
	 */
	public UnsupportedDisplayableException(Displayable disp) {
		this("Unknown or unsupported displayable", disp);
	}

	/**
	 * @param message
	 * @param disp
	 *            The displayable which caused the exception.
	 */
	public UnsupportedDisplayableException(String message, Displayable disp) {
		super(messagify(message, disp));
	}

	/**
	 * @param cause
	 * @param disp
	 *            The displayable which caused the exception.
	 */
	public UnsupportedDisplayableException(Throwable cause, Displayable disp) {
		this("Unknown or unsupported displayable", cause, disp);
	}

	/**
	 * @param message
	 * @param cause
	 * @param disp
	 *            The displayable which caused the exception.
	 */
	public UnsupportedDisplayableException(String message, Throwable cause, Displayable disp) {
		super(messagify(message, disp), cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 * @param disp
	 *            The displayable which caused the exception.
	 */
	public UnsupportedDisplayableException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace, Displayable disp) {
		super(messagify(message, disp), cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param disp
	 *            The displayable which caused the exception.
	 * @return fullMessage The message to be used by the Exception.
	 */
	public static String messagify(String message, Displayable disp) {
		StringBuilder sb = new StringBuilder();
		sb.append(message);
		sb.append(": ");
		sb.append(disp.getClass().getName());
		return sb.toString();
	}

}
