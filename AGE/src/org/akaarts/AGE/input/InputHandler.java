package org.akaarts.AGE.input;

import java.util.ArrayList;

import org.akaarts.AGE.Console;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputHandler {

	private InputHandler(){};
	
	private static ArrayList<InputListener> listeners = new ArrayList<InputListener>(4);
	
	public static void update(){
		
		while(Keyboard.next()){
			int key = Keyboard.getEventKey();
			boolean state = Keyboard.getEventKeyState();
			for(InputListener l:listeners){
				l.keyEvent(key, state);
			}
			Console.info("Keyevent: "+Keyboard.getKeyName(key)+"/"+state);
		}
		while(Mouse.next()){
			int x = Mouse.getEventX();
			int y = Mouse.getEventY();
			int button = Mouse.getEventButton();
			boolean state = Mouse.getEventButtonState();
			
			for(InputListener l:listeners){
				l.mouseEvent(x, y, button, state);
			}
			Console.info("Mouseevent: "+x+"/"+y+" / Button: "+button+"/"+state);
		}
	}
	
	public static void addListener(InputListener listener){
		
		listeners.add(listener);
		
	}
	
}
