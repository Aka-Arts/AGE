package org.akaarts.earlyalpha.AGE.collision2d;

public class Line2d {
	public Point2d origin;
	public Vector2d halfWidth;
	
	public Line2d(Point2d point1 , Point2d point2){
		
		this.origin = point1.subtract(point1.subtract(point2).multiply(0.5f));
		this.halfWidth = point1.subtract(point2).multiply(0.5f);
		
	}
	
	/**
	 * Calculates and returns the normal Vector of the line
	 * @return The normalized normal
	 */
	public Vector2d getNormal(){
		
		Point2d p1,p2;
		p1 = this.origin.subtract(halfWidth);
		p2 = this.origin.add(halfWidth);
		return new Vector2d(p2.y-p1.y,-(p2.x-p1.x)).getNormalized();
	}
	
	public String toString(){
		
		Point2d p1,p2;
		
		p1 = this.origin.subtract(halfWidth);
		p2 = this.origin.add(halfWidth);
		Vector2d norm = new Vector2d(p2.y-p1.y,-(p2.x-p1.x)).getNormalized();
		
		return "P1: "+p1+" / P2: "+p2+" / normal: "+norm;
	}
}
