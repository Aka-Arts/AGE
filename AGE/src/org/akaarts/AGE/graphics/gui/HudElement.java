package org.akaarts.AGE.graphics.gui;

import java.util.ArrayList;

public class HudElement{

	private static HudElement root = new HudElement();
	
	private String widthExpression, heightExpression;
	private int width,height;
	
	private String positionXExpression, positionYExpression;	
	private String originX,	originY;
	private int positionX,positionY;
	
	private String backgroundColorExpression, backgroundImageExpression, backgroundFilterExpression, backgroundUVExpression, backgroundRepeatExpression;
	private float backgroundColorR, backgroundColorG, backgroundColorB, backgroundColorA;
	
	public final boolean ISROOT;
	
	private ArrayList<HudElement> children = new ArrayList<HudElement>();
	
	public static final String ORIGIN_CENTER = "center",
							ORIGIN_TOP = "top", 
							ORIGIN_BOTTOM = "bottom", 
							ORIGIN_LEFT = "left", 
							ORIGIN_RIGHT = "right";
	
	/**
	 * Constructor only for the root element
	 * Setting all default styles
	 */
	private HudElement() {
		
		this.ISROOT = true;
		
		this.applyDefaults();
		
		
	}
	
	public void draw() {
		
	}
	
	/**
	 * Recalculates the element and it's children
	 */
	public void update() {

		//TODO update here...
		
		//update all children
		
		for(HudElement child:this.children){
			child.update();
		}
		
	}
	
	public static HudElement getRoot() {
		return root;
	}
	
	/**
	 * applies all the default expressions
	 */
	private void applyDefaults(){
		this.widthExpression = "100%";
		this.heightExpression = "100%";
		
		this.originX = ORIGIN_CENTER;
		this.originY = ORIGIN_CENTER;
		
		this.positionXExpression = "0px";
		this.positionYExpression = "0px";
		
		this.backgroundColorExpression = "#00000000";
		this.backgroundImageExpression = "none";
		this.backgroundFilterExpression = "nearest";
		this.backgroundRepeatExpression = "none";
		this.backgroundUVExpression = "0 0,0 1,1 1,1 0";
	}

	
}
