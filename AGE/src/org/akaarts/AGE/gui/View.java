package org.akaarts.AGE.gui;

import org.akaarts.AGE.input.KeyEvent;
import org.akaarts.AGE.input.KeyEventListener;
import org.akaarts.AGE.input.MouseEvent;
import org.akaarts.AGE.input.MouseEventListener;

public abstract class View implements MouseEventListener, KeyEventListener{
	
	public final ViewElement ROOT;
		
	private ViewElement focus;
	
	public View() {
		
		this.ROOT = new ViewElement() {

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
			
		};
		
	}
	
	public final void setFocusOn(ViewElement e) {
		
		this.focus = e;
		
	}
	
	public final ViewElement getFocus() {
		
		return focus;
		
	}
	
	@Override
	public boolean onKeyEvent(KeyEvent e) {
		
		return false;
		
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
