package com.lylat.dungeoncrawler;

public class GridSquare {
	private final int width = 800/20;
	private final int height = 480/20;
	private int x;
	private int y;
	

	public GridSquare(int x, int y){
		this.setX(x);
		this.setY(y);
		System.out.printf("GridSquare created: (%s,%s)\n", x, y);
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}
}
