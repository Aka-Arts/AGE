package org.akaarts.AGE.graphics.text;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.akaarts.AGE.graphics.Texture2D;
import org.lwjgl.opengl.GL11;

public class FontMap {
	
	private Texture2D texture;
	
	private String[] chars = new String[256];
	
	public FontMap(Font font) {
		
		
		Font tmpFont = font.deriveFont(Font.PLAIN,60);		
		BufferedImage img = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		FontMetrics metrics = g.getFontMetrics(tmpFont);
		g.setColor(Color.WHITE);
		g.setFont(tmpFont);
		
		//char map
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
		chars[80] = ">";	chars[81] = "+";	chars[82] = "*";	chars[83] = "�";
		chars[84] = "%";	chars[85] = "&";	chars[86] = "/";	chars[87] = "\\";
		chars[88] = "=";	chars[89] = "^";	chars[90] = "�";	chars[91] = "@";
		chars[92] = "#";	chars[93] = "�";	chars[94] = "�";	chars[95] = "�";
		chars[96] = "|";	chars[97] = "�";	chars[98] = "�";	chars[99] = "�";
		chars[100] = "�";	chars[101] = "�";	chars[102] = "�";	chars[103] = "�";
		chars[104] = "�";	chars[105] = "�";	chars[106] = "�";	chars[107] = "�";
		chars[108] = "�";	chars[109] = "�";	chars[110] = "�";	chars[111] = "�";
		chars[112] = "�";	chars[113] = "�";	chars[114] = "�";	chars[115] = "`";
		chars[116] = "�";	chars[117] = "�";	chars[118] = "�";	chars[119] = "�";
		chars[120] = "�";	chars[121] = "�";	chars[122] = "�";	chars[123] = "�";
		chars[124] = "�";	chars[125] = "�";	chars[126] = "�";	chars[127] = "�";
		chars[128] = "�";	chars[129] = "�";	chars[130] = "�";	chars[131] = "�";
		
		for(int i = 0; i < 16 ; i++) {
			for(int j = 0; j < 16 ; j++) {
				
				int index = i * 16 + j;
				
				if(chars[index]!=null&&!chars[index].equals("")) {
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
