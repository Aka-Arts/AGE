package org.akaarts.AGE.input;

import java.util.EventListener;

/**
 * Basic interface for InputHandler listener
 * @author Luca Egli
 *
 */
public interface InputListener extends EventListener {
	
	/**
	 * gets called if a key event is registered
	 * @param lwjglKey - the lwjgl Keyboard.* value
	 * @param keyState - true if pressed or false if released
	 */
	public void keyEvent(int lwjglKey, boolean keyState);
	/**
	 * gets called if a mouse event is registered
	 * @param x - the absolute x coordinate
	 * @param y - the absolute y coordinate
	 * @param lwjglButton - the mouse button number or -1 if no button is pressed
	 * @param buttonState - true if pressed or false if released/no press event
	 */
	public void mouseEvent(int x, int y, int lwjglButton, boolean buttonState);

}
