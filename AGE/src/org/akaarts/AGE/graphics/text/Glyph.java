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
		
		// TODO set uvmap
		int u1, v1, u2, v2, u3, v3, u4, v4;
		
		u1 = u4 = mapX * 32 + (32-this.WIDTH)/2;
		v1 = v2 = mapY * 32;
		u2 = u3 = mapX * 32 + this.WIDTH + (32-this.WIDTH)/2;
		v3 = v4 = mapY * 32 + 32;
		
		this.UV = new UVMap4(u1/512f, v1/512f, u2/512f, v2/512f, u3/512f, v3/512f, u4/512f, v4/512f);
		
	}

}
