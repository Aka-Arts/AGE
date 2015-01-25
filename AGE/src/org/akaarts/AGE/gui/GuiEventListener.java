package org.akaarts.AGE.gui;

import org.akaarts.AGE.input.KeyEvent;
import org.akaarts.AGE.input.MouseEvent;

public interface GuiEventListener {
	
	public boolean onClick(MouseEvent e);
	
	public boolean onHover(MouseEvent e);
	
	public boolean onKey(KeyEvent e);

}
