package org.akaarts.AGE.graphics.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.color.CMMException;
import java.util.ArrayList;

import org.akaarts.AGE.CLI.Console;
import org.akaarts.AGE.graphics.Texture2D;
import org.akaarts.AGE.input.InputHandler;
import org.akaarts.AGE.input.InputListener;
import org.akaarts.AGE.utils.UVMap4;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class HudElement implements InputListener{

	private static HudElement root = new HudElement();
	
	private HudElement parent;
	
	private int width,height;
	
	private int originX,originY;
	private int positionX,positionY,relativeX,relativeY;
	
	private Color backgroundColor;

	public String onHover, onActive, onClick, onEnter, onLeave;
	
	private boolean isHovered, isActive, wasClicked, wasEntered, wasLeft;
	
	private Rectangle aabb;
	
	private TextElement text;
	
	public Texture2D texture, textureHover, textureActive;
	private UVMap4 uv, uvHover, uvActive;
	
	
	
	public final String NOTEX = "/assets/defaults/NOTEX.png";
	
	public final boolean ISROOT;
	
	private ArrayList<HudElement> children = new ArrayList<HudElement>();
	
	public static final int ORIGIN_CENTER = 0,
							ORIGIN_TOP = 1, 
							ORIGIN_BOTTOM = 2, 
							ORIGIN_LEFT = 3, 
							ORIGIN_RIGHT = 4,
							STATE_NORMAL = 5,
							STATE_HOVER = 6,
							STATE_ACTIVE = 7,
							STATE_CLICK = 8,
							STATE_ENTER = 9,
							STATE_LEAVE = 10;
	
	/**
	 * Constructor only for the root element
	 * Setting all default styles
	 */
	private HudElement() {
		
		this.ISROOT = true;
		
		this.applyDefaultStyle();
		
		this.setBackgroundColor(new Color(1,1,1,0));
		
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

		if(this.isActive&&this.textureActive!=null){
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureActive.ID);
		}else if(this.isHovered&&this.textureHover!=null){
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureHover.ID);			
		}else if(this.texture!=null){
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture.ID);			
		}else{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}
		
		UVMap4 uvMap4 = this.uv;
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor4f(
					(1/255f)*this.backgroundColor.getRed(),
					(1/255f)*this.backgroundColor.getGreen(),
					(1/255f)*this.backgroundColor.getBlue(),
					(1/255f)*this.backgroundColor.getAlpha());
			GL11.glTexCoord2f(uvMap4.U[0], uvMap4.V[0]);
			GL11.glVertex2i(this.positionX, this.positionY);
			GL11.glTexCoord2f(uvMap4.U[1], uvMap4.V[1]);
			GL11.glVertex2i(this.positionX+this.width, this.positionY);
			GL11.glTexCoord2f(uvMap4.U[2], uvMap4.V[2]);
			GL11.glVertex2i(this.positionX+this.width, this.positionY+this.height);
			GL11.glTexCoord2f(uvMap4.U[3], uvMap4.V[3]);
			GL11.glVertex2i(this.positionX, this.positionY+this.height);
		GL11.glEnd();
		
		if(this.text != null) {
			this.text.draw(this.backgroundColor.getAlpha());
		}
		
		//for debugging
//		if(this.isClicked){
//			Console.info(this.hashCode()+" was clicked!");
//		}else if(this.isActive){
//			Console.info(this.hashCode()+" is pressed!");
//		}else if(this.isHovered){
//			Console.info(this.hashCode()+" is hovered!");
//		}
		
		// queue enter commands if there are any
		if(this.wasEntered&&this.onEnter!=null){
			Console.queueCommands(onEnter);
		}
		// queue hover commands if there are any
		if(this.isHovered&&this.onHover!=null){
			Console.queueCommands(onHover);			
		}
		// queue active commands if there are any
		if(this.isActive&&this.onActive!=null){
			Console.queueCommands(onActive);		
		}
		// queue click commands if there are any
		if(this.wasClicked&&this.onClick!=null){
			Console.queueCommands(onClick);
			this.wasClicked = false;
		}
		// queue leave commands if there are any
		if(this.wasLeft&&this.onLeave!=null){
			Console.queueCommands(onLeave);
		}
		
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
	 * Simply sets a new or no image for this elements normal state (UVs will be set to default and the scale filter is GL_NEAREST)
	 * @param path - the path to the new image or null for no image
	 */
	public void setBackgroundImage(String path){
		
		this.setBackgroundImage(path, STATE_NORMAL);
	}

	/**
	 * Sets a new or no image for this elements selected state (UVs will be set to default and the scale filter is GL_NEAREST)
	 * @param path - the path to the new image or null for no image
	 * @param state - the state to associate to image to (STATE_(ACTIVE|HOVER|NORMAL))
	 */
	public void setBackgroundImage(String path, int state){
		
		Texture2D tmp = null;

		if(path!=null){
			tmp = Texture2D.loadTexture2d(path);
		}
		
		switch(state){
		case STATE_ACTIVE:
			if(this.textureActive!=null){this.textureActive.destroy();}
			this.textureActive = tmp;
			this.uvActive = new UVMap4(0,0,1,0,1,1,0,1);
			break;
		case STATE_HOVER:
			if(this.textureHover!=null){this.textureHover.destroy();}
			this.textureHover = tmp;
			this.uvHover = new UVMap4(0,0,1,0,1,1,0,1);
			break;
		case STATE_NORMAL:
		default:
			if(this.texture!=null){this.texture.destroy();}
			this.texture = tmp;
			this.uv = new UVMap4(0,0,1,0,1,1,0,1);
			break;
		}
	}
	
	/**
	 * Sets a new or no image for this elements selected state
	 * @param path - the path to the new image or null for no image
	 * @param state - the state to associate the image to
	 * @param filter - the GL_filter to use (Nearest or Linear)
	 * @param uvs - an UVMap4 to use with the selected state
	 */
	public void setBackgroundImage(String path, int state, int filter, UVMap4 uvs){
		
		Texture2D tmp = null;
		
		if(!(filter==GL11.GL_NEAREST||filter==GL11.GL_LINEAR)){
			filter = GL11.GL_NEAREST;
		}
		
		if(path!=null){
			tmp = Texture2D.loadTexture2d(path);
		}
		
		switch(state){
		case STATE_ACTIVE:
			if(this.textureActive!=null){this.textureActive.destroy();}
			this.textureActive = tmp;
			this.uvActive = uvs;
			break;
		case STATE_HOVER:
			if(this.textureHover!=null){this.textureHover.destroy();}
			this.textureHover = tmp;
			this.uvHover = uvs;
			break;
		case STATE_NORMAL:
		default:
			if(this.texture!=null){this.texture.destroy();}
			this.texture = tmp;
			this.uv = uvs;
			break;
		}
	}
	
	public void setText(String text) {
		this.setText(text, TextElement.STDFONT,TextElement.STDSIZE, Font.PLAIN);
	}
	
	public void setText(String text, int size) {
		this.setText(text, TextElement.STDFONT, size, Font.PLAIN);
	}
	
	public void setText(String text, String fontPath, int fontsize, int fontStyle) {
		if(text==null) {
			this.text = null;
			return;
		}
		this.text = new TextElement(this.positionX, this.positionY, this.width, this.height);
		this.text.setText(text);
		this.text.setFont(fontPath);
		this.text.setSize(fontsize);
		this.text.setSytle(fontStyle);
		this.text.update();
	}
	
	/**
	 * Destroys all own textures and calls destroy() on it's children
	 */
	public void destroy() {
		if(this.texture!=null) {
			this.texture.destroy();
		}
		if(this.textureActive!=null) {
			this.textureActive.destroy();
		}
		if(this.textureHover!=null) {
			this.textureHover.destroy();
		}
		if(this.text!=null) {
			this.text.destroy();
		}
		for(HudElement child:children){
			child.destroy();
		}
	}
	
	/**
	 * Sets, if this element is listening to mouse/key events
	 * @param listening - if listening
	 */
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
			if(!this.isHovered){
				this.wasEntered = true;
			}else{
				this.wasEntered = false;
			}
			this.isHovered = true;
			return;
		}else{
			if(this.isHovered){
				this.wasLeft = true;
			}else{
				this.wasLeft = false;
			}
			this.isHovered = false;
			return;
		}
		
	}

	@Override
	public void mouseButtonEvent(int x, int y, int lwjglButton, boolean buttonState) {
		if(this.aabb.contains(x, y)){
			if(buttonState){
				// press
				this.isActive = true;
			}else if(this.isActive && !buttonState){
				// click
				this.isActive = false;
				this.wasClicked = true;
			}else{
				// nothing
				this.isActive = false;
				this.wasClicked = false;
			}
			return;
		}else{
			this.isActive = false;
			this.wasClicked = false;
			return;
		}
		
	}

	@Override
	public void mouseWheelEvent(int x, int y, int wheelScroll) {
		// TODO Auto-generated method stub
		
	}
	
}
