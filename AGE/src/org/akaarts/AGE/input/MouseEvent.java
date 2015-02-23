package org.akaarts.AGE.input;

import org.akaarts.AGE.utils.Event;

public class MouseEvent extends Event {

	public final int x;
	public final int y;
	
	public final int deltaX;
	public final int deltaY;
	
	public final int button;
	public final boolean buttonPressed;
	public final boolean grabbed;
	
	public final int wheel;
	
	public MouseEvent(int x, int y, int deltaX, int deltaY, int button, boolean buttonPressed, int wheel, boolean grabbed) {
		
		this.x = x;
		this.y = y;
		
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		
		this.button = button;
		this.buttonPressed = buttonPressed;
		
		this.grabbed = grabbed;
		
		this.wheel = wheel;
		
	}
		
	public String toString() {
		
		return "x: " + this.x +
				", y: " + this.y +
				", deltaX: " + this.deltaX +
				", deltaY: " + this.deltaY +
				", button: " + this.button +
				", buttonPressed: " + this.buttonPressed +
				", wheel: " + this.wheel +
				", grabbed: " + this.grabbed;
		
	}
	
}
