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
	private float backgroundUTL,backgroundUBL,backgroundUBR,backgroundUTR,backgroundVTL,backgroundVBL,backgroundVBR,backgroundVTR;
	
	private Texture texture;
	
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
		
		this.update();
		
	}
	
	/**
	 * Public constructor for all basic hudElements. after construction, it adds itself to the children of the parent
	 * @param parent - the parent hudElement
	 */
	public HudElement(HudElement parent) {
		
		this.ISROOT = false;
		
		this.parent = parent;
		
		this.applyDefaultStyle();
		
		this.inheritStyle();
		
		this.update();
		
		parent.children.add(this);
		
	}
	
	public void draw() {


		float R = 0, G = 0, B = 0;
		
		if(this.texture!=null){
			this.texture.bind();
		}else{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(1, 1, 1);
			GL11.glTexCoord2f(this.backgroundUTL, this.backgroundVTL);
			GL11.glVertex2i(this.positionX, this.positionY);
			GL11.glTexCoord2f(this.backgroundUTR, this.backgroundVTR);
			GL11.glVertex2i(this.positionX+this.width, this.positionY);
			GL11.glTexCoord2f(this.backgroundUBR, this.backgroundVBR);
			GL11.glVertex2i(this.positionX+this.width, this.positionY+this.height);
			GL11.glTexCoord2f(this.backgroundUBL, this.backgroundVBL);
			GL11.glVertex2i(this.positionX, this.positionY+this.height);
		GL11.glEnd();
		
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
		
		this.softUpdate();
				
		//update all children
		
		for(HudElement child:this.children){
			child.update();
		}
		
	}
	
	/**
	 * Update function for updating this element without updating it's children. Only updates things like backgrounds, which doesn't affect it's children.
	 */
	public void softUpdate(){
		// destroy old texture
		
				if(this.texture!=null){
					this.texture.release();
				}
				
				// load texture
				if(!(this.backgroundImageExpression.isEmpty()||this.backgroundImageExpression.equals("none"))) {
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
							this.texture = TextureLoader.getTexture("PNG", HudElement.class.getResourceAsStream(NOTEX), GL11.GL_NEAREST);
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
						this.backgroundUTL = Float.parseFloat(TL[0]);
					}catch(NumberFormatException e) {
						Console.warning("incorrect UV value U: "+TL[0]);
						this.backgroundUTL = 0;
					}
					
					try {
						this.backgroundVTL = Float.parseFloat(TL[1]);
					}catch(NumberFormatException e) {
						Console.warning("incorrect UV value V: "+TL[1]);
						this.backgroundVTL = 0;
					}
				}else {
					// set defaults
					this.backgroundUTL = 0;
					this.backgroundVTL = 0;
				}
				
				// bottom left
				String[] BL = this.backgroundUVBLExpression.split(" ");
				
				// if correct data
				if(BL.length==2) {
					// parse them
					try {
						this.backgroundUBL = Float.parseFloat(BL[0]);
					}catch(NumberFormatException e) {
						Console.warning("incorrect UV value U: "+BL[0]);
						this.backgroundUBL = 0;
					}
					
					try {
						this.backgroundVBL = Float.parseFloat(BL[1]);
					}catch(NumberFormatException e) {
						Console.warning("incorrect UV value V: "+BL[1]);
						this.backgroundVBL = 1;
					}
				}else {
					// set defaults
					this.backgroundUBL = 0;
					this.backgroundVBL = 1;
				}
				
				// bottom right
				String[] BR = this.backgroundUVBRExpression.split(" ");
				
				// if correct data
				if(BR.length==2) {
					// parse them
					try {
						this.backgroundUBR = Float.parseFloat(BR[0]);
					}catch(NumberFormatException e) {
						Console.warning("incorrect UV value U: "+BR[0]);
						this.backgroundUBR = 1;
					}
					
					try {
						this.backgroundVBR = Float.parseFloat(BR[1]);
					}catch(NumberFormatException e) {
						Console.warning("incorrect UV value V: "+BR[1]);
						this.backgroundVBR = 1;
					}
				}else {
					// set defaults
					this.backgroundUBR = 1;
					this.backgroundVBR = 1;
				}
				
				// top right
				String[] TR = this.backgroundUVTRExpression.split(" ");
				
				// if correct data
				if(TR.length==2) {
					// parse them
					try {
						this.backgroundUTR = Float.parseFloat(TR[0]);
					}catch(NumberFormatException e) {
						Console.warning("incorrect UV value U: "+TR[0]);
						this.backgroundUTR = 1;
					}
					
					try {
						this.backgroundVTR = Float.parseFloat(TR[1]);
					}catch(NumberFormatException e) {
						Console.warning("incorrect UV value V: "+TR[1]);
						this.backgroundVTR = 0;
					}
				}else {
					// set defaults
					this.backgroundUTR = 1;
					this.backgroundVTR = 0;
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
	
	public void setBackgroundImage(String path){
		this.backgroundImageExpression = path;
		
		this.softUpdate();
	}
	
	public void destroy() {
		if(this.texture!=null) {
			this.texture.release();
			Console.info("Released a texture");
		}
		for(HudElement child:children){
			child.destroy();
		}
	}
	
}
