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
import org.akaarts.AGE.graphics.Texture2D;
import org.lwjgl.opengl.GL11;

public class TextElement {

	private Font font;
	private int width, height, x, y,texDimension;
	private Texture2D texture;
	private String text;
	private int style;
	private int size;

	public static final String STDFONT = "/assets/defaults/font.ttf";
	public static final int STDSIZE = 32;

	public TextElement(int x, int y, int width, int height) {
		this.setDimensions(width, height);
		this.x = x;
		this.y = y;
		this.text = "";
		try {
			this.font = Font.createFont(Font.TRUETYPE_FONT, Font.class.getResourceAsStream(STDFONT));
		} catch (FontFormatException | IOException e2) {
			Console.error("could not load default font file...!?");
			e2.printStackTrace();
		}
		this.size = 12;
		this.style = Font.PLAIN;

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

	public void setFont(String path) {
		try {
			this.font = Font.createFont(Font.TRUETYPE_FONT, Font.class.getResourceAsStream(path));
		}catch(FontFormatException | IOException e) {
			Console.warning("Could not load font: "+path);
			try {
				this.font = Font.createFont(Font.TRUETYPE_FONT, Font.class.getResourceAsStream(STDFONT));
			} catch (FontFormatException | IOException e2) {
				Console.error("could not load default font file...!?");
				e2.printStackTrace();
				return;
			}
		}
	}

	public void update() {
		if(this.texture!=null) {
			this.texture.destroy();
		}
		if(this.text == null ||this.text.isEmpty()) {
			return;
		}
		Font tmpFont = font.deriveFont(this.style,this.size);		
		BufferedImage img = new BufferedImage(this.texDimension, this.texDimension, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		FontMetrics metrics = g.getFontMetrics(tmpFont);
		g.setColor(Color.WHITE);
		g.setFont(tmpFont);
		g.drawString(text, 0, metrics.getMaxAscent());
		
		this.texture = Texture2D.loadTexture2d(img, GL11.GL_REPEAT, GL11.GL_REPEAT, GL11.GL_LINEAR, GL11.GL_LINEAR, false);
	}
	
	public void draw(float parentAlpha) {
		
		if(this.texture == null) {
			return;
			// nothing to draw here!
		}
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,this.texture.ID);
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor4f(1,1,1,(1/255f)*parentAlpha);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2i(this.x, this.y);
			GL11.glTexCoord2f((1/(float)this.texDimension)*this.width, 0);
			GL11.glVertex2i(this.x+this.width, this.y);
			GL11.glTexCoord2f((1/(float)this.texDimension)*this.width, (1/(float)this.texDimension)*this.height);
			GL11.glVertex2i(this.x+this.width, this.y+this.height);
			GL11.glTexCoord2f(0, (1/(float)this.texDimension)*this.height);
			GL11.glVertex2i(this.x, this.y+this.height);
		GL11.glEnd();
	}
	
	public void destroy() {
		this.texture.destroy();
	}

}
