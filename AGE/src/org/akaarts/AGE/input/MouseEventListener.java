package org.akaarts.AGE.input;

public interface MouseEventListener {
	
	/**
	 * Callback method for mouse events
	 * @param e - mouse event object
	 * @return default false. true if the event controller should stop event propagation
	 */
	public boolean onMouseEvent(MouseEvent e);

}
