package org.akaarts.AGE.graphics.gui;

import org.akaarts.AGE.Console;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HudView {
	
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

	
	
	
}
