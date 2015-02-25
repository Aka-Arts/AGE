package org.akaarts.AGE.graphics;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.akaarts.AGE.main.Console;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

public class Texture2d {
	
	public static final String NOTEX = "/assets/defaults/NOTEX.png";
	
	final int GLID;
	private boolean isDestroyed;
	final boolean useMipmap;
	
	final int wrapU;
	final int wrapV;
	final int magFilter;
	final int minFilter;
	
	final int width;
	final int height;

	private Texture2d(int width, int height, ByteBuffer data, int wrapU, int wrapV, int magFilter, int minFilter, boolean useMipmap) {
        
		// generate buffer on GPU
        this.GLID = GL11.glGenTextures();
        
        this.width = width;
        this.height = height;
        this.wrapU = wrapU;
        this.wrapV = wrapV;
        this.magFilter = magFilter;
        this.minFilter = minFilter;
        this.useMipmap = useMipmap;
        
        // texture level 0
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        
        // bind call context
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, GLID);
        
        // 
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        
        // load image to GPU memory
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);
        
        if(useMipmap) {
        	// generate mipmaps ...
        	GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        }
        
    	// set up filters
    	GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, magFilter);
    	GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, minFilter);
    	
        // set wrapping/clamping
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wrapU);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wrapV);
                        
	}
	
	/** Loads a Texture form an image file (preferably PNG) with the given parameters as setting.
	 * @param path - the path to the file
	 * @param wrapU - The behavior for horizontal wrapping
	 * @param wrapV - The behavior for vertical wrapping
	 * @param magFilter - the GL11 filter for magnifying (NEAREST|LINEAR)
	 * @param minFilter - the GL11 filter for minimizing (NEAREST|LINEAR|mipmap...)
	 * @param useMipmaps - true for using mipmaps
	 * @return a Texture2D object
	 */
	static Texture2d loadTexture2d(String path, int wrapU, int wrapV, int magFilter, int minFilter, boolean useMipmaps) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(Texture2d.class.getResourceAsStream(path));
		} catch (Exception e) {
			Console.warning("Could not read file: "+path);
			try {
				img = ImageIO.read(Texture2d.class.getResourceAsStream(NOTEX));
			}catch(Exception e2) {
				Console.error("Could not load NOTEX...!?");
				e2.printStackTrace();
				System.exit(-1);
			}
		}
		int[] pixels = new int[img.getWidth() * img.getHeight()];
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());

        ByteBuffer buffer = ByteBuffer.allocateDirect(img.getWidth() * img.getHeight() * 4); //4 for RGBA, 3 for RGB
        
        for(int y = 0; y < img.getHeight(); y++){
        	
            for(int x = 0; x < img.getWidth(); x++){
                int pixel = pixels[y * img.getWidth() + x];
                // red
                byte red = (byte) ((pixel >> 16) & 0xFF);
                buffer.put(red);
                // green
                byte green = (byte) ((pixel >> 8) & 0xFF);
                buffer.put(green);
                // blue
                byte blue = (byte) (pixel & 0xFF);
                buffer.put(blue);
                // alpha
                byte alpha = (byte) ((pixel >> 24) & 0xFF);
                buffer.put(alpha);

            }
        }

        buffer.flip();
        
        return new Texture2d(img.getWidth(), img.getHeight(), buffer, wrapU, wrapV, magFilter, minFilter, useMipmaps);
	}
	
	/**
	 * Disposing method
	 */
	void destroy() {
		GL11.glDeleteTextures(GLID);
		
		this.isDestroyed = true;
		Console.info("Released a texture");
	}
	
	/**
	 * Method for checking object state
	 * @return True if already disposed
	 */
	boolean isDestroyed() {
		return this.isDestroyed;
	}
	
}
