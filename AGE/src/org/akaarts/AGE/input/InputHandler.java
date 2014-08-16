package org.akaarts.AGE.input;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputHandler {

	private InputHandler(){};
	
	private static ArrayList<InputListener> listeners = new ArrayList<InputListener>(4);
	
	/**
	 * Polls all inputs and broadcasts them to the registered listeners
	 */
	public static void update(){
		//Check keyboard buffer
		while(Keyboard.next()){
			int key = Keyboard.getEventKey();
			boolean state = Keyboard.getEventKeyState();
			//broadcast
			for(InputListener l:listeners){
				l.keyEvent(key, state);
			}
		}
		//check mouse buffer
		while(Mouse.next()){
			int x = Mouse.getEventX();
			int y = Mouse.getEventY();
			int button = Mouse.getEventButton();
			boolean state = Mouse.getEventButtonState();
			//broadcast
			for(InputListener l:listeners){
				l.mouseEvent(x, y, button, state);
			}
		}
	}
	
	/**
	 * Adds a new listener object to the broadcast list
	 * @param listener - the new listener
	 */
	
	public static void addListener(InputListener listener){	
		listeners.add(listener);
	}
	
}
