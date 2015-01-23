package org.akaarts.AGE.input;

import java.util.ArrayList;
import java.util.HashMap;

import org.akaarts.AGE.Console;
import org.akaarts.earlyalpha.AGE.input.InputListener;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MouseInputController {

	private static ArrayList<MouseEventListener> listeners = new ArrayList<MouseEventListener>(4);

	/**
	 * Internal public method, do not call in your code!<br>
	 * 
	 * Processes all inputs.
	 * 
	 */
	public static void pollAndBroadcast() {

		// check mouse buffer
		while (Mouse.next()) {

			// new event object
			MouseEvent e = new MouseEvent(
					Mouse.getEventX(),
					Display.getHeight() - Mouse.getEventY(),
					Mouse.getEventButton(),
					Mouse.getEventButtonState(),
					Mouse.getEventDWheel());

			// broadcast
			for (MouseEventListener l : listeners) {

				l.onMouseEvent(e);
				
			}

		}

	}
	
	/**
	 * Adds a new listener object to the broadcast list
	 * @param listener - the new listener
	 */
	
	public static void addListener(MouseEventListener listener){
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}else {
			Console.warning("Prevented 2nd addition of identical inputListener");
		}
		Console.info("MouseInputController - Status: "+listeners.size()+" listeners");
	}
	
	public static void clearListeners(){
		listeners.clear();
		Console.info("MouseInputController - Status: "+listeners.size()+" listeners");
	}

	public static void removeListener(MouseEventListener listener) {
		listeners.remove(listener);
		Console.info("MouseInputController - Status: "+listeners.size()+" listeners");
	}

}
