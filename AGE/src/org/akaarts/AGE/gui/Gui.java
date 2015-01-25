package org.akaarts.AGE.gui;

import org.akaarts.AGE.Console;
import org.akaarts.AGE.input.KeyEvent;
import org.akaarts.AGE.input.KeyEventListener;
import org.akaarts.AGE.input.MouseEvent;
import org.akaarts.AGE.input.MouseEventListener;

public class Gui implements MouseEventListener, KeyEventListener{

	public static final Gui SINGLETON = new Gui();
	
	public final GuiElement ROOT;
		
	private GuiElement focus;
	
	private Gui() {
		
		this.ROOT = new GuiElement() {

			@Override
			public boolean onClick(MouseEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onHover(MouseEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onKey(KeyEvent e) {
				
				return false;
			}
		
			
			
		};
		
	}
	
	public void setFocusOn(GuiElement e) {
		
		this.focus = e;
		
	}
	
	public GuiElement getFocus() {
		
		return focus;
		
	}
	
	@Override
	public boolean onKeyEvent(KeyEvent e) {
		
		if(e.keyName.equals("F4") && e.altPressed) {
			
			System.exit(-1);
			
		}
		
		return ROOT.onKeyInternal(e);

	}

	@Override
	public boolean onMouseEvent(MouseEvent e) {

		if(e.button == -1) {
			
			return ROOT.onHoverInternal(e);
			
		}
		
		if(e.button != -1 && !e.buttonPressed) {
			
			return ROOT.onClickInternal(e);
			
		}
		
		// TODO WHEEL
		
		return false;
		
	}
	
}
