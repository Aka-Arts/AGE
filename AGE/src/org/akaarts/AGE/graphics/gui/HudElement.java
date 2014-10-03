package org.akaarts.AGE.graphics.gui;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.akaarts.AGE.CLI.Console;
import org.akaarts.AGE.input.InputHandler;
import org.akaarts.AGE.input.InputListener;
import org.akaarts.AGE.utils.UVMap4;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class HudElement implements InputListener{

	private static HudElement root = new HudElement();
	
	private HudElement parent;
	
	private int width,height;
	
	private int originX,originY;
	private int positionX,positionY,relativeX,relativeY;
	
	private Color backgroundColor;

	
	private boolean isHovered, isActive, isClicked;
	
	private Rectangle aabb;
	
	private Texture texture, textureHover, textureActive;
	private UVMap4 uv, uvHover, uvActive;
	
	
	
	public final String NOTEX = "/assets/defaults/NOTEX.png";
	
	public final boolean ISROOT;
	
	private ArrayList<HudElement> children = new ArrayList<HudElement>();
	
	public static final int ORIGIN_CENTER = 0,
							ORIGIN_TOP = 1, 
							ORIGIN_BOTTOM = 2, 
							ORIGIN_LEFT = 3, 
							ORIGIN_RIGHT = 4;
	
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
	
	/**
	 * Draws the element and it's children
	 */
	public void draw() {


		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		float a = 0;
		
		if(this.texture!=null){
			a = 1;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture.getTextureID());
			// texture.bind() seems broken... strange...
		}
		
		UVMap4 uvMap4 = this.uv;
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor4f(1, 1, 1, a);
			GL11.glTexCoord2f(uvMap4.U[0], uvMap4.V[0]);
			GL11.glVertex2i(this.positionX, this.positionY);
			GL11.glTexCoord2f(uvMap4.U[1], uvMap4.V[1]);
			GL11.glVertex2i(this.positionX+this.width, this.positionY);
			GL11.glTexCoord2f(uvMap4.U[2], uvMap4.V[2]);
			GL11.glVertex2i(this.positionX+this.width, this.positionY+this.height);
			GL11.glTexCoord2f(uvMap4.U[3], uvMap4.V[3]);
			GL11.glVertex2i(this.positionX, this.positionY+this.height);
		GL11.glEnd();
		
		if(this.isClicked){
			Console.info(this.hashCode()+" was clicked!");
		}else if(this.isActive){
			Console.info(this.hashCode()+" is pressed!");
		}else if(this.isHovered){
			Console.info(this.hashCode()+" is hovered!");
		}
		
		//draw all children
		for(HudElement child:this.children) {
			child.draw();
		}
		
		this.isClicked = false;
	}
	
	/**
	 * Recalculates the element and it's children
	 */
	public void update() {

		//TODO update here...
		if(!ISROOT) {
			
			// update x position relative to parent			switch(this.originX) {
			switch(this.originX) {
			case HudElement.ORIGIN_CENTER:
				this.positionX = ((this.parent.width/2+this.relativeX)-this.width/2)+this.parent.positionX;
				break;
			case HudElement.ORIGIN_RIGHT:
				this.positionX = (this.parent.width-(this.relativeX+this.width))+this.parent.positionX;
				break;
			default:
				this.positionX = this.relativeX + this.parent.positionX;
				break;
			}
			
			// update y position relative to parent
			switch(this.originY) {
			case HudElement.ORIGIN_CENTER:
				this.positionY = ((this.parent.height/2+this.relativeY)-this.height/2)+this.parent.positionY;
				break;
			case HudElement.ORIGIN_BOTTOM:
				this.positionY = (this.parent.height-(this.relativeY+this.height))+this.parent.positionY;
				break;
			default:
				this.positionY = this.relativeY + this.parent.positionY;
				break;
			}
			
		}else {
			//only root
			this.width = Display.getWidth();
			this.height = Display.getHeight();
			
			this.positionX = 0;
			this.positionY = 0;
			
		}
		
		this.aabb = new Rectangle(this.positionX,this.positionY,this.width,this.height);
				
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
		this.setDimensions(1f, 1f);
		
		this.originX = ORIGIN_LEFT;
		this.originY = ORIGIN_TOP;
		
		this.setPositioning(0, 0, ORIGIN_LEFT, ORIGIN_TOP);
		
		this.setBackgroundColor(null);
		this.setBackgroundImage(null);
	}
	
	private void inheritStyle() {
		
	}
	
	/**
	 * Sets the new Dimensions of this element as integer in pixels and performs an update()
	 * @param w - new width 
	 * @param h - new height
	 */
	public void setDimensions(int w, int h){
		if(this.ISROOT){
			return;
		}
		this.width = w;
		this.height = h;
		
		this.update();
	}
	
	/**
	 * Sets the new Dimensions of this element as float in percent and performs an update()<br> Examples: 0.5 for 50% (refer to the parents width/height)
	 * @param wPercent - percent of parents width (0.00 to 1.00)
	 * @param hPercent - percent of parents width (0.00 to 1.00)
	 */
	public void setDimensions(float wPercent, float hPercent){
		if(this.ISROOT){
			return;
		}
		this.width = (int) Math.floor(this.parent.width*wPercent);
		this.height = (int) Math.floor(this.parent.width*wPercent);
		
		this.update();
	}
	
	/**
	 * Sets the new positioning for this element.
	 * @param xExpr - the distance from xOrigin-position to this elements position
	 * @param yExpr	- the distance from yOrigin-position to this elements position
	 * @param xOrigin - the origin for the x Axis (ORIGIN_LEFT,ORIGIN_CENTER,ORIGIN_RIGHT)
	 * @param yOrigin - the origin for the y Axis (ORIGIN_TOP,ORIGIN_CENTER,ORIGIN_BOTTOM)
	 */
	public void setPositioning(int x, int y, int xOrigin, int yOrigin){
		if(this.ISROOT){
			return;
		}
		this.relativeX = x;
		this.relativeY = y;
		
		this.originX = xOrigin;
		this.originY = yOrigin;
		
		this.update();
		
	}
	
	/**
	 * Returns the effective width as integer
	 * @return - the width in pixels
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Returns the effective height as integer
	 * @return - the height in pixels
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Returns the alignment origin of the X Axis as raw String expression
	 * @return - a String of the interpreted expression
	 */
	public int getOriginX() {
		return this.originX;
	}
	
	/**
	 * Returns the alignment origin of the Y Axis as raw String expression
	 * @return - a String of the interpreted expression
	 */
	public int getOriginY() {
		return this.originY;
	}
	
	/**
	 * Sets a new or default color for this element (default is white)
	 * @param color - new color
	 */
	public void setBackgroundColor(Color color){
		if(color!=null){
			this.backgroundColor = color;			
		}else{
			this.backgroundColor = new Color(1f,1f,1f);
		}

	}
	
	/**
	 * Sets a new or no image for this element (UVs will be set to default and the scale filter is GL_NEAREST)
	 * @param path - the path to the new image or null for no image
	 */
	public void setBackgroundImage(String path){
		
		if(this.texture!=null){
			this.texture.release();
		}
		
		if(path!=null){
			try {
				this.texture = TextureLoader.getTexture("PNG", HudElement.class.getResourceAsStream(path), GL11.GL_NEAREST);
			}catch(Exception e) {
				Console.warning("Could not find texture: "+path);
				try {
					this.texture = TextureLoader.getTexture("PNG", HudElement.class.getResourceAsStream(NOTEX), GL11.GL_NEAREST);
				}catch(Exception e2) {
					Console.error("Could not find NOTEX ?!");
					e2.printStackTrace();
				}
			}
		}else{
			this.texture = null;
		}
		this.uv = new UVMap4(0,0,1,0,1,1,0,1);
	}
	
	/**
	 * Destroys all own textures and calls destroy() on it's children
	 */
	public void destroy() {
		if(this.texture!=null) {
			this.texture.release();
			Console.info("Released a texture");
		}
		for(HudElement child:children){
			child.destroy();
		}
	}
	
	
	
	public void setListening(boolean listening){
		if(listening){
			InputHandler.addListener(this);
		}else{
			InputHandler.removeListener(this);
		}
	}

	@Override
	public void keyEvent(int lwjglKey, boolean keyState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoveEvent(int x, int y) {
		if(this.aabb.contains(x, y)){
			this.isHovered = true;
			return;
		}else{
			this.isHovered = false;
			return;
		}
		
	}

	@Override
	public void mouseButtonEvent(int x, int y, int lwjglButton, boolean buttonState) {
		if(this.aabb.contains(x, y)){
			if(buttonState){
				this.isActive = true;
			}else if(this.isActive && !buttonState){
				this.isActive = false;
				this.isClicked = true;
			}else{
				this.isActive = false;
				this.isClicked = false;
			}
			return;
		}else{
			this.isActive = false;
			this.isClicked = false;
			return;
		}
		
	}

	@Override
	public void mouseWheelEvent(int x, int y, int wheelScroll) {
		// TODO Auto-generated method stub
		
	}
	
}
