package org.akaarts.AGE.graphics.gui;

public class HudElement{

	private static HudElement root = new HudElement();
	
	private String widthExpression, heightExpression;
	private int width,height;
	
	private String position;
	
	private String topExpression, leftExpression, bottomExpression, rightExpression;
	private int top, left, bottom, right;
	
	/**
	 * Constructor only for the root element
	 * Setting all default styles
	 */
	private HudElement() {
		this.widthExpression = "100%";
		this.heightExpression = "100%";
		
		this.position = "static";
	}
	
	public HudElement getRoot() {
		return root;
	}

	
}
