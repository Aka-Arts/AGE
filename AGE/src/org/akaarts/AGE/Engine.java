package org.akaarts.AGE;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Engine {
	final int DEF_WIDTH = 1280;
	final int DEF_HEIGHT = 720;

	int initWidth;
	int initHeight;

	public Engine() {

		initWidth = DEF_WIDTH;
		initHeight = DEF_HEIGHT;
		
	}

	public void start() {
		Console.info("Starting AGE...");
		try {
			Display.setDisplayMode(new DisplayMode(initWidth, initHeight));
		} catch (LWJGLException e) {
			Console.error("Error at setting DisplayMode");
			e.printStackTrace();
		}
		try {
			Display.create();
		} catch (LWJGLException e) {
			Console.error("Error at creating Display");
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop() {
		Console.info("Stopping AGE...");
		Display.destroy();
		
		Console.info("Bye!");
	}

}
