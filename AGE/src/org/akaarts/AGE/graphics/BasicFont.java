package org.akaarts.AGE.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

public class BasicFont {
	
	public Texture2D texture;
	
	public BasicFont(Font font) {
		
		
		Font tmpFont = font.deriveFont(Font.PLAIN,60);		
		BufferedImage img = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		FontMetrics metrics = g.getFontMetrics(tmpFont);
		g.setColor(Color.WHITE);
		g.setFont(tmpFont);
		
		String[] chars = new String[256];
		
		chars[0] = "A";
		chars[1] = "B";
		chars[2] = "C";
		chars[3] = "D";
		chars[4] = "E";
		chars[5] = "F";
		chars[6] = "G";
		chars[7] = "H";
		chars[8] = "I";
		chars[9] = "J";
		chars[10] = "K";
		chars[11] = "L";
		chars[12] = "M";
		chars[13] = "N";
		chars[14] = "O";
		chars[15] = "P";
		chars[16] = "Q";
		chars[17] = "R";
		chars[18] = "S";
		chars[19] = "T";
		chars[20] = "U";
		chars[21] = "V";
		chars[22] = "W";
		chars[23] = "X";
		chars[24] = "Y";
		chars[25] = "Z";
		
		for(int i = 0; i < 16 ; i++) {
			for(int j = 0; j < 16 ; j++) {
				
				int index = i * 16 + j;
				
				if(chars[index]!=null) {
					int charWidth = metrics.charWidth(chars[index].charAt(0));
					
					g.drawString(chars[index], j*64 + ((64-charWidth)/2), i*64 + metrics.getMaxAscent());
				}
			}
		}
		
		this.texture = Texture2D.loadTexture2d(img, GL11.GL_REPEAT, GL11.GL_REPEAT, GL11.GL_LINEAR, GL11.GL_LINEAR, false);
		
	}
	
	public void destroy() {
		this.texture.destroy();
	}
	
}
