package org.akaarts.earlyalpha.AGE.graphics.text;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;

import org.akaarts.earlyalpha.AGE.CLI.Console;

/**
 * A class for managing fonts for AGE
 * @author Luca Egli
 *
 */
public class FontManager {
	
	private HashMap<String,FontMap> fonts = new HashMap<String, FontMap>();
	
	public static final String STDFONT = "/assets/defaults/font.ttf";	
	
	private static final FontManager SELF = new FontManager();
	
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
	
	/**
	 * Method for adding and initializing a new Font with AA
	 * @param name - The Name for the new font
	 * @param font - The Font Object to add
	 */
	public static void addFont(String name, Font font){
		addFont(name, font, true);
	}
	
	/**
	 * Method for adding and initializing a new Font
	 * @param name - The Name for the new font
	 * @param font - The Font Object to add
	 * @param useAA - Use Anti Aliasing?
	 */
	public static void addFont(String name, Font font, boolean useAA){
		SELF.fonts.put(name, new FontMap(font,useAA));
	}
	
	/**
	 * Method for removing a current active font from the Manager
	 * @param name - The name of the font to remove
	 */
	public static void destroyFont(String name){
		SELF.fonts.get(name).destroy();
		SELF.fonts.remove(name);
	}
	
	/**
	 * Returns a FontMap of  the named font or the default if no font by this name was found
	 * @param name - name of the font
	 * @return the named or the default font
	 */
	public static FontMap getFont(String name){
		
		FontMap font = SELF.fonts.get(name);
		
		if(font == null){
			font = SELF.fonts.get("DEFAULT");
		}
		
		return font;
	}
	
	/**
	 * Clean up method for destroying all fonts
	 */
	public static void destroy() {
		for(FontMap font: SELF.fonts.values()) {
			font.destroy();
		}
		SELF.fonts.clear();
		Console.info("FontManager destroyed");
	}
	

}
