package org.akaarts.AGE.graphics.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.akaarts.AGE.CLI.Console;
import org.akaarts.AGE.graphics.Color4f;
import org.akaarts.AGE.graphics.Texture2D;
import org.akaarts.AGE.graphics.text.FontManager;
import org.akaarts.AGE.graphics.text.FontMap;
import org.lwjgl.opengl.GL11;

public class TextElement {

	private FontMap font;
	private int width, height, x, y,texDimension;
	private String text;
	private int style;
	private int size;
	private Color4f color;

	public static final String STDFONT = "/assets/defaults/font.ttf";
	public static final int STDSIZE = 32;

	public TextElement(int x, int y, int width, int height) {
		this.setDimensions(width, height);
		this.x = x;
		this.y = y;
		this.text = "";
		this.font = FontManager.getFont("DEFAULT");
		this.size = STDSIZE;
		this.style = Font.PLAIN;
		this.color = new Color4f(1,1,1,1);

	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
		int max = Math.max(width, height);
		int dimension = 64;
		while(max > dimension) {
			dimension*=2;
			if(dimension>=1024) {
				break;
			}
		}
		this.texDimension = dimension;
		
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setSytle(int style) {
		this.style = style;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	public void setFont(FontMap font) {
		this.font = font;
	}

//	public void update() {
//		if(this.texture!=null) {
//			this.texture.destroy();
//		}
//		if(this.text == null ||this.text.isEmpty()) {
//			return;
//		}
//		Font tmpFont = font.deriveFont(this.style,this.size);		
//		BufferedImage img = new BufferedImage(this.texDimension, this.texDimension, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D g = (Graphics2D)img.getGraphics();
//		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
//		FontMetrics metrics = g.getFontMetrics(tmpFont);
//		g.setColor(Color.WHITE);
//		g.setFont(tmpFont);
//		g.drawString(text, 0, metrics.getMaxAscent());
//		
//		this.texture = Texture2D.loadTexture2d(img, GL11.GL_REPEAT, GL11.GL_REPEAT, GL11.GL_LINEAR, GL11.GL_LINEAR, false);
//	}
	
	public void draw(float parentAlpha) {
		
		if(this.text == null || this.text.isEmpty()) {
			return;
			// nothing to draw here!
		}
		
		this.font.drawString(this.x, this.y, this.text, this.size, this.color);
	}

}
