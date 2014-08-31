package org.akaarts.AGE.graphics.gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.akaarts.AGE.Console;
import org.akaarts.AGE.input.InputHandler;
import org.akaarts.AGE.input.InputListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

public class Hud implements InputListener{
	
	private static ArrayList<HudElement> elements = new ArrayList<HudElement>(4);
	
	private static JSONObject hudFile;
	private static String filePath;
	
	private static Hud self = new Hud();
	
	private Hud(){}
	
	public static void draw(){
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		for(HudElement element:elements){
			if(element!=null){
				element.draw();
			}
		}
		
	}
	
	public static void update(long delta, boolean wasResized){
		for(HudElement element:elements){
			element.update(delta);
		}
	}
	
	public static void destroyElements(){
		for(HudElement element:elements){
			element.destroy();
		}
	}
	
	public static void destroy(){
		destroyElements();
		Console.info("Hud destroyed");
	}
	
	public static void addElement(HudElement elem){
		elements.add(elem);
	}
	
	public static void loadPreset(String name){
		
		if(hudFile==null){
			Console.error("No Hudfile Set!");
			return;
		}
		
		JSONArray elementNames;
		try{
			elementNames = hudFile.getJSONObject("HUDPRESETS").getJSONArray(name);
		}catch(JSONException e){
			e.printStackTrace();
			Console.error("Failed to load Preset: "+name);
			return;
		}
		
		destroyElements();
		
		for(int ct = 0;ct < elementNames.length();ct++){
			addElement(new HudElement(HudElement.getElement(elementNames.getString(ct))));
		}
		
	}
	
	/**
	 * Loads the new reference-file for hud elements
	 * @param path - The path to the file
	 */
	public static void setFile(String path){

		String RawJSON;
		try {
			RawJSON = new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			e.printStackTrace();
			Console.error("Could not load HUD from: "+path);
			return;
		}
		
		try {
			hudFile = new JSONObject(RawJSON);
		} catch(JSONException e){
			e.printStackTrace();
			Console.error("Malformed JSON in hudfile: "+path);
			return;
		}

		filePath = Paths.get(path).getParent().toString()+"/";
	}
	
	public static JSONObject getFile(){
		return hudFile;
	}
	
	public static String getPath(){
		return filePath;
	}
	
	public static Hud self(){
		return self;
	}

	@Override
	public boolean keyEvent(int lwjglKey, boolean keyState) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseEvent(int x, int y, int lwjglButton, boolean buttonState) {
		for(HudElement elem:elements){
			elem.pushMouse(x, y, lwjglButton, buttonState);
		}
		return false;
	}
	
}
