package org.akaarts.AGE.collision2d;

/**
 * Class for 2D float vectors.
 * 
 * @author Luca Egli
 */
public class Vector2d {

	public float x,y;
	/**
	 * Creates a new 2D vector from x and y
	 * 
	 * @param x	- The x value
	 * @param y - The y value
	 */
	public Vector2d(float x, float y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Calculates the length of the Vector2d
	 * @return The length as float
	 */
	public float length(){
		return (float) Math.sqrt((this.x*this.x)+(this.y*this.y));
	}

	/**
	 * Returns, if the Vector2d is a null vector
	 * @return true if true, yeah...
	 */
	public boolean isNull(){
		if((this.x==0)&&(this.y==0)){
			return true;
		}
		return false;
	}

	/**
	 * Returns a normalized version of the Vector2d
	 * @return The normalized Vector2d
	 */
	public Vector2d getNormalized(){
		return new Vector2d(this.x/this.length(),this.y/this.length());
	}

	/**
	 * Calculates the dot/scalar product of this Vector2d with another Vector2d
	 * @param vector - The second Vector2d
	 * @return The dot product of the two Vector2d
	 */
	public float dotWith(Vector2d vector){
		if(!this.isNull() && !vector.isNull()){
			if(vector.x == this.x && vector.y == this.y){
				return 1;
			}
			return ((this.x * vector.x + this.y * vector.y));
		}else{
			return 0;
		}
	}

	/**
	 * Returns the (smaller) angle in radiant between this Vector2d and another Vector2d.<br><b style="color:red">Caution! Uses trigonometry</b>
	 * @param vector - The other Vector2d
	 * @return The angle in radiant
	 */
	public float getAngleTo(Vector2d vector){
		return (float) (Math.acos(this.dotWith(vector)/(this.length()*vector.length())));
	}
	
	/**
	 * Returns a moved version of this vector, so that vector is the new coordinate center: (0/0).
	 * @param vector - The new center
	 * @return The localized vector
	 */
	public Vector2d localizeTo(Point2d point){
		return new Vector2d(this.x - point.x,this.y-point.y);
	}
	
	public float getDistTo(Vector2d vector){
		
		float X = vector.x - this.x;
		float Y = vector.y - this.y;
		
		
		return (float) Math.sqrt(X*X+Y*Y);
	}

	public String toString() {
		return "X: "+this.x+" / Y: "+this.y;
	}
	
	public Vector2d multiply(float num){
		return new Vector2d(this.x*num,this.y*num);
	}

}
