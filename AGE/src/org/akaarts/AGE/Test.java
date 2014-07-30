package org.akaarts.AGE;

import org.akaarts.AGE.collision2d.Point2d;
import org.akaarts.AGE.collision2d.Ray2d;
import org.akaarts.AGE.collision2d.Shape2d;
import org.akaarts.AGE.collision2d.Vector2d;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Ray2d ray = new Ray2d(new Point2d(0,0f),new Vector2d(-1,0));
		Shape2d shape = new Shape2d(1.000001f,1.1f);
		shape.addPoint(new Point2d(1,1));
		shape.addPoint(new Point2d(1,-1));
		shape.addPoint(new Point2d(-1,-1));
		shape.addPoint(new Point2d(-1,1));
		
		Console.info("is in shape: "+ray.isInShape(shape));
		
		/*test more more*/
		
		Engine engine = new Engine();
		engine.start();
		engine.stop();
		engine.start();
		engine.stop();

	}

}
