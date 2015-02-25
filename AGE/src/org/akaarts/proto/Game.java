package org.akaarts.proto;

import org.akaarts.AGE.gui.View;
import org.akaarts.AGE.gui.ViewElement;
import org.akaarts.AGE.gui.ViewManager;
import org.akaarts.AGE.input.KeyEvent;
import org.akaarts.AGE.input.MouseEvent;
import org.akaarts.AGE.input.MouseEventListener;
import org.akaarts.AGE.input.MouseInputController;
import org.akaarts.AGE.main.Console;
import org.akaarts.AGE.main.Engine;
import org.lwjgl.input.Mouse;

public class Game extends Engine{

	
	
	@Override
	public void init(String[] args){
		
		View view = new View() {
		};
		
		view.ROOT.addChild(new ViewElement() {
			
			@Override
			public boolean onHover(MouseEvent e) {
				
				Console.info("hi!");
				
				return true;
			}
			
			@Override
			public boolean onClick(MouseEvent e) {

				Console.info("clicked!");
				
				return true;
			}

			@Override
			public boolean onWheel(MouseEvent e) {
				
				Console.info("wheeeeel!");
				
				return true;
			}
		});
		
		ViewManager.INSTANCE.setView(view);
		
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
