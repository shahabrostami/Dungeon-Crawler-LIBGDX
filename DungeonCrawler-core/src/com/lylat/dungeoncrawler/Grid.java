package com.lylat.dungeoncrawler;

public class Grid {
	private int width;
	private int height;
	private int unit;
	private GridSquare[][] gridSquares;
	
	public Grid(int width, int height, int unit) {
		this.width = width;
		this.height = height;
		this.unit = unit;
		gridSquares = new GridSquare[width][height];
		
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				gridSquares[i][j] = new GridSquare(i*unit, j*unit);	
	}
	
	public GridSquare[][] getGridSquares(){
		return gridSquares;
	}
}
