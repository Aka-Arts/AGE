package org.akaarts.AGE.input;

import java.util.ArrayList;

import org.akaarts.AGE.gui.ViewManager;
import org.akaarts.AGE.main.Console;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MouseInputController {

	private static ArrayList<MouseEventListener> listeners = new ArrayList<MouseEventListener>();

	/**
	 * Internal public method, do not call in your code!<br>
	 * Gets called if mouse is not grabbed.
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
					Mouse.getEventDX(),
					-Mouse.getEventDY(),
					Mouse.getEventButton(),
					Mouse.getEventButtonState(),
					Mouse.getEventDWheel(),
					Mouse.isGrabbed());

			// broadcast internal listeners
			
			if(ViewManager.INSTANCE.onMouseEvent(e)) {
				
				Console.info("Gui stopped an event propagation: " + e.toString());
				
				continue;
				
			}
			
			// broadcast external listeners
			for (MouseEventListener l : listeners) {

				if(l.onMouseEvent(e)) {
					
					break;
					
				}
				
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
			
			Console.warning("Prevented 2nd addition of identical MouseEventListener");
			
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
