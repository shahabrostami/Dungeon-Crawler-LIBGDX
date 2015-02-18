package com.lylat.dungeoncrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Arrow {
	static Texture up,down,left,right;
	static {
		up = new Texture(Gdx.files.internal("arrow_up.png"));
		down = new Texture(Gdx.files.internal("arrow_down.png"));
		left = new Texture(Gdx.files.internal("arrow_left.png"));
		right = new Texture(Gdx.files.internal("arrow_right.png"));
	}
	
	private Move move;
	private Texture texture;
	private float x;
	private float y;
	private int x_change = 0;
	private int y_change = 0;
	public Arrow(Move bMove, float x, float y)
	{
		this.move = move;
		this.x = x;
		this.y = y;
		switch(bMove)
		{
		case BOW_UP:
			this.texture = up;
			y_change = 1;
			break;
		case BOW_DOWN:
			this.texture = down;
			y_change = -1;
			break;
		case BOW_LEFT:
			this.texture = left;
			x_change = -1;
			break;
		case BOW_RIGHT:
			this.texture = right;
			x_change = 1;
			break;
		}
	}
	
	public void update(){
		this.x += (x_change * Gdx.graphics.getDeltaTime()*300);
		this.y += (y_change * Gdx.graphics.getDeltaTime()*300);
	}
	
	public Texture getTexture(){
		return texture;
	}

	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
}
