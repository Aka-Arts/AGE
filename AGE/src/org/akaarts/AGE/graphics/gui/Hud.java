package org.akaarts.AGE.graphics.gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Hud {
	
	 private static final HudElement ROOT = HudElement.getRoot();
	 
	 public static void update() {
		 
		 ROOT.update(); 
	 }

	 public static void draw() {
		 
		 // switch matrix mode
		 GL11.glMatrixMode(GL11.GL_PROJECTION);
		 GL11.glLoadIdentity();
		 GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		 GL11.glMatrixMode(GL11.GL_MODELVIEW);
		 
		 ROOT.draw();
	 }
	 
	 public static void destroy() {
		 ROOT.destroy();
	 }
	
}
