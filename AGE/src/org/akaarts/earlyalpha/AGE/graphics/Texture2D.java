package org.akaarts.earlyalpha.AGE.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.text.html.MinimalHTMLWriter;

import org.akaarts.earlyalpha.AGE.Engine;
import org.akaarts.earlyalpha.AGE.CLI.Console;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

/**
 * AGE class for loading and holding textures. remember to destroy unused textures!
 * @author Luca Egli
 *
 */
public class Texture2D {
	
	private static int activeTextures = 0;
	
	public final int ID;
	
	public final boolean usesMipmap;

	private boolean isDestroyed = false;
	
	
	public static final String NOTEX = "/assets/defaults/NOTEX.png";

	private Texture2D(int width, int height, ByteBuffer data, int wrapU, int wrapV, int magFilter, int minFilter, boolean useMipmap) {
        // generate buffer on GPU
        this.ID = GL11.glGenTextures();
        // texture level 0
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        // bind call context
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, ID);
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
        this.usesMipmap = useMipmap;
        // set wrapping/clamping
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wrapU);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wrapV);
        
        Texture2D.activeTextures++;
                
	}
	
	/**
	 * basic method for texture loading. UV wrapping are GL_REPEAT, min/mag filters are GL_NEAREST and mipmapping is disabled
	 * @param path - the path to the file
	 * @return
	 */
	public static Texture2D loadTexture2d(String path) {
		return Texture2D.loadTexture2d(path, GL11.GL_REPEAT, GL11.GL_REPEAT, GL11.GL_NEAREST, GL11.GL_NEAREST, false);
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
	public static Texture2D loadTexture2d(String path, int wrapU, int wrapV, int magFilter, int minFilter, boolean useMipmaps) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(Engine.class.getResourceAsStream(path));
		} catch (Exception e) {
			Console.warning("Could not read file: "+path);
			try {
				img = ImageIO.read(Engine.class.getResourceAsStream(NOTEX));
			}catch(Exception e2) {
				Console.error("Could not load NOTEX...!?");
				e2.printStackTrace();
				return null;
			}
		}
		int[] pixels = new int[img.getWidth() * img.getHeight()];
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());

        ByteBuffer buffer = ByteBuffer.allocateDirect(img.getWidth() * img.getHeight() * 4); //4 for RGBA, 3 for RGB
        int a = 110;
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
        
        return new Texture2D(img.getWidth(), img.getHeight(), buffer, wrapU, wrapV, magFilter, minFilter, useMipmaps);
	}
	
	/**
	 * Advanced method for texture loading.
	 * @param img - image as BufferImage
	 * @param wrapU - GL wrapping constant
	 * @param wrapV - GL wrapping constant
	 * @param magFilter - GL magnification constant
	 * @param minFilter - GL minimizing constant
	 * @param useMipmaps - If to use mipmaps
	 * @return A texture2D object
	 */
	public static Texture2D loadTexture2d(BufferedImage img, int wrapU, int wrapV, int magFilter, int minFilter, boolean useMipmaps) {
		int[] pixels = new int[img.getWidth() * img.getHeight()];
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());

        ByteBuffer buffer = ByteBuffer.allocateDirect(img.getWidth() * img.getHeight() * 4); //4 for RGBA, 3 for RGB
        int a = 110;
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
        
        return new Texture2D(img.getWidth(), img.getHeight(), buffer, wrapU, wrapV, magFilter, minFilter, useMipmaps);
	}
	
	/**
	 * Disposing method
	 */
	public void destroy() {
		GL11.glDeleteTextures(ID);
		if(!this.isDestroyed) {
			Texture2D.activeTextures--;
		}
		this.isDestroyed = true;
		Console.info("Released a texture");
	}
	
	/**
	 * Method for checking object state
	 * @return True if already disposed
	 */
	public boolean isDestroyed() {
		return this.isDestroyed;
	}
	
	/**
	 * Returns a informational status-String
	 * @return - A string containing informations of the actual status
	 */
	public static String getStatus() {
		return Texture2D.activeTextures+" loaded Textures right now.";
	}

	public static void checkUndestroyed() {
		
		if(Texture2D.activeTextures > 0) {
			Console.error("There are " + Texture2D.activeTextures + " undetroyed textures! Please prevent memoryLeaks");
		}
		
	}
	
}
