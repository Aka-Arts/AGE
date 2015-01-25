package org.akaarts.AGE.input;

import java.util.ArrayList;

import org.akaarts.AGE.Console;
import org.akaarts.AGE.gui.Gui;
import org.lwjgl.input.Keyboard;


public class KeyInputController {
		
	private static ArrayList<KeyEventListener> listeners = new ArrayList<KeyEventListener>();
	
	private static boolean ctrlPressed = false;
	private static boolean altPressed = false;
	private static boolean shiftPressed = false;
	
	public static void init() {
		
		Keyboard.enableRepeatEvents(true);
		
	}
	
	/**
	 * Polls all inputs and broadcasts them to the registered listeners
	 */
	public static void pollAndBroadcast(){
		
		//Check keyboard buffer
		while(Keyboard.next()){
			
			int keyCode = Keyboard.getEventKey();
			boolean keyState = Keyboard.getEventKeyState();
			
			char rawCharacter = Keyboard.getEventCharacter();
			
			boolean isRepeated = Keyboard.isRepeatEvent();
			
			switch(keyCode) {
			
			case Keyboard.KEY_LSHIFT:
			case Keyboard.KEY_RSHIFT:
				
				shiftPressed = keyState;
				
				break;
				
			case Keyboard.KEY_LCONTROL:
			case Keyboard.KEY_RCONTROL:
				
				ctrlPressed = keyState;
				
				break;
				
			case Keyboard.KEY_LMENU:
			case Keyboard.KEY_RMENU:
				
				altPressed = keyState;
				
				break;
			
			}
			
			
			KeyEvent e = new KeyEvent(
					keyCode,
					keyState, 
					ctrlPressed, 
					altPressed, 
					shiftPressed,
					rawCharacter,
					isRepeated);
			
			// broadcast internal listeners
			
			if(Gui.SINGLETON.onKeyEvent(e)) {
				
				Console.info("Gui stopped an event propagation: " + e.toString());
				
				continue;
				
			}
			
			// broadcast external listeners
			for(KeyEventListener l:listeners){
			
				if(l.onKeyEvent(e)) {
					
					break;
					
				}
		
			}
		
		}
		
	}
	
	/**
	 * Adds a new listener object to the broadcast list
	 * @param listener - the new listener
	 */
	
	public static void addListener(KeyEventListener listener){
		
		if(!listeners.contains(listener)) {
			
			listeners.add(listener);
			
		}else {
			
			Console.warning("Prevented 2nd addition of identical KeyEventListener");
			
		}
		
		Console.info("KeyInputController - Status: "+listeners.size()+" listeners");
		
	}
	
	public static void clearListeners(){
		
		listeners.clear();
		
		Console.info("KeyInputController - Status: "+listeners.size()+" listeners");
		
	}

	public static void removeListener(KeyEventListener listener) {
		
		listeners.remove(listener);
		
		Console.info("KeyInputController - Status: "+listeners.size()+" listeners");
		
	}
	
}
