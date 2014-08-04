package org.akaarts.AGE;

import org.akaarts.AGE.graphics.gui.Hud;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Engine {
	final static int AVG_FPS = 60;
	final static int DEF_WIDTH = 1280;
	final static int DEF_HEIGHT = 720;
	
	private static byte mode;
	
	protected static boolean  closeRequested = false;

	private Engine() {
		
	}
	/**
	 * Starts the engine and enters loop
	 */
	public static void start() {
		
		Console.info("Starting AGE...");
		try {
			Display.setDisplayMode(new DisplayMode(DEF_WIDTH, DEF_HEIGHT));
		} catch (LWJGLException e) {
			Console.error("Error at setting DisplayMode");
			e.printStackTrace();
		}
			Display.setTitle("AGE Launcher");
		try {
			Display.create();
		} catch (LWJGLException e) {
			Console.error("Error at creating Display");
			e.printStackTrace();
		}
		
		Hud.loadHudJSON("assets/huds/launcher.json");
		
		Engine.setupGL();
		
		Engine.loop();
		Engine.stop();
		
	}
	private static void setupGL() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);               
        
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);          
        
        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        GL11.glViewport(0,0,Display.getWidth(),Display.getHeight());
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
	}
	/**
	 * Stops engine and cleans up
	 */
	private static void stop() {
		Console.info("Stopping AGE...");
		Display.destroy();
		
		Console.info("Bye!");
	}
	
	/**
	 * the main loop of the engine
	 */
	private static void loop(){
		Console.info("Entering loop...");
		while(!(closeRequested||Display.isCloseRequested())){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			Hud.draw();
			
			Display.update();
			Display.sync(AVG_FPS);
		}
		Console.info("Leaving loop...");
	}
	
	public static void main(String[] args) {
		Engine.start();
	}

}
