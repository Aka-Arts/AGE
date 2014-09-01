package org.akaarts.AGE.input;

import java.util.ArrayList;

import org.akaarts.AGE.Console;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

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
				if(l.keyEvent(key, state)){
					//stop propagation
					break;
				}
			}
		}
		//check mouse buffer
		while(Mouse.next()){
			int x = Mouse.getEventX();
			int y = Display.getHeight() - Mouse.getEventY();
			int button = Mouse.getEventButton();

			boolean state = Mouse.getEventButtonState();
			
			int wheel = Mouse.getEventDWheel();
			Console.info("Button: "+state);
			//broadcast
			for(InputListener l:listeners){
				if(button == -1 && wheel == 0){
					if(l.mouseMoveEvent(x, y)){
					//stop propagation
					break;
					}
				}else if(button == -1){
					if(l.mouseWheelEvent(x, y, wheel)){
					//stop propagation
					break;
					}
				}else{
					if(l.mouseButtonEvent(x, y, button, state)){
					//stop propagation
					break;
					}
				}
			}
		}
	}
	
	/**
	 * Adds a new listener object to the broadcast list
	 * @param listener - the new listener
	 */
	
	public static void addListener(InputListener listener){	
		listeners.add(listener);
		Console.info("InputHandler - Status: "+listeners.size());
	}
	
	public static void clearListeners(){
		listeners.clear();
		Console.info("InputHandler - Status: "+listeners.size());
	}

	public static void removeListener(InputListener listener) {
		listeners.remove(listener);
		Console.info("InputHandler - Status: "+listeners.size());
	}
	
}
