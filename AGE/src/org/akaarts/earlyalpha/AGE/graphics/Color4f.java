package org.akaarts.earlyalpha.AGE.graphics;

/**
 * A class representing a 4 component color as floats (0 to 1)
 * @author luca.egli
 *
 */
public class Color4f {
	
	/**
	 * Color component.<br>Preferable range is 0.0f to 1.0f
	 */
	public float R, G, B, A;
	
	/**
	 * Instantiates a new Color object with the given values
	 * @param R - Red
	 * @param G - Green
	 * @param B - Blue
	 * @param A - Alpha
	 */
	public Color4f(float R, float G, float B, float A) {
		this.R = R;
		this.G = G;
		this.B = B;
		this.A = A;
	}
}
