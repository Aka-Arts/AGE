package org.akaarts.AGE.gui;

import org.akaarts.AGE.input.KeyEvent;
import org.akaarts.AGE.input.KeyEventListener;
import org.akaarts.AGE.input.MouseEvent;
import org.akaarts.AGE.input.MouseEventListener;

public class ViewManager implements KeyEventListener, MouseEventListener {
	
	public static final ViewManager INSTANCE = new ViewManager();
	
	private View currentView;
	
	private ViewManager(){
		
		this.currentView = null;
		
	}

	@Override
	public boolean onMouseEvent(MouseEvent e) {
		
		if(this.currentView!=null){
			
			if(this.currentView.onMouseEvent(e)){
				return true;
			}
			
		}
		
		return false;
	}

	@Override
	public boolean onKeyEvent(KeyEvent e) {
		
		if(this.currentView!=null){
			
			if(this.currentView.onKeyEvent(e)){
				return true;
			}
			
		}
		
		return false;
	}
	
	public void setView(View view){
		
		this.currentView = view;
		
	}
	
	public View getView(){
		
		return this.currentView;
		
	}

}
