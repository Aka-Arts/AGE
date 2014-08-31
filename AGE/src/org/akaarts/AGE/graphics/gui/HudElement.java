package org.akaarts.AGE.graphics.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import org.akaarts.AGE.Console;
import org.akaarts.AGE.input.InputListener;
import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class HudElement{

	public static String NOTEX = "assets/defaults/NOTEX.png";
	public static String NOFONT = "assets/defaults/font.ttf";

	private int xPos, yPos, width, height, compX, compY, xFont, yFont, xFontOff, yFontOff;
	private Texture texture,hoverTexture;
	private String xAlign, yAlign, fontXAlign, fontYAlign, textContent, clickCommand;
	private TrueTypeFont font;
	private float zPos, opacity, uvTop, uvLeft, uvBottom, uvRight;
	private Color fontColor;
	private Rectangle aabb;
	private boolean hover,click,press;
	
	public HudElement(){
		this.width = 100;
		this.height = 100;
		this.xPos = 0;
		this.yPos = 0;
		this.zPos = 0;
		
		this.xAlign = "CENTER";
		this.yAlign = "CENTER";
		
		this.uvLeft = 0;
		this.uvTop = 0;
		this.uvBottom = 1;
		this.uvRight = 1;
		
		this.opacity = 1;
		this.fontColor = Color.white;
		this.fontXAlign = "CENTER";
		this.fontYAlign = "CENTER";

		
		try {
			this.font = new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream(NOFONT)).deriveFont(10), true);
		} catch (FontFormatException | IOException e) {
			Console.error("Failed to load default font from: "+NOFONT);
			e.printStackTrace();
		}
			
		
		
		computePos();
	}

	public HudElement(JSONObject elem){
		
		String path = Hud.getPath();
		
		if(!elem.optString("image").isEmpty()){
			String imgPath = path+elem.getString("image");
			try {
				switch(elem.optString("glTexFilter","NEAREST")){
				case "LINEAR":
					this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(imgPath),GL11.GL_LINEAR);
					break;
				case "NEAREST":
				default:		
					this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(imgPath),GL11.GL_NEAREST);
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
				Console.warning("Could not load texture: "+imgPath);
				try {
					this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(NOTEX));
				} catch (IOException e1) {
					e1.printStackTrace();
					Console.error("Could not find NOTEX.png? Ah, rubbish...");
				}
			}
		}else{
			try {
				this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(NOTEX));
			} catch (IOException e1) {
				e1.printStackTrace();
				Console.error("Could not find NOTEX.png? Ah, rubbish...");
			}
		}
		
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
		
		
		
		this.width = elem.optInt("width",100);
		this.height = elem.optInt("height",100);
		this.xPos = elem.optInt("xPos",0);
		this.yPos = elem.optInt("yPos",0);
		this.xAlign = elem.optString("xAlign","CENTER");
		this.yAlign = elem.optString("yAlign","CENTER");
		this.zPos = (float) elem.optDouble("zPos",0);
		this.opacity = (float) elem.optDouble("opacity",1);
		

		
		this.uvTop = (float) elem.optDouble("uvTop", 0);
		this.uvLeft = (float) elem.optDouble("uvLeft",0);
		this.uvBottom = (float) elem.optDouble("uvBottom",1);
		this.uvRight = (float) elem.optDouble("uvRight", 1);

		String fontPath = path+elem.optString("font");
		if(!elem.optString("font").isEmpty()){
			try {
				this.font = new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream(fontPath)).deriveFont((float) elem.optDouble("fontSize",10)), elem.optBoolean("fontAntiAlias"));
			} catch (IOException e) {
				e.printStackTrace();
				Console.warning("Could not find font: "+fontPath);
				this.font = new TrueTypeFont(new Font("Arial", Font.PLAIN, (int) elem.optDouble("fontSize",10)),false);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FontFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Console.error("something with the font format went wrong...");
			}
		} else {
			this.font = new TrueTypeFont(new Font("Arial", Font.PLAIN, (int) elem.optDouble("fontSize",10)),elem.optBoolean("fontAntiAlias", true));
		}

		int r = elem.optInt("fontColorR",255);
		int g = elem.optInt("fontColorG",255);
		int b = elem.optInt("fontColorB",255);
		int a = elem.optInt("fontColorA",255);
		
		this.xFont = elem.optInt("fontX",0);
		this.yFont = elem.optInt("fontY",0);
		this.fontXAlign = elem.optString("fontXAlign","CENTER");
		this.fontYAlign = elem.optString("fontYAlign","CENTER");

		this.fontColor = new Color(r, g, b, a);
		
		this.textContent = elem.optString("textContent", "");
		this.clickCommand = elem.optString("clickCommand", "");

		computePos();
	}
	
	/**
	 * Transforms the offset and align relative to the upper left corner
	 */
	private void computePos(){
		//box
		switch(this.xAlign){
		case "CENTER":
			this.compX = (Display.getWidth()/2+this.xPos)-this.width/2;
			break;
		case "RIGHT":
			this.compX = (Display.getWidth()-(this.xPos+this.width));
			break;
		case "LEFT":
		default:
			this.compX = this.xPos;
			break;
		}
		
		switch(this.yAlign){
		case "CENTER":
			this.compY = (Display.getHeight()/2+this.yPos)-this.height/2;
			break;
		case "BOTTOM":
			this.compY = (Display.getHeight()-(this.yPos+this.height));
			break;
		case "TOP":
		default:
			this.compY = this.yPos;
			break;
		}
		
		//font relative to box
		switch(this.fontXAlign){
		case "CENTER":
			this.xFontOff = (this.width/2+this.xFont)-this.font.getWidth(textContent)/2;
			break;
		case "RIGHT":
			this.xFontOff = (this.width-(this.xFont+this.font.getWidth(textContent)));
			break;
		case "LEFT":
		default:
			this.xFontOff = this.xFont;
			break;
		}
		
		switch(this.fontYAlign){
		case "CENTER":
			this.yFontOff = (this.height/2+this.yFont)-this.font.getHeight(textContent)/2;
			break;
		case "BOTTOM":
			this.yFontOff = (this.height-(this.yFont+this.font.getHeight(textContent)));
			break;
		case "TOP":
		default:
			this.yFontOff = this.yFont;
			break;
		}
		
		this.aabb = new Rectangle(this.compX,this.compY,this.width,this.height);
	}

	/**
	 * Draws the element
	 */
	public void draw(){
		if(this.hover&&this.hoverTexture!=null){
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

	public static JSONObject getElement(String name) {
		JSONObject elem;
		try{
			elem = Hud.getFile().getJSONObject("HUDELEMENTS").getJSONObject(name);
		}catch(JSONException e){
			e.printStackTrace();
			Console.error("Failed to load hudElement: "+name);
			return null;
		}

		return elem;
	}
	
	public void update(long delta){
		computePos();
		
		
		if(hover){
			
		}
		if(press){
			
		}
		if(click){
			if(!this.clickCommand.isEmpty()){
				Console.execute(clickCommand);
			}
			click = false;
		}
	}

	public void destroy() {
		this.texture.release();
		Console.info("Release the texture!");
	}

	public boolean pushMouse(int x, int y, int lwjglButton, boolean buttonState) {
		if(this.aabb.contains(x, y)){
			hover = true;
			if(buttonState){
				press = true;
			}else if(press == true && !buttonState){
				press = false;
				click = true;
			}else{
				press = false;
				click = false;
			}
			
			return true;
		}else{
			hover = false;
			click = false;
			press = false;
			return false;
		}
	}
}
