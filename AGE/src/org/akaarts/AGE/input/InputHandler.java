package org.akaarts.AGE.input;

import java.util.HashMap;

import org.akaarts.AGE.Console;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class InputHandler {
	
	private static HashMap<Integer, Boolean> keyList = new HashMap<Integer, Boolean>();
	
	private static int cursorX = 0;
	private static int cursorY = 0;
	private static int wheelDelta = 0;
	private static HashMap<Integer, Boolean> buttonList = new HashMap<Integer, Boolean>();

	/**
	 * Internal public method, do not call in your code!<br>
	 * 
	 * Processes all inputs.
	 * 
	 */
	public static void update() {

		// Check keyboard buffer
		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			boolean state = Keyboard.getEventKeyState();

			// cache
			keyList.put(key, state);
			
			Console.info("Key:"+key+"/"+state);
			
		}
		// check mouse buffer
		while (Mouse.next()) {
			int x = Mouse.getEventX();
			int y = Display.getHeight() - Mouse.getEventY();
			int button = Mouse.getEventButton();

			boolean state = Mouse.getEventButtonState();

			int wheel = Mouse.getEventDWheel();
			// broadcast

			// TODO add mouse event cache
			
		}

	}

}
