package org.akaarts.AGE;

import java.util.ArrayList;

import org.akaarts.AGE.CLI.CommandListener;
import org.akaarts.AGE.graphics.gui.ActiveElement;
import org.akaarts.AGE.graphics.gui.Hud;
import org.akaarts.AGE.graphics.gui.HudElement;
import org.lwjgl.opengl.DisplayMode;

public class LauncherCommands implements CommandListener {

	public static final LauncherCommands SELF = new LauncherCommands();
	
	private LauncherCommands() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean[] run(String func, String[] args) {
		boolean[] ret = new boolean[2];
		
		switch(func){
		case "exit":
			Engine.requestExit();
			break;
		case "menu":
			if(args.length<=0){
				Hud.loadPreset("HOME");
				break;
			}
			switch(args[0]){
			case "settings":
				Hud.loadPreset("SETTINGS");
				ActiveElement elem = new ActiveElement(HudElement.getElement("BUTTON_BIG"));
				elem.setPos(150, 50);
				elem.setElementAlign("CENTER", "TOP");
				elem.setText("Back");
				elem.setCommand("menu");
				Hud.addElement(elem);
				break;
			case "resolutions":
				Hud.loadPreset("RESOLUTIONS");
				ActiveElement elem2 = new ActiveElement(HudElement.getElement("BUTTON_BIG"));
				elem2.setPos(150, 50);
				elem2.setElementAlign("CENTER", "TOP");
				elem2.setText("Back");
				elem2.setCommand("menu settings");
				Hud.addElement(elem2);
				ArrayList<DisplayMode> modes = Engine.getDisplayModes();
				int yOffset = 120;
				int index = 0;
				for(DisplayMode mode:modes){
					ActiveElement modeButton = new ActiveElement(HudElement.getElement("BUTTON_BIG"));
					modeButton.setPos(150, yOffset);
					modeButton.setElementAlign("CENTER", "TOP");
					modeButton.setText(mode.getWidth()+"x"+mode.getHeight());
					modeButton.setCommand("resolution "+index);
					Hud.addElement(modeButton);
					yOffset += 70;
					index++;
				}
				break;
			case "home":
			default:
				Hud.loadPreset("HOME");
				break;
			}
			break;
		default:
			ret[0] = false;
			ret[1] = false;
			return ret;
		}
		ret[1]=true;
		return ret;
	}

	@Override
	public String[] getCommands() {
		return new String[]{"exit"};
	}

}
