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
import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

public class Hud {
	
	private static Map<String,HudView> allViews = new HashMap<String,HudView>(10);
	private static ArrayList<String> currentViews = new ArrayList<String>(4);
	private static Iterator<String> iterator;
	
	private static Hud self = new Hud();
	
	private Hud(){}
	
	public static void draw(){
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		iterator = currentViews.iterator();
		while(iterator.hasNext()){
			String view = iterator.next();
			if(view!=null){
				allViews.get(view).draw();
			}
		}
	}
	
	public static void update(long delta, boolean wasResized){
		for(String viewName : currentViews){
			allViews.get(viewName).update(delta);
		}
	}
	
	
	public static boolean showView(String viewName){
		if(currentViews.contains(viewName)){
			return true;
		} else if(allViews.get(viewName)==null){
			return false;
		} else {
			currentViews.add(viewName);
			InputHandler.addListener(allViews.get(viewName));
			return true;
		}
	}
	
	public static void hideView(String viewName){
		if(currentViews.contains(viewName)){
			currentViews.remove(viewName);
			InputHandler.removeListener(allViews.get(viewName));
		} else {
			Console.warning("No view with given name active: "+viewName);
		}
	}

	
	public static void clearCurrentView(){
		currentViews.clear();
	}
	
	private static void cleanMem(){
		for(Map.Entry<String, HudView> entry : allViews.entrySet()){
			entry.getValue().destroy();
		}
	}
	
	public static void destroy(){
		cleanMem();
		Console.info("Hud destroyed");
	}
	
	/**
	 * Clears the old Hud and loads a Hud from a json File
	 * @param path - The path to the file
	 * @return - true on success, else false
	 */
	public static boolean loadHudJSON(String path){
		cleanMem();
		allViews.clear();
		currentViews.clear();
		
		String parentPath = Paths.get(path).getParent().toString()+"/";
		String RawJSON;
		JSONObject jsonObj;
		JSONObject jsonViews,jsonElements;
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
			jsonViews = jsonObj.getJSONObject("hudViews");
			jsonElements = jsonObj.getJSONObject("hudElements");
		} catch(JSONException e){
			e.printStackTrace();
			Console.error("Error at finding 'hudViews'/'hudElements' in: "+path);
			return false;
		}
		
		
		Iterator<?> keys = jsonViews.keys();
		
		while(keys.hasNext()){
			String key = keys.next().toString();
			Console.info("Loading view: "+key);
			allViews.put(key, HudView.loadView(jsonViews.getJSONObject(key),jsonElements, parentPath));
		}
		

		return true;
	}
	
	public static ArrayList<String> getActiveNames(){
		return currentViews;
	}
	public static HudView getViewByName(String name){
		return allViews.get(name);
	}
}
