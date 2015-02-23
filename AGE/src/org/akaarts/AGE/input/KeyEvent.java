package org.akaarts.AGE.input;

import org.akaarts.AGE.utils.Event;
import org.lwjgl.input.Keyboard;

public class KeyEvent extends Event{
	
	public final int keyCode;
	public final boolean keyState;
	
	public final boolean ctrlPressed;
	public final boolean altPressed;
	public final boolean shiftPressed;
	public final boolean isRepeated;
	
	public final String keyName;
	public final char rawCharacter;
	
	public KeyEvent(int keyCode, boolean keyState, boolean ctrlPressed, boolean altPressed, boolean shiftPressed, char rawCharacter, boolean isRepeated) {
		
		this.keyCode = keyCode;
		this.keyState = keyState;
		
		this.ctrlPressed = ctrlPressed;
		this.altPressed = altPressed;
		this.shiftPressed = shiftPressed;
		
		this.isRepeated = isRepeated;
		
		this.keyName = Keyboard.getKeyName(keyCode);
		this.rawCharacter = rawCharacter;
		
	}
	
	public String toString() {
		
		return "keyCode: " + this.keyCode +
				", keyState: " + this.keyState +
				", ctrlPressed: " + this.ctrlPressed +
				", altPressed: " + this.altPressed +
				", shiftPressed: " + this.shiftPressed +
				", keyName: " + this.keyName +
				", rawCharacter: "+ this.rawCharacter +
				", isRepeated: " + this.isRepeated;
		
	}

}
