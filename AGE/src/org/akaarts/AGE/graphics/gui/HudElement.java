package org.akaarts.AGE.graphics.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import org.akaarts.AGE.Console;
import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class HudElement{

	public static HudElement AGE_LAUNCHER_EXIT;

	public static String NOTEX = "assets/NOTEX.png";
	public static String NOFONT = "Arial";

	private int xPos, yPos, width, height, compX, compY;
	private Texture texture;
	private String xAlign, yAlign, textContent;
	private TrueTypeFont font;
	private float zPos, opacity, uvTop, uvLeft, uvBottom, uvRight;
	private Color fontColor;

	private HudElement(JSONObject hudConfig, String path){
		String imgPath = path+hudConfig.getString("backgroundImg");
		try {
			switch(hudConfig.optString("glTexFilter","NEAREST")){
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
		
		this.width = hudConfig.getInt("width");
		this.height = hudConfig.getInt("height");
		this.xPos = hudConfig.getInt("xPos");
		this.yPos = hudConfig.getInt("yPos");
		this.xAlign = hudConfig.getString("xAlign");
		this.yAlign = hudConfig.getString("yAlign");
		this.zPos = (float) hudConfig.getDouble("zPos");
		this.opacity = (float) hudConfig.getDouble("opacity");
		
		computePos();
		
		this.uvTop = (float) hudConfig.optDouble("uvTop", 0);
		this.uvLeft = (float) hudConfig.optDouble("uvLeft",0);
		this.uvBottom = (float) hudConfig.optDouble("uvBottom",1);
		this.uvRight = (float) hudConfig.optDouble("uvRight", 0);

		String fontPath = path+hudConfig.getString("font");
		if(!hudConfig.getString("font").equals("")){
			try {
				this.font = new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream(fontPath)).deriveFont((float) hudConfig.getDouble("fontSize")), false);
			} catch (IOException e) {
				e.printStackTrace();
				Console.warning("Could not find font: "+fontPath);
				this.font = new TrueTypeFont(new Font("Arial", Font.PLAIN, (int) hudConfig.getDouble("fontSize")),false);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FontFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Console.error("something with the font format went wrong...");
			}
		} else {
			this.font = new TrueTypeFont(new Font("Arial", Font.PLAIN, (int) hudConfig.getDouble("fontSize")),hudConfig.optBoolean("fontAntiAlias", true));
		}

		int r = hudConfig.getInt("fontColorR");
		int g = hudConfig.getInt("fontColorG");
		int b = hudConfig.getInt("fontColorB");
		int a = hudConfig.getInt("fontColorA");

		this.fontColor = new Color(r, g, b, a);
		
		this.textContent = hudConfig.optString("textContent", "");

	}
	
	private void computePos(){
		switch(this.xAlign){
		case "AGE_HUD_CENTER":
			this.compX = (Display.getWidth()/2+this.xPos)-this.width/2;
			break;
		case "AGE_HUD_RIGHT":
			this.compX = (Display.getWidth()-(this.xPos+this.width));
			break;
		case "AGE_HUD_LEFT":
		default:
			this.compX = this.xPos;
			break;
		}
		
		switch(this.yAlign){
		case "AGE_HUD_CENTER":
			this.compY = (Display.getHeight()/2+this.yPos)-this.height/2;
			break;
		case "AGE_HUD_BOTTOM":
			this.compY = (Display.getHeight()-(this.yPos+this.height));
			break;
		case "AGE_HUD_TOP":
		default:
			this.compY = this.yPos;
			break;
		}
	}

	public void draw(){
		this.texture.bind();
		
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
		
		this.font.drawString(this.compX, this.compY, textContent, fontColor);
		

	}

	public static HudElement loadElement(JSONObject hudConfig, String path) {
		HudElement newElem;
		try{
			newElem = new HudElement(hudConfig, path);
		} catch(JSONException e){
			e.printStackTrace();
			Console.info("Could not load AGE_LAUNCHER_EXIT hudElement");
			return null;
		}

		return newElem;
	}
	
	public void update(long delta){
		computePos();
	}

	public void destroy() {
		this.texture.release();
		Console.info("Release the texture!");
	}

}
