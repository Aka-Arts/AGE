package org.akaarts.AGE.graphics.text;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

import org.akaarts.AGE.graphics.Color4f;
import org.akaarts.AGE.graphics.Texture2D;
import org.lwjgl.opengl.GL11;

public class FontMap {
	
	private Texture2D texture;
	
	private LinkedHashMap<String,Glyph> chars = new LinkedHashMap<String,Glyph>();
	
	private final int DIMENSION = 512;
	
	public boolean usesAA = false;
	
	// TODO javadoc !
	
	public FontMap(Font font, boolean useAA) {
		
		initChars();
		
		BufferedImage img = new BufferedImage(DIMENSION, DIMENSION, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)img.getGraphics();
		if(useAA){
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		}
		
		usesAA = useAA;
		
		int fontSizePx;
		
		int fontSizePt = 35;
		
		Font tmpFont;
		FontMetrics metrics;
		
		do {
			tmpFont = font.deriveFont(Font.PLAIN,fontSizePt);
			
			metrics = g.getFontMetrics(tmpFont);
			
			fontSizePx = metrics.getHeight();
			
			fontSizePt--;
			
		}while(fontSizePx > 32);
		

		g.setColor(java.awt.Color.WHITE);
		g.setFont(tmpFont);
		
		int i = 0, j = 0; 
		
		for(String key : chars.keySet()) {
			
			if(j >= 16){
				j = 0;
				i++;
			}
			
			if(key!=null) {
				
				Glyph glyph = new Glyph(key, metrics, j, i);
				
				g.drawString(key, j*32+((32-glyph.WIDTH)/2), i*32 + metrics.getMaxAscent()+metrics.getLeading());
				
				chars.put(key, glyph);
				
			}
			
			j++;
		}
		
		this.texture = Texture2D.loadTexture2d(img, GL11.GL_REPEAT, GL11.GL_REPEAT, GL11.GL_NEAREST, GL11.GL_NEAREST, false);
		
	}
	
	public void draw(){
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture.ID);
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor4f(1f,1f,0f,1f);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2i(0, 0);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2i(DIMENSION, 0);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2i(DIMENSION, DIMENSION);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2i(0, DIMENSION);
		GL11.glEnd();
	}
	
	public void drawChar(int x, int y ,String character, int fontSize, Color4f color){
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture.ID);
		
		Glyph glyph = chars.get(character);
		
		int fontWidth =  (int) (fontSize*(glyph.WIDTH/32f));
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor4f(color.R,color.G,color.B,color.A);
			GL11.glTexCoord2f(glyph.UV.U[0], glyph.UV.V[0]);
			GL11.glVertex2i(x, y);
			GL11.glTexCoord2f(glyph.UV.U[1], glyph.UV.V[1]);
			GL11.glVertex2i(x+fontWidth, y);
			GL11.glTexCoord2f(glyph.UV.U[2], glyph.UV.V[2]);
			GL11.glVertex2i(x+fontWidth, y+fontSize);
			GL11.glTexCoord2f(glyph.UV.U[3], glyph.UV.V[3]);
			GL11.glVertex2i(x, y+fontSize);
		GL11.glEnd();
		
	}
	
	public void destroy() {
		this.texture.destroy();
	}
	
	private void initChars(){
		
		if(!chars.isEmpty()) {
			return;
		}
		
		String[] chars = new String[256];
		
		// CharMap
		// TODO extend
		
		chars[0] = "A";		chars[1] = "B";		chars[2] = "C";		chars[3] = "D";
		chars[4] = "E";		chars[5] = "F";		chars[6] = "G";		chars[7] = "H";
		chars[8] = "I";		chars[9] = "J";		chars[10] = "K";	chars[11] = "L";
		chars[12] = "M";	chars[13] = "N";	chars[14] = "O";	chars[15] = "P";
		chars[16] = "Q";	chars[17] = "R";	chars[18] = "S";	chars[19] = "T";
		chars[20] = "U";	chars[21] = "V";	chars[22] = "W";	chars[23] = "X";
		chars[24] = "Y";	chars[25] = "Z";	chars[26] = "a";	chars[27] = "b";
		chars[28] = "c";	chars[29] = "d";	chars[30] = "e";	chars[31] = "f";
		chars[32] = "g";	chars[33] = "h";	chars[34] = "i";	chars[35] = "j";
		chars[36] = "k";	chars[37] = "l";	chars[38] = "m";	chars[39] = "n";
		chars[40] = "o";	chars[41] = "p";	chars[42] = "q";	chars[43] = "r";
		chars[44] = "s";	chars[45] = "t";	chars[46] = "u";	chars[47] = "v";
		chars[48] = "w";	chars[49] = "x";	chars[50] = "y";	chars[51] = "z";
		chars[52] = " ";	chars[53] = "0";	chars[54] = "1";	chars[55] = "2";
		chars[56] = "3";	chars[57] = "4";	chars[58] = "5";	chars[59] = "6";
		chars[60] = "7";	chars[61] = "8";	chars[62] = "9";	chars[63] = "-";
		chars[64] = "_";	chars[65] = ".";	chars[66] = ",";	chars[67] = ":";
		chars[68] = ";";	chars[69] = "'";	chars[70] = "\"";	chars[71] = "?";
		chars[72] = "!";	chars[73] = "[";	chars[74] = "]";	chars[75] = "{";
		chars[76] = "}";	chars[77] = "(";	chars[78] = ")";	chars[79] = "<";
		chars[80] = ">";	chars[81] = "+";	chars[82] = "*";	chars[83] = "ç";
		chars[84] = "%";	chars[85] = "&";	chars[86] = "/";	chars[87] = "\\";
		chars[88] = "=";	chars[89] = "^";	chars[90] = "¦";	chars[91] = "@";
		chars[92] = "#";	chars[93] = "°";	chars[94] = "§";	chars[95] = "¬";
		chars[96] = "|";	chars[97] = "¢";	chars[98] = "€";	chars[99] = "ä";
		chars[100] = "ö";	chars[101] = "ü";	chars[102] = "é";	chars[103] = "è";
		chars[104] = "ê";	chars[105] = "á";	chars[106] = "à";	chars[107] = "â";
		chars[108] = "ú";	chars[109] = "ù";	chars[110] = "û";	chars[111] = "ó";
		chars[112] = "ò";	chars[113] = "ô";	chars[114] = "´";	chars[115] = "`";
		chars[116] = "¨";	chars[117] = "Ä";	chars[118] = "Ö";	chars[119] = "Ü";
		chars[120] = "É";	chars[121] = "È";	chars[122] = "Ê";	chars[123] = "Á";
		chars[124] = "À";	chars[125] = "Â";	chars[126] = "Ú";	chars[127] = "Ù";
		chars[128] = "Û";	chars[129] = "Ó";	chars[130] = "Ò";	chars[131] = "Ô";
		
		for(String chr : chars){
			if(chr!=null){
				this.chars.put(chr,null);
			}
		}
		
	}
	
}
