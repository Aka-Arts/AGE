package org.akaarts.AGE.graphics;

/**
 * A class, representing a color made from 3 floats.<br>
 * Each color should be in range from 0.0 to 1.0
 * @author Luca Egli
 *
 */
public class Color3f {

	public float R;
	public float G;
	public float B;

	/**
	 * Constructor for a new color object
	 * @param R - Red value (0.0 to 1.0)
	 * @param G - Green value (0.0 to 1.0)
	 * @param B - Blue value (0.0 to 1.0)
	 */
	public Color3f(float R, float G, float B) {

		this.R = R;
		this.G = G;
		this.B = B;

	}

}
