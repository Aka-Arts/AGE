package org.akaarts.AGE.graphics;

public class Texture {
	
	public final int GLID;
	public final String name;
	private boolean isDestroyed;

	public Texture(String path, String name, int minFilter, int magFilter){
		
		this.GLID = 0; // TODO
		this.name = name;
		
	}
	
}
