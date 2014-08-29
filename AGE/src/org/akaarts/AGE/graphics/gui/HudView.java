package org.akaarts.AGE.graphics.gui;

import org.akaarts.AGE.Console;
import org.akaarts.AGE.input.InputListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.util.Point;

public class HudView implements InputListener {
	
	private HudElement[] hudElements;

	public HudView(JSONArray elementNames,JSONObject elements, String path) {
		hudElements = new HudElement[elementNames.length()];
		for(int ct = 0;ct<elements.length();ct++){
			hudElements[ct] = HudElement.loadElement(elements.getJSONObject(elementNames.getString(ct)), path);
		}
	}

	public void draw() {
		for(HudElement elem : hudElements){
			elem.draw();
		}
	}

	public static HudView loadView(JSONObject jsonView,JSONObject jsonElements, String path) {
		HudView newView;
		try{
			JSONArray elements = jsonView.getJSONArray("elements");
			newView = new HudView(elements,jsonElements, path);
		} catch(JSONException e){
			e.printStackTrace();
			Console.info("Could not load AGE_LAUNCHER_EXIT hudElement");
			return null;
		}

		return newView;
	}

	public void destroy() {
		for(HudElement elem : hudElements){
			elem.destroy();
		}
	}

	public void update(long delta) {
		for(HudElement elem:this.hudElements){
			elem.update(delta);
		}
		
	}

	@Override
	public boolean keyEvent(int lwjglKey, boolean keyState) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseEvent(int x, int y, int lwjglButton, boolean buttonState) {
		for(HudElement elem:hudElements){
			elem.pushMouse(x, y, lwjglButton, buttonState);
		}
		return false;
	}	
	
}
