package org.akaarts.AGE.graphics;

import java.util.HashMap;

public class TextureManager {
	
	private static HashMap<String, Texture2d> textures = new HashMap<String, Texture2d>();
	
	public static int getTexture(String path, int wrapU, int wrapV, int magFilter, int minFilter, boolean useMipmaps){
		
		if(textures.containsKey(path)){
			
			// if already loaded
			
			Texture2d texture = textures.get(path);
			
			if(texture.isDestroyed()){
				
				// reload
				
				texture = Texture2d.loadTexture2d(path, wrapU, wrapV, magFilter, minFilter, useMipmaps);
				
				textures.put(path, texture);
				
				return texture.GLID;
				
			}else{
				
				return texture.GLID;
				
			}
			
		}else{
			
			// not yet loaded
			
			Texture2d texture = Texture2d.loadTexture2d(path, wrapU, wrapV, magFilter, minFilter, useMipmaps);
						
			textures.put(path, texture);
			
			return texture.GLID;
			
		}
				
	}

}
