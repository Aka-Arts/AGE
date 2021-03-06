package org.akaarts.earlyalpha.AGE;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;

import org.akaarts.earlyalpha.AGE.CLI.Console;
import org.akaarts.earlyalpha.AGE.graphics.Color4f;
import org.akaarts.earlyalpha.AGE.graphics.Texture2D;
import org.akaarts.earlyalpha.AGE.graphics.gui.GUI;
import org.akaarts.earlyalpha.AGE.graphics.gui.GUIElement;
import org.akaarts.earlyalpha.AGE.graphics.gui.GUINode;
import org.akaarts.earlyalpha.AGE.graphics.gui.TextElement;
import org.akaarts.earlyalpha.AGE.graphics.text.FontManager;
import org.akaarts.earlyalpha.AGE.graphics.text.FontMap;
import org.akaarts.earlyalpha.AGE.input.InputHandler;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

/**
 * The main class of the AGE
 * @author luca.egli
 *
 */
public class Engine {
	final static int AVG_FPS = 120;
	final static int DEF_WIDTH = 1280;
	final static int DEF_HEIGHT = 720;
	final static String LAUNCHER_TITLE = "AGE - ";
	
	static boolean showSysInf = true;
	
	static long lastFrame = getTime();
	
	private static long delta;
	
	private static boolean  closeRequested = false;

	private Engine() {}
	
	/**
	 * Starts the engine and enters loop
	 */
	public static void start() {
		
		Console.info("Starting AGE...");
		
		// call the setups
		Engine.setup();
		Engine.setupGL();
		
//		//TODO output all display modes, remove later or move to debugging
//		Console.info("Available DisplayModes:");
//		for(DisplayMode mode:getDisplayModes()){
//			Console.info(mode.getWidth()+"x"+mode.getHeight()+" @ "+mode.getFrequency()+" Hz with "+mode.getBitsPerPixel()+" bpp");
//		}
		
		// go to the loop and stop after
		Engine.loop();
		Engine.stop();		
	}
	
	/**
	 * Sets the Display, title and icons up
	 */
	private static void setup(){
		
		Console.info(Display.getAdapter());
		
		// try to setup the display
		try {
			Display.setDisplayMode(new DisplayMode(DEF_WIDTH, DEF_HEIGHT));
		} catch (LWJGLException e) {
			e.printStackTrace();
			Engine.crashGracefully(e.getMessage() + "\r\n^ Error at setting DisplayMode");
		}
		
//		//TODO output desktop display mode in console, remove later or move to debugging
//		DisplayMode desktop = Display.getDesktopDisplayMode();
//		Console.info("Current Desktop Resolution: "+desktop.getWidth()+"x"+desktop.getHeight()+" @"+desktop.getFrequency()+" Hz with "+desktop.getBitsPerPixel()+" bits per pixel");
		
		// set the initial Title
		Engine.setTitle("AGE - Launcher");
		// set the initial LOGO
		Engine.setIcons(new String[]{"/assets/AGE_128.png","/assets/AGE_32.png","/assets/AGE_16.png"});
		
		// try to create the Display
		try {
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Engine.crashGracefully(e.getMessage() + "\r\n^ Error at creating Display");
		}
		
		GUINode container = new GUINode();
		container.setPositioning(0, 0, GUINode.ORIGIN_CENTER, GUINode.ORIGIN_TOP);
		container.setDimensions(128, 128);
		container.setBackgroundImage("/assets/defaults/AGE.png");
		container.setBackgroundColor(new Color4f(1, 1, 1, 1));
		container.setText("AGE!",40);
		GUI.ROOT.addChild(container);
		
	}
	
	/**
	 * Setup the GL space
	 */
	private static void setupGL() {
		
		GL11.glEnable(GL11.GL_TEXTURE_2D); 
		// black clear color
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);          
        
        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        // set the view port
        GL11.glViewport(0,0,Display.getWidth(),Display.getHeight());
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		Console.info("Maximum texture size: "+GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE));
		
	}
	
	/**
	 * the main loop of the engine
	 */
	private static void loop(){
		Console.info("Entering loop...");
		
		// main loop
		while(!(closeRequested||Display.isCloseRequested())){
			// clear the window
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			//update global delta
			updateDelta();
			
			//show FPS in title
			if(showSysInf){
				if(getDelta() <= 0){
					setTitle("FPS: inf");
				} else {
					setTitle("FPS: "+1000/getDelta());
				}
			}
			
			//update the input handler
			InputHandler.update();
			
			//execute all queued commands
			Console.executeQueue();
			
			GUI.update();
			
			//draw GUI
			GUI.draw();
			
			checkGLError();
			
			// update the display
			Display.update();			
			
			// sync to the preferred FPS
			Display.sync(AVG_FPS);

			
		}
		Console.info("Leaving loop...");
	}
	
	
	/**
	 * Stops engine and cleans up
	 */
	private static void stop() {
		Console.info("Stopping AGE...");		
		
		// destroy the hud
		GUI.destroy();
		
		FontManager.destroy();
		
		// destroy the display
		Display.destroy();
		
		Texture2D.checkUndestroyed();
		
		Console.info("Bye!");
	}
	
	/**
	 * Checks for a GL Error and outputs it to the console
	 */
	public static void checkGLError() {
		int errorCode = GL11.glGetError();
		
		if(errorCode!=GL11.GL_NO_ERROR) {
			Console.error("GL Error: "+errorCode+" / "+GLU.gluErrorString(errorCode));
		}
	}
	
	/**
	 * get the delta since the last frame
	 * @return - the delta in milliseconds
	 */
	private static long getDelta(){
		return delta;
	}
	
	/**
	 * update the global delta value, call once per frame
	 */
	private static void updateDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		
		Engine.delta = delta;
	}
	
	/**
	 * get the current time in milliseconds
	 * @return
	 */
	public static long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	/**
	 * java start method
	 * @param args
	 */
//	public static void main(String[] args) {
//		Engine.start();
//	}
	
	/**
	 * Set the display icon(s)
	 * @param paths - one or more paths to images
	 */
	public static void setIcons(String[] paths){
		ByteBuffer[] icons = new ByteBuffer[paths.length];
		int ct = 0;
		for(String path:paths){
			BufferedImage img = null;
			try {
			    img = ImageIO.read(Engine.class.getResourceAsStream(path));
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
	
	/**
	 * Set the window title
	 * @param title - the new title
	 */
	public static void setTitle(String title){
		Display.setTitle(LAUNCHER_TITLE+title);
	}
	
	/**
	 * public method to request the exit from anywhere,<br>
	 * <b style="color:red">Use with caution!</b>
	 */
	public static void requestExit(){
		closeRequested = true;
	}
	
	/**
	 * Returns all DisplayModes which are equal or smaller to the desktop resolution and with the same frequency and bit per pixel
	 * @return All modes in sorted list
	 */
	public static ArrayList<DisplayMode> getDisplayModes(){
		
		DisplayMode desktop = Display.getDesktopDisplayMode();
		ArrayList<DisplayMode> modes = new ArrayList<DisplayMode>(10);
		int width = desktop.getWidth(), height = desktop.getHeight(), hz = desktop.getFrequency(), bpp = desktop.getBitsPerPixel();
		
		DisplayMode[] allModes;
		try {
			allModes = Display.getAvailableDisplayModes();
		} catch (LWJGLException e) {
			Console.error("Failed to load DisplayModes");
			e.printStackTrace();
			return null;
		}

		for(DisplayMode mode:allModes){
			if(mode.getFrequency()==hz&&mode.getBitsPerPixel()==bpp){
				if(mode.getHeight()<=height&&mode.getWidth()<=width){
					modes.add(mode);
				}
			}
		}
		
		Collections.sort(modes, new Comparator<DisplayMode>(){
			public int compare(DisplayMode mode1, DisplayMode mode2){
				int width1 = mode1.getWidth(), width2 = mode2.getWidth();
				int height1 = mode1.getHeight(), height2 = mode2.getHeight();
				
				if(width1 > width2){
					return -1;
				}else if(width1 < width2){
					return 1;
				}
				
				if(height1 > height2){
					return -1;
				}else if(height1 < height2){
					return 1;
				}
				
				return 0;
			}
		});
		
		return modes;
	}
	
	/**
	 * Shows a message dialog and forcefully exits afterwards
	 * @param message - message to show
	 */
	public static void crashGracefully(String message){
		String additional = 
				"\r\n" +
				"\r\n" +
				"We're sorry, but please inform us of the previous error.";
		Sys.alert("Some gears totally broke with the following message:", message + additional);
		
		System.exit(666);
	}
	
}
