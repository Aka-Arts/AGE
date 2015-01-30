package org.akaarts.AGE;

import java.nio.ByteBuffer;

import org.akaarts.AGE.graphics.Color3f;
import org.akaarts.AGE.graphics.GraphicUtils;
import org.akaarts.AGE.input.KeyInputController;
import org.akaarts.AGE.input.MouseInputController;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Main class of AGE
 * 
 * Extend this class in your "Game" class<br>
 * 
 * Functions to override in your class:<br>
 * 
 * <dl>
 * <dt>init(String[] args)</dt>
 * <dd>gets called once before going into the loop(long delta)</dd>
 * <dt>loop(long delta)</dt>
 * <dd>gets called every frame</dd>
 * <dt>end()</dt>
 * <dd>gets called after leaving loop(long delta)</dd>
 * </dl>
 * 
 * @author Luca Egli
 * 
 */
public class Engine {

	// internal values
	private boolean exitRequested = false;
	private long lastFrameTime;

	// values with setter and getter
	private int settingWindowWidth = 1280;
	private int settingWindowHeight = 720;
	private int settingWindowLocationX = (Display.getDesktopDisplayMode()
			.getWidth() - settingWindowWidth) / 2;
	private int settingWindowLocationY = (Display.getDesktopDisplayMode()
			.getHeight() - settingWindowHeight) / 2;
	private int settingMaxFramesPerSecond = 120;
	private boolean settingWindowResizeable = false;
	private String settingWindowTitle = "AGE";
	private String[] settingAppIconPaths = new String[] {
			"/assets/AGE_128.png", "/assets/AGE_32.png", "/assets/AGE_16.png" };
	private Color3f settingCanvasInitialColor = new Color3f(0, 0, 0);
	private boolean settingCanvasVSyncEnabled = false;

	// constructors

	/**
	 * plain constructor
	 */
	public Engine() {
		
	}

	// public immutable functions

	/**
	 * Simple start method if you don't want to pass arguments
	 */
	public final void start() {
		
		this.start(null);
	
	}

	/**
	 * Start method if you want to pass arguments to init(...)
	 * 
	 * @param args
	 *            - arguments for the init(...) method
	 */
	public final void start(String[] args) {

		// internal init
		this.initInternal();

		// public init
		this.init(args);

		// main loop
		while (!exitRequested) {

			long delta = getDelta();

			// internal updates
			KeyInputController.pollAndBroadcast();
			MouseInputController.pollAndBroadcast();

			// public loop
			loop(delta);

			// GL-repaint and sync
			Display.update();
			Display.sync(settingMaxFramesPerSecond);

			// if windowExit was requested
			if (Display.isCloseRequested()) {
				
				exitRequested = true;
				
			}

		}

		// public end function
		this.end();

	}

	/**
	 * Requests the engine to exit the main loop
	 */
	public final void exit() {
		
		this.exitRequested = true;
		
	}

	// internal methods

	/**
	 * Internal initialization method
	 */
	private void initInternal() {

		// initialization of lastFrameTime
		lastFrameTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();

		// try to setup the display
		try {
			
			Display.setDisplayMode(new DisplayMode(settingWindowWidth,
					settingWindowHeight));
			
		} catch (LWJGLException e) {
			
			e.printStackTrace();
			Console.error("Failed to initialize display");
			System.exit(-1);
			
		}

		// apply window settings
		Display.setTitle(settingWindowTitle);
		Display.setResizable(settingWindowResizeable);
		Display.setIcon(loadIcons(settingAppIconPaths));
		Display.setLocation(settingWindowLocationX, settingWindowLocationY);
		Display.setInitialBackground(settingCanvasInitialColor.R,
				settingCanvasInitialColor.G, settingCanvasInitialColor.B);
		Display.setVSyncEnabled(settingCanvasVSyncEnabled);
		// TODO Display.setFullscreen();

		// try to create the Display
		try {
			
			Display.create();
			
		} catch (LWJGLException e) {
			
			e.printStackTrace();
			Console.error("Failed to create display");
			System.exit(-1);
			
		}
		
		KeyInputController.init();

		initInternalGL();

	}

	/**
	 * Internal GL initialization method
	 */
	private void initInternalGL() {

	}

	/**
	 * Internal helper method for loading icons from an array of path strings
	 * 
	 * @param paths
	 * @return icons as bytebuffers
	 */
	private ByteBuffer[] loadIcons(String[] paths) {

		ByteBuffer[] buffers = new ByteBuffer[paths.length];

		int counter = 0;
		for (String path : paths) {

			buffers[counter] = GraphicUtils.getByteBufferFromPNG(path);

			counter++;
			
		}

		return buffers;

	}

	/**
	 * Returns time delta in milliseconds
	 * 
	 * @return - delta since last frame in milliseconds
	 */
	private long getDelta() {

		long time = (Sys.getTime() * 1000) / Sys.getTimerResolution();

		long delta = time - lastFrameTime;

		lastFrameTime = time;

		return delta;

	}

	// public overrides

	/**
	 * override this method in your base class if you want to initialize some
	 * things before the main loop.<br>
	 * Gets internally called only once.<br>
	 * DON'T CALL THIS METHOD YOURSELF
	 * 
	 * @param args
	 *            - startup arguments, is null if none were passed to start().
	 */
	public void init(String[] args) {

	}

	/**
	 * the main loop for your update methods and logic
	 * 
	 * @param delta
	 *            - time since last loop was called in milliseconds
	 */
	public void loop(long delta) {

	}

	/**
	 * last method to be called by the engine before stopping
	 */
	public void end() {

	}

}
