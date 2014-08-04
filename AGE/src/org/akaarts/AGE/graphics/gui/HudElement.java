package org.akaarts.AGE.graphics.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.nio.file.Path;

import org.akaarts.AGE.Console;
import org.json.JSONException;
import org.json.JSONObject;
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

	private static Path currentJSONPath;

	private int xPos, yPos, width, height;
	private Texture texture;
	private String xAlign, yAlign;
	private TrueTypeFont font;
	private float zPos;
	private Color fontColor;

	private HudElement(JSONObject jsonObject){
		String path = currentJSONPath.getParent().toString()+"/"+jsonObject.getString("backgroundImg");
		try {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			Console.warning("Could not find texture: "+path);
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
		this.xAlign = jsonObject.getString("xAlign");
		this.zPos = (float) jsonObject.getDouble("zPos");

		String fontPath = jsonObject.getString("font");
		if(!fontPath.equals("")){
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

	public void draw(){
		Console.info("Draw element");
		Color.magenta.bind();
		this.texture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(100,100);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(100+texture.getTextureWidth(),100);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(100+texture.getTextureWidth(),100+texture.getTextureHeight());
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(100,100+texture.getTextureHeight());
		GL11.glEnd();
		

	}

	public static boolean loadElements(JSONObject jsonObject, Path path) {
		currentJSONPath = path;
		try{
			HudElement.AGE_LAUNCHER_EXIT = new HudElement(jsonObject.getJSONObject("AGE_LAUNCHER_EXIT"));
		} catch(JSONException e){
			e.printStackTrace();
			Console.info("Could not load AGE_LAUNCHER_EXIT hudElement");
			return false;
		}

		return true;
	}

}
