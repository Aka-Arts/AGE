package org.akaarts.AGE.input;

import org.akaarts.AGE.utils.Event;

public class MouseEvent extends Event {

	public final int x;
	public final int y;
	
	public final int button;
	public final boolean buttonPressed;
	
	public final int wheel;
	
	public MouseEvent(int x, int y, int button, boolean buttonPressed, int wheel) {
		
		this.x = x;
		this.y = y;
		
		this.button = button;
		this.buttonPressed = buttonPressed;
		
		this.wheel = wheel;
		
	}
		
	public String toString() {
		
		return "x: " + this.x +
				", y: " + this.y +
				", button: " + this.button +
				", buttonPressed: " + this.buttonPressed +
				", wheel: " + this.wheel;
		
	}
	
}
