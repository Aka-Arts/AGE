package org.akaarts.AGE.input;

public interface KeyEventListener {
	
	/**
	 * Callback method for key events
	 * @param e - key event object
	 * @return default false. true if the event controller should stop event propagation
	 */
	public boolean onKeyEvent(KeyEvent e);

}
