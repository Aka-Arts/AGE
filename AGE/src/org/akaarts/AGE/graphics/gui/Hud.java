package org.akaarts.AGE.graphics.gui;

import java.util.ArrayList;
import java.util.Iterator;

public class Hud {
	
	private static ArrayList<HudElement> hudElements = new ArrayList<HudElement>(30);
	private static Iterator<HudElement> iterator = hudElements.iterator();
	
	private Hud(){}
	
	public static void draw(){
		while(iterator.hasNext()){
			HudElement elem = iterator.next();
			if(elem!=null){
				elem.draw();
			}
		}
	}
	
	public static int addElement(HudElement elem){
		if(hudElements.contains(elem)){
			return hudElements.indexOf(elem);
		}
		hudElements.add(elem);
		iterator = hudElements.iterator();
		return hudElements.indexOf(elem);
	}
	
	public static void deleteElement(int id){
		hudElements.remove(id);
	}
	
}
