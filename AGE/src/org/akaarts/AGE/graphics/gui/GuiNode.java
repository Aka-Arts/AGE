package org.akaarts.AGE.graphics.gui;

import org.akaarts.AGE.utils.Node;

public class GuiNode extends Node {
	
	private HudElement hudElement;
	
	public void setElem(HudElement element) {
		this.hudElement = element;
	}
	
	public HudElement getElement() {
		return this.hudElement;
	}
	
}
