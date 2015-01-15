package org.akaarts.AGE.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.akaarts.earlyalpha.AGE.Engine;
import org.lwjgl.BufferUtils;

public class GraphicUtils {

	/**
	 * Loads a PNG from a path into a ByteBuffer
	 * 
	 * @param path
	 *            The path to the PNG
	 * @return ByteBuffer, representing the PNG or null if the PNG could not be
	 *         loaded
	 */
	public static ByteBuffer getByteBufferFromPNG(String path) {

		BufferedImage img = null;
		try {
			img = ImageIO.read(Engine.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		int[] pixels = new int[img.getWidth() * img.getHeight()];
		img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0,
				img.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(img.getWidth()
				* img.getHeight() * 4); // 4 for RGBA

		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				int pixel = pixels[y * img.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF)); // Green component
				buffer.put((byte) (pixel & 0xFF)); // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component.
			}
		}

		buffer.flip();

		return buffer;

	}

}
