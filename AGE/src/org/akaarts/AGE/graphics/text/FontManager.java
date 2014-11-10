package org.akaarts.AGE.graphics.text;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;

public class FontManager {
	
	private HashMap<String,FontMap> fonts = new HashMap<String, FontMap>();
	
	public static final String STDFONT = "/assets/defaults/font.ttf";	
	
	public static final FontManager SELF = new FontManager();
	
	private FontManager() {
		
		try {
			this.fonts.put("DEFAULT", new FontMap(Font.createFont(Font.PLAIN, FontManager.class.getResourceAsStream(STDFONT)),true));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void destroy() {
		for(FontMap font: SELF.fonts.values()) {
			font.destroy();
		}
	}
	

}
