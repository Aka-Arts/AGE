package org.akaarts.AGE.main;

public interface Phase {
	
	public void begin(String[] args);
	
	public void loop(long delta);
	
	public void end();

}
