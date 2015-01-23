package org.akaarts.proto;

import org.akaarts.AGE.Console;
import org.akaarts.AGE.Engine;
import org.akaarts.AGE.input.MouseEvent;
import org.akaarts.AGE.input.MouseEventListener;
import org.akaarts.AGE.input.MouseInputController;

public class Game extends Engine{

	
	
	@Override
	public void init(String[] args){
		
		MouseInputController.addListener(new MouseEventListener() {
			
			@Override
			public void onMouseEvent(MouseEvent e) {

				Console.info(e.x + "|" + e.y);

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
