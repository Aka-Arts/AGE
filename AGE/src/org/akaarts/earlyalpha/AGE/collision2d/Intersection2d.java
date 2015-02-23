package org.akaarts.earlyalpha.AGE.collision2d;

import java.util.ArrayList;

/**
 * A object representing an intersection of a ray and a intersectable Object
 * @author Luca Egli
 *
 */
public class Intersection2d {
	
	public float distance = -1;
	public ArrayList<Point2d> intersectionPoints = new ArrayList<Point2d>();
	public int times = 0;
	
	/**
	 * Creates a new empty Intersection object.
	 */
	public Intersection2d(){
	}
	
	public void registerHit(Point2d point){
		this.intersectionPoints.add(point);
	}
	
	public String toString(){
		return "Times: "+this.times+" / Distance: "+this.distance;
	}
	
}
