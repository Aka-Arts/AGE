package org.akaarts.AGE;

import org.lwjgl.Sys;

public class Engine {
	
	public Engine() {
		
	}
	
	private boolean exitRequested = false;
	
	private long lastFrameTime;
	
	
	/**
	 * Simple start method if you don't want to pass arguments
	 */
	public final void start() {
		this.start(null);
	}
	
	/**
	 * Start method if you want to pass arguments to init(...)
	 * @param args - arguments for the init(...) method
	 */
	public final void start(String[] args) {
		
		this.init(args);
		
		// init of lastFrameTime
		lastFrameTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		
		while(!exitRequested) {
			
			long delta = getDelta();
			
			loop(delta);
		
		}
		
		this.end();
		
	}
	
	public final void exit() {
		this.exitRequested = true;
	}
	
	// internal methods
	
	private long getDelta() {
		
		long time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
		
		long delta = time - lastFrameTime;
		
		lastFrameTime = time;
		
		return delta;
		
	}
	
	
	// public overrides
	
	/**
	 * override this method in your base class if you want to initialize some things before the main loop.<br>
	 * Gets internally called only once.<br>
	 * DON'T CALL THIS METHOD YOURSELF
	 * @param args - startup arguments, is null if none were passed to start().
	 */
	public void init(String[] args) {
		
	}
	
	/**
	 * the main loop for your update methods and logic
	 * @param delta - time since last loop was called in milliseconds
	 */
	public void loop(long delta) {
		
	}
	
	/**
	 * last method to be called by the engine before stopping
	 */
	public void end() {
		
	}
	
}
