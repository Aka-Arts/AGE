package org.akaarts.AGE.collision2d;

import org.akaarts.AGE.CLI.Console;

/**
 * Class for 2D ray intersection tests.
 * @author Luca Egli
 *
 */
public class Ray2d {
	
	public Vector2d direction;
	public Point2d origin;
	
	/**
	 * Creates a new 2D ray for future testing.
	 * @param startPoint - The origin of the ray
	 * @param direction - The direction of the ray (gets automatically normalized)
	 */
	public Ray2d(Point2d origin, Vector2d direction){
		this.origin = origin;
		this.direction = direction.getNormalized();
	}
	
	/**
	 * Tests if this Ray intersects with the given plane
	 * @param plane - The plan to test against
	 * @return the distance to the plane from the Ray.origin or -1 if no hit occurred. 
	 */
	public float intersectsPlane(Plane2d plane){
		float l = 0;
		if(this.origin == plane.origin){
			return 0;
		}
		Vector2d oriToOri = plane.origin.subtract(this.origin);
		float oToDotN = oriToOri.dotWith(plane.normal);
		float dirDotN = this.direction.dotWith(plane.normal);
		
		if(Math.abs(dirDotN) < 0.00000001){
			return -1;
		}
		
		l = oToDotN/dirDotN;
		if(l < 0.0){
			return -1;
		}
		
		return Math.abs(l);
		
	}
	
	/**
	 * Tests if this Ray intersects with the given line
	 * @param line - The line to test against
	 * @return The distance to the line from the Ray.origin or -1 if no hit occurred.
	 */
	public float intersectsLine(Line2d line){

		float l = this.intersectsPlane(new Plane2d(line.origin,line.getNormal()));
		

		
		if(l == -1){
			return l;
		}
		if(l > 0){
			
			Point2d intersection = this.getPointAt(l);
			
			if(line.origin.getDistTo(intersection)>line.halfWidth.length()){
				
				return -1;
			}
		}
		return l;
		
	}
	
	public Intersection2d intersectsShape(){
		Intersection2d intersection = new Intersection2d();
		
		//TODO shapeintersection
		
		return intersection;
	}
	
	public boolean isInShape(Shape2d shape){
		int hits = 0;
		for(int ct = 0; ct < shape.points.size() ;ct++){
			if(ct == shape.points.size()-1){
				Line2d line = new Line2d(shape.getPoint(ct),shape.getPoint(0));
				if(this.intersectsLine(line)!=(-1)){
					hits++;
				}
			}else{
				Line2d line = new Line2d(shape.getPoint(ct),shape.getPoint(ct+1));
				if(this.intersectsLine(line)!=(-1)){
					hits++;
				}
			}
		}
		if(hits%2 == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * Returns the point on the ray at the given distance from Ray.origin
	 * @param distance - The distance from Ray.origin to the Point2d
	 * @return The point as Point2d or null if negative distance.
	 */
	public Point2d getPointAt(float distance){
		
		if(distance < 0){
			Console.error("Distance is negative: "+distance);
			return null;
		}
		
		return this.origin.add(direction.multiply(distance));
	}
	
	public String toString(){
		return "Origin: "+this.origin+" / Direction: "+this.direction;
	}
}
