package org.akaarts.AGE;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.akaarts.AGE.graphics.gui.Hud;
import org.akaarts.AGE.input.InputHandler;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Engine {
	final static int AVG_FPS = 120;
	final static int DEF_WIDTH = 1280;
	final static int DEF_HEIGHT = 720;
	final static String LAUNCHER_TITLE = "AGE - ";
	
	static boolean showSysInf = true;
	
	static long lastFrame = getTime();
	
	
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
			setTitle("Launcher");
			setIcons(new String[]{"assets/AGE_128.png","assets/AGE_32.png","assets/AGE_16.png"});
		try {
			Display.create();
		} catch (LWJGLException e) {
			Console.error("Error at creating Display");
			e.printStackTrace();
		}		
		Engine.setupGL();
		
		Hud.loadHudJSON("assets/huds/launcher.json");
		Hud.activateView("AGE_VIEW_HOME");
		
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
		
		Hud.destroy();
		
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
			long delta = getDelta();
			
			if(showSysInf){
				if(delta <= 0){
					setTitle("FPS: "+1000/1);
				} else {
					setTitle("FPS: "+1000/delta);
				}
			}
			
			InputHandler.update();
			
			Hud.update(delta, Display.wasResized());
			Hud.draw();
			
			Display.update();
			Display.sync(AVG_FPS);

			
		}
		Console.info("Leaving loop...");
	}
	

	
	public static long getDelta(){
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		 
		return delta;
	}
	
	public static long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public static void main(String[] args) {
		Engine.start();
	}
	
	public static void setIcons(String[] paths){
		ByteBuffer[] icons = new ByteBuffer[paths.length];
		int ct = 0;
		for(String path:paths){
			BufferedImage img = null;
			try {
			    img = ImageIO.read(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			int[] pixels = new int[img.getWidth() * img.getHeight()];
	        img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());

	        ByteBuffer buffer = BufferUtils.createByteBuffer(img.getWidth() * img.getHeight() * 4); //4 for RGBA, 3 for RGB
	        
	        for(int y = 0; y < img.getHeight(); y++){
	            for(int x = 0; x < img.getWidth(); x++){
	                int pixel = pixels[y * img.getWidth() + x];
	                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
	                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
	                buffer.put((byte) (pixel & 0xFF));               // Blue component
	                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
	            }
	        }

	        buffer.flip();
			icons[ct] = buffer;
			ct++;
		}
		Display.setIcon(icons);
	}
	public static void setTitle(String title){
		Display.setTitle(LAUNCHER_TITLE+title);
	}

}
