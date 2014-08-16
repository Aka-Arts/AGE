package org.akaarts.AGE.input;

import java.util.EventListener;

public interface InputListener extends EventListener {
	
	public void keyEvent(int lwjglKey, boolean keyState);
	public void mouseEvent(int x, int y, int lwjglButton, boolean buttonState);

}
