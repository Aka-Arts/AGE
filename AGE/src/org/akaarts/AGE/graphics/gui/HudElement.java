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

public class HudElement {

	public static HudElement AGE_LAUNCHER_EXIT;

	public static String NOTEX = "assets/NOTEX.png";
	public static String NOFONT = "Arial";

	private int xPos, yPos, width, height, compX, compY;
	private Texture texture;
	private String xAlign, yAlign;
	private TrueTypeFont font;
	private float zPos;
	private Color fontColor;

	private HudElement(JSONObject jsonObject, String path){
		String imgPath = path+jsonObject.getString("backgroundImg");
		try {
			switch(jsonObject.getString("glTexFilter")){
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
			Console.warning("Could not find texture: "+imgPath);
			try {
				this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(NOTEX));
			} catch (IOException e1) {
				e1.printStackTrace();
				Console.error("Could not find NOTEX.png? Ah, rubbish...");
			}
		}
		
		this.width = jsonObject.getInt("width");
		this.height = jsonObject.getInt("height");
		this.xPos = jsonObject.getInt("xPos");
		this.yPos = jsonObject.getInt("yPos");
		this.xAlign = jsonObject.getString("xAlign");
		this.yAlign = jsonObject.getString("yAlign");
		this.zPos = (float) jsonObject.getDouble("zPos");
		
		computePos();

		String fontPath = path+jsonObject.getString("font");
		if(!jsonObject.getString("font").equals("")){
			try {
				this.font = new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream(fontPath)).deriveFont((float) jsonObject.getDouble("fontSize")), false);
			} catch (IOException e) {
				e.printStackTrace();
				Console.warning("Could not find font: "+fontPath);
				this.font = new TrueTypeFont(new Font("Arial", Font.PLAIN, (int) jsonObject.getDouble("fontSize")),false);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FontFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Console.error("something with the font format went wrong...");
			}
		} else {
			this.font = new TrueTypeFont(new Font("Arial", Font.PLAIN, (int) jsonObject.getDouble("fontSize")),false);
		}

		int r = jsonObject.getInt("fontColorR");
		int g = jsonObject.getInt("fontColorG");
		int b = jsonObject.getInt("fontColorB");
		int a = jsonObject.getInt("fontColorA");

		this.fontColor = new Color(r, g, b, a);
		


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
		Color.magenta.bind();
		this.texture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(1f, 1f, 1f);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(this.compX,this.compY);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(this.compX+this.width,compY);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(this.compX+this.width,this.compY+this.height);
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(this.compX,this.compY+this.height);
		GL11.glEnd();
		

	}

	public static HudElement loadElement(JSONObject jsonObject, String path) {
		HudElement newElem;
		try{
			newElem = new HudElement(jsonObject, path);
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
