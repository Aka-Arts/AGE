package org.akaarts.AGE.graphics.text;

import java.awt.FontMetrics;

import org.akaarts.AGE.utils.UVMap4;

public class Glyph {
	
	public final int WIDTH, HEIGHT;
	
	public final UVMap4 UV;
	
	public final String CHAR;
	
	public Glyph(String character, FontMetrics metrics, int mapX, int mapY){
		
		this.WIDTH = metrics.charWidth(character.charAt(0));
		this.HEIGHT= metrics.getHeight();
		
		this.CHAR = character;
		
		int u1, v1, u2, v2, u3, v3, u4, v4;
		
		u1 = u4 = mapX * FontMap.CHARSIZE + (FontMap.CHARSIZE-this.WIDTH)/2;
		v1 = v2 = mapY * FontMap.CHARSIZE;
		u2 = u3 = mapX * FontMap.CHARSIZE + this.WIDTH + (FontMap.CHARSIZE-this.WIDTH)/2;
		v3 = v4 = mapY * FontMap.CHARSIZE + FontMap.CHARSIZE;
		
		this.UV = new UVMap4(
				u1/(float)FontMap.DIMENSION, 
				v1/(float)FontMap.DIMENSION, 
				u2/(float)FontMap.DIMENSION,
				v2/(float)FontMap.DIMENSION,
				u3/(float)FontMap.DIMENSION, 
				v3/(float)FontMap.DIMENSION, 
				u4/(float)FontMap.DIMENSION, 
				v4/(float)FontMap.DIMENSION
				);
		
	}

}
