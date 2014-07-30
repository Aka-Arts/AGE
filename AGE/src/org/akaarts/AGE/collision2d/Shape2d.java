package org.akaarts.AGE.collision2d;

import java.util.ArrayList;

/**
 * Class for 2D n-gon shapes
 * @author Luca Egli
 *
 */
public class Shape2d {
	public ArrayList<Point2d> points = new ArrayList<Point2d>(5);
	public Point2d origin;
	
	/**
	 * Creates a new empty shape with the origin at x/y
	 * @param x
	 * @param y
	 */
	public Shape2d(float x, float y ){
		this.origin = new Point2d(x,y);
	}
	/**
	 * Creates a new empty shape with the origin at the given Point2d
	 * @param origin - The origin as Point2d
	 */
	public Shape2d(Point2d origin){
		this.origin = origin;
	}
	
	public boolean addPoint(Point2d point){
		return this.points.add(point);
	}
	
	public Point2d getPoint(int index){
		return this.points.get(index).add(new Vector2d(this.origin.x,this.origin.y));
	}
}
