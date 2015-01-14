package org.akaarts.earlyalpha.AGE.collision2d;

public class Point2d {
	float x,y;
	
	/**
	 * Creates a new 2D point from x and y
	 * @param x - The x position
	 * @param y - the y position
	 */
	public Point2d(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Subtracts another point from this point
	 * @param point - The other point
	 * @return A vector with the difference
	 */
	public Vector2d subtract(Point2d point){
		return new Vector2d(this.x-point.x,this.y-point.y);
	}
	
	public Point2d subtract(Vector2d vector){
		return new Point2d(this.x-vector.x,this.y-vector.y);
	}
	
	/**
	 * Adds a vector to this point
	 * @param vector - The vector
	 * @return A point from the sum
	 */
	public Point2d add(Vector2d vector){
		return new Point2d(this.x+vector.x,this.y+vector.y);
	}
	
	public float getDistTo(Point2d point){
		
		float X = point.x - this.x;
		float Y = point.y - this.y;
		
		
		return (float) Math.sqrt(X*X+Y*Y);
	}
	
	public String toString() {
		return "X: "+this.x+" / Y: "+this.y;
	}

	
}
