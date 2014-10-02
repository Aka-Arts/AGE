package org.akaarts.AGE.graphics.gui;

import java.util.ArrayList;

import org.akaarts.AGE.CLI.Console;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class HudElement{

	private static HudElement root = new HudElement();
	
	private HudElement parent;
	
	private String widthExpression, heightExpression;
	private int width,height;
	
	private String positionXExpression, positionYExpression;	
	private String originX,	originY;
	private int positionX,positionY;
	
	private String backgroundColorExpression, backgroundImageExpression, backgroundFilterExpression,  backgroundRepeatExpression;
	private String backgroundUVTLExpression,backgroundUVBLExpression,backgroundUVBRExpression,backgroundUVTRExpression;
	private float backgroundColorR, backgroundColorG, backgroundColorB, backgroundColorA;
	private float backgroundUTL,backgroundUBL,backgroundUBR,backgroundUTR,backgroundVTL,backgroundVBL,backgroundVTBR,backgroundVTR;
	
	private Texture texture;
	private float topLeftU,bottomLeftU,bottomRightU,topRightU,topLeftV,bottomLeftV,bottomRightV,topRightV;
	
	public final String NOTEX = "/assets/defaults/NOTEX.png";
	
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
		
		this.applyDefaultStyle();
		
		
	}
	
	public HudElement(HudElement parent) {
		
		this.ISROOT = false;
		
		this.parent = parent;
		
		this.applyDefaultStyle();
		
		this.inheritStyle();
		
	}
	
	public void draw() {
		
		//TODO draw here..
		
		//draw all children
		for(HudElement child:this.children) {
			child.draw();
		}
	}
	
	/**
	 * Recalculates the element and it's children
	 */
	public void update() {

		//TODO update here...
		if(!ISROOT) {
			
			// update width
			if(this.widthExpression.endsWith("%")) {
				// from percent
				try {
					int percent = Integer.parseInt(this.widthExpression.substring(0, this.widthExpression.length()-1));
					this.width = (int) Math.floor(((float)this.parent.width/100)*percent);
				}catch(NumberFormatException e) {
					// unknown value, set like parent
					this.width = this.parent.width;
				}
			
			}else {
				// from pixels
				try {
					this.width = Integer.parseInt(widthExpression);
				}catch(NumberFormatException e) {
					// unknown value, set like parent
					this.width = this.parent.width;
				}
			
			}
			
			// update height
			if(this.heightExpression.endsWith("%")) {
				// from percent
				try {
					int percent = Integer.parseInt(this.heightExpression.substring(0, this.heightExpression.length()-1));
					this.height = (int) Math.floor(((float)this.parent.height/100)*percent);
				}catch(NumberFormatException e) {
					// unknown value, set like parent
					this.height = this.parent.height;
				}
			}else {
				// from pixels
				try {
					this.height = Integer.parseInt(heightExpression);
				}catch(NumberFormatException e) {
					// unknown value, set like parent
					this.height = this.parent.height;
				}
			
			}
			
			// update x position relative to parent
			int posX;
			try {
				posX = Integer.parseInt(this.positionXExpression);
			}catch(NumberFormatException e) {
				posX = 0;
			}
			
			switch(this.originX) {
			case HudElement.ORIGIN_CENTER:
				this.positionX = ((this.parent.width/2+posX)-this.width/2)+this.parent.positionX;
				break;
			case HudElement.ORIGIN_RIGHT:
				this.positionX = (this.parent.width-(posX+this.width))+this.parent.positionX;
				break;
			default:
				this.positionX = posX + this.parent.positionX;
				break;
			}
			
			// update y position relative to parent
			int posY;
			try {
				posY = Integer.parseInt(this.positionYExpression);
			}catch(NumberFormatException e) {
				posY = 0;
			}

			switch(this.originY) {
			case HudElement.ORIGIN_CENTER:
				this.positionY = ((this.parent.height/2+posY)-this.height/2)+this.parent.positionY;
				break;
			case HudElement.ORIGIN_BOTTOM:
				this.positionY = (this.parent.height-(posY+this.height))+this.parent.positionY;
				break;
			default:
				this.positionY = posY + this.parent.positionY;
				break;
			}
			
		}else {
			//only root
			this.width = Display.getWidth();
			this.height = Display.getHeight();
			
			this.positionX = 0;
			this.positionY = 0;
			
		}
		
		// load texture
		if(!this.backgroundImageExpression.isEmpty()) {
			// find filter
			int filter;
			if(this.backgroundFilterExpression.equals("linear")) {
				filter = GL11.GL_LINEAR;
			}else {
				filter = GL11.GL_NEAREST;
			}
			try {
				this.texture = TextureLoader.getTexture("PNG", HudElement.class.getResourceAsStream(this.backgroundImageExpression), filter);
			}catch(Exception e) {
				Console.warning("Could not find texture: "+this.backgroundImageExpression);
				try {
					this.texture = TextureLoader.getTexture("PNG", HudElement.class.getResourceAsStream(NOTEX));
				}catch(Exception e2) {
					Console.error("Could not find NOTEX ?!");
					e2.printStackTrace();
				}
			}
		}else {
			this.texture = null;
		}
		
		// set UVs
		
		// top left
		String[] TL = this.backgroundUVTLExpression.split(" ");
		
		// if correct data
		if(TL.length==2) {
			// parse them
			try {
				this.topLeftU = Float.parseFloat(TL[0]);
			}catch(NumberFormatException e) {
				Console.warning("incorrect UV value U: "+TL[0]);
				this.topLeftU = 0;
			}
			
			try {
				this.topLeftV = Float.parseFloat(TL[1]);
			}catch(NumberFormatException e) {
				Console.warning("incorrect UV value V: "+TL[1]);
				this.topLeftV = 0;
			}
		}else {
			// set defaults
			this.topLeftU = 0;
			this.topLeftV = 0;
		}
		

		
		//update all children
		
		for(HudElement child:this.children){
			child.update();
		}
		
	}
	
	/**
	 * returns the unique root
	 * @return - always the same root
	 */
	public static HudElement getRoot() {
		return root;
	}
	
	/**
	 * applies all the default expressions
	 */
	private void applyDefaultStyle(){
		this.widthExpression = "100%";
		this.heightExpression = "100%";
		
		this.originX = ORIGIN_LEFT;
		this.originY = ORIGIN_TOP;
		
		this.positionXExpression = "0";
		this.positionYExpression = "0";
		
		this.backgroundColorExpression = "#00000000";
		this.backgroundImageExpression = "none";
		this.backgroundFilterExpression = "nearest";
		this.backgroundRepeatExpression = "none";
		this.backgroundUVTLExpression = "0 0";
		this.backgroundUVBLExpression = "0 1";
		this.backgroundUVBRExpression = "1 1";
		this.backgroundUVTRExpression = "1 0";
	}
	
	private void inheritStyle() {
		
	}
	
	public String getWidthExpr() {
		return this.widthExpression;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public String getHeightExpr() {
		return this.heightExpression;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public String getOriginX() {
		return this.originX;
	}
	
	public String getOriginY() {
		return this.originY;
	}
	
	public void destroy() {
		if(this.texture!=null) {
			this.texture.release();
		}
	}
	
}
