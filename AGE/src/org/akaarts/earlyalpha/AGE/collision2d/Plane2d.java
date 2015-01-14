package org.akaarts.earlyalpha.AGE.collision2d;

public class Plane2d {
	
	public Point2d origin;
	public Vector2d normal;
	
	public Plane2d(Point2d origin, Vector2d normal){
		this.origin = origin;
		
		if(normal.length() == 0){
			this.normal = new Vector2d(1,0);
		}else{
			this.normal = normal.getNormalized();
		}
	}
	
}
