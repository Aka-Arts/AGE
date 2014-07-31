package org.akaarts.AGE;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Engine {
	final static int AVG_FPS = 60;
	final static int DEF_WIDTH = 1280;
	final static int DEF_HEIGHT = 720;
	
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
		
		Engine.loop();
		Engine.stop();
		
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
			Display.update();
			Display.sync(AVG_FPS);
		}
		Console.info("Leaving loop...");
	}
	
	public static void main(String[] args) {
		Engine.start();
	}

}
