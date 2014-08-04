package org.akaarts.AGE.graphics.gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import org.akaarts.AGE.Console;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Hud {
	
	private static ArrayList<HudElement> hudElements = new ArrayList<HudElement>(30);
	private static Iterator<HudElement> iterator = hudElements.iterator();
	
	private Hud(){}
	
	public static void draw(){
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		iterator = hudElements.iterator();
		while(iterator.hasNext()){
			HudElement elem = iterator.next();
			if(elem!=null){
				elem.draw();
			}
		}
	}
	
	private static void addElement(HudElement elem){
		if(hudElements.contains(elem)){
			return;
		}
		Console.info("Add element");
		hudElements.add(elem);
		
		return;
	}
	
	public static void clearElements(){
		hudElements.clear();
	}
	
	/**
	 * Clears the old Hud and loads a Hud from a json File
	 * @param path - The path to the file
	 * @return - true on success, else false
	 */
	public static boolean loadHudJSON(String path){
		hudElements.clear();
		String RawJSON;
		JSONObject jsonObj;
		JSONObject jsonSub;
		try {
			RawJSON = new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			e.printStackTrace();
			Console.error("Could not load HUD from: "+path);
			return false;
		}
		
		try {
			jsonObj = new JSONObject(RawJSON);
		} catch(JSONException e){
			e.printStackTrace();
			Console.error("Malformed JSON in hudfile: "+path);
			return false;
		}
		
		try {
			jsonSub = jsonObj.getJSONObject("hudElements");
		} catch(JSONException e){
			e.printStackTrace();
			Console.error("Error at finding 'hudElements' in: "+path);
			return false;
		}
		
		
		
		
		boolean success = HudElement.loadElements(jsonSub,Paths.get(path));
		addElement(HudElement.AGE_LAUNCHER_EXIT);
		return success;
	}
	
}
