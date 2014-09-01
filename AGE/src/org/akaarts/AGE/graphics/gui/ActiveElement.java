package org.akaarts.AGE.graphics.gui;

import java.io.IOException;

import org.akaarts.AGE.CLI.Console;
import org.akaarts.AGE.input.InputListener;
import org.json.JSONObject;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class ActiveElement extends HudElement implements InputListener {
	
	private Rectangle aabb;
	private boolean hover,click,press;
	private String clickCommand;
	private Texture hoverTexture, pressTexture;

	public ActiveElement() {
		super();
		
		computeBox();
	}
	
	public ActiveElement(JSONObject elem) {
		super(elem);
		
		String path = Hud.getPath();

		if(!elem.optString("hoverImage").isEmpty()){
			String imgPath = path+elem.getString("hoverImage");
			try {
				switch(elem.optString("glTexFilter","NEAREST")){
				case "LINEAR":
					this.hoverTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(imgPath),GL11.GL_LINEAR);
					break;
				case "NEAREST":
				default:		
					this.hoverTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(imgPath),GL11.GL_NEAREST);
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
				Console.warning("Could not load texture: "+imgPath);
				try {
					this.hoverTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(NOTEX));
				} catch (IOException e1) {
					e1.printStackTrace();
					Console.error("Could not find NOTEX.png? Ah, rubbish...");
				}
			}
		}else{
			hoverTexture = null;
		}
		
		if(!elem.optString("pressImage").isEmpty()){
			String imgPath = path+elem.getString("pressImage");
			try {
				switch(elem.optString("glTexFilter","NEAREST")){
				case "LINEAR":
					this.pressTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(imgPath),GL11.GL_LINEAR);
					break;
				case "NEAREST":
				default:		
					this.pressTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(imgPath),GL11.GL_NEAREST);
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
				Console.warning("Could not load texture: "+imgPath);
				try {
					this.pressTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(NOTEX));
				} catch (IOException e1) {
					e1.printStackTrace();
					Console.error("Could not find NOTEX.png? Ah, rubbish...");
				}
			}
		}else{
			pressTexture = null;
		}
		
		this.clickCommand = elem.optString("clickCommand", "");
		
		computeBox();
	}
	
	/**
	 * Computes the aabb
	 */
	private void computeBox(){
		this.aabb = new Rectangle(this.compX,this.compY,this.width,this.height);
	}
	
	/**
	 * Draws the element
	 */
	public void draw(){
		
		if(this.press&&this.pressTexture!=null){
			this.pressTexture.bind();
		}else if(this.hover&&this.hoverTexture!=null){
			this.hoverTexture.bind();
		}else if(this.texture!=null){
			this.texture.bind();
		}else{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor4f(1f, 1f, 1f, this.opacity);
			GL11.glTexCoord2f(this.uvLeft,this.uvTop);
			GL11.glVertex2f(this.compX,this.compY);
			GL11.glTexCoord2f(this.uvRight,this.uvTop);
			GL11.glVertex2f(this.compX+this.width,compY);
			GL11.glTexCoord2f(this.uvRight,this.uvBottom);
			GL11.glVertex2f(this.compX+this.width,this.compY+this.height);
			GL11.glTexCoord2f(this.uvLeft,this.uvBottom);
			GL11.glVertex2f(this.compX,this.compY+this.height);
		GL11.glEnd();
		
		if(!this.textContent.isEmpty()){
			this.font.drawString(this.compX+this.xFontOff, this.compY+this.yFontOff, textContent, fontColor);
		}
	}
	
	/**
	 * updates the button
	 * @param delta - difference in milliseconds since last frame
	 */
	public void update(long delta){
		
		if(hover){
			
		}
		if(press){
			
		}
		if(click){
			if(!this.clickCommand.isEmpty()){
				Console.queueCommands(clickCommand);
			}
			click = false;
		}
	}
	
	/**
	 * Destroys all textures
	 */
	public void destroy() {
		this.texture.release();
		if(hoverTexture!=null){
			this.hoverTexture.release();
		}
		if(pressTexture!=null){
			this.pressTexture.release();
	}
		Console.info("Release the texture!");
	}
	
	/**
	 * Sets a new Console command for click events
	 * @param command - the new Console Command
	 */
	public void setCommand(String command){
		this.clickCommand = command;
	}
	
	/**
	 * Sets a new position for this element
	 * @param x - the new x
	 * @param y - the new y
	 */
	public void setPos(int x, int y){
		this.xPos = x;
		this.yPos = y;
		
		computePos();
		computeBox();
	}
	
	/**
	 * Sets the alignment rule for x (LEFT,CENTER,RIGHT) an y (TOP,CENTER,BOTTOM)
	 * @param x - The rule for x
	 * @param y - The rule for y
	 */
	public void setElementAlign(String x, String y){
		this.xAlign = x;
		this.yAlign = y;
		
		computePos();
		computeBox();
	}
	
	
	//Interface Methods
	@Override
	public void keyEvent(int lwjglKey, boolean keyState) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoveEvent(int x, int y) {
		if(this.aabb.contains(x, y)){
			hover = true;
			return;
		}else{
			hover = false;
			return;
		}
	}
	
	public void mouseButtonEvent(int x, int y, int lwjglButton, boolean buttonState){
		if(this.aabb.contains(x, y)){
			if(buttonState){
				press = true;
			}else if(press == true && !buttonState){
				press = false;
				click = true;
			}else{
				press = false;
				click = false;
			}
			return;
		}else{
			press = false;
			click = false;
			return;
		}
		
	}

	@Override
	public void mouseWheelEvent(int x, int y, int wheelScroll) {
		// TODO Auto-generated method stub

	}

}
