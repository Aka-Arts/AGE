package org.akaarts.earlyalpha.AGE.grid2d;

public class GridMap {
	
	GridSquare[][] gridSquares;
	final int width,length;
	
	public GridMap(int width, int length){
		this.width = width;
		this.length = length;
		
		this.gridSquares = new GridSquare[width][length];
	}
}
