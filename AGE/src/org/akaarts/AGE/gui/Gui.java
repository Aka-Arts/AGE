package org.akaarts.AGE.gui;

import org.akaarts.AGE.input.KeyEvent;
import org.akaarts.AGE.input.KeyEventListener;
import org.akaarts.AGE.input.MouseEvent;
import org.akaarts.AGE.input.MouseEventListener;

public class Gui implements MouseEventListener, KeyEventListener{

	public static final Gui SELF = new Gui();
		
	private Gui() {
		
	}
	
	@Override
	public boolean onKeyEvent(KeyEvent e) {
		
		if(e.keyName.equals("F4") && e.altPressed) {
			
			System.exit(-1);
			
		}
		
		return false;
	}

	@Override
	public boolean onMouseEvent(MouseEvent e) {
		
		return false;
	}
	
}
