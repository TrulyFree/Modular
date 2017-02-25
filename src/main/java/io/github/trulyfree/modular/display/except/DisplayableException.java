package io.github.trulyfree.modular.display.except;

import io.github.trulyfree.modular.display.Displayable;

/**
 * UnsupportedDisplayableException class. This exception should be thrown
 * whenever a Display does not support a Displayable instance provided by the
 * DisplayableModule that is below the Display's priority threshold OR when a
 * Displayable instance fails to be rendered. You MUST provide a Displayable
 * instance in order to throw this Exception.
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
	 * @param enableSuppression whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 * @param disp
	 *            The displayable which caused the exception.
	 */
	public DisplayableException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace, Displayable disp) {
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
		sb.append(disp.getClass().getName());
		return sb.toString();
	}

}
