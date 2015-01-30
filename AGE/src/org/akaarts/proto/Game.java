package org.akaarts.proto;

import org.akaarts.AGE.Console;
import org.akaarts.AGE.Engine;
import org.akaarts.AGE.gui.Gui;
import org.akaarts.AGE.gui.GuiElement;
import org.akaarts.AGE.input.KeyEvent;
import org.akaarts.AGE.input.MouseEvent;

public class Game extends Engine{

	
	
	@Override
	public void init(String[] args){
		
		Gui.SINGLETON.ROOT.addChild(new GuiElement() {
		
			@Override
			public boolean onKey(KeyEvent e) {
				
				Console.info("Input received: " + e.toString());
				
				return false;
			}

			@Override
			public boolean onClick(MouseEvent e) {
				
				Console.info("Input received: " + e.toString());
				
				return false;
			}

			@Override
			public boolean onHover(MouseEvent e) {
				
				Console.info("Input received: " + e.toString());
				
				return false;
			}
		
		});
		
	}
	
	@Override
	public void loop(long delta) {

		
		
	}
	
	@Override
	public void end() {
		
		
		
	}
	
	/**
	 * JVM start method
	 * @param args
	 */
	public static void main(String[] args) {
		
		Game game = new Game();
		
		game.start(args);
		
	}
	
}
