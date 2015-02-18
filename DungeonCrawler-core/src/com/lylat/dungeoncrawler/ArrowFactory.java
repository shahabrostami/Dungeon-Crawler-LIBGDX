package com.lylat.dungeoncrawler;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ArrowFactory {
	static Texture up,down,left,right;
	ArrayList<Arrow> arrows;
	static {
		up = new Texture(Gdx.files.internal("arrow_up.png"));
		down = new Texture(Gdx.files.internal("arrow_down.png"));
		left = new Texture(Gdx.files.internal("arrow_left.png"));
		right = new Texture(Gdx.files.internal("arrow_right.png"));
	}
	
	public ArrowFactory(float mapx, float mapy)
	{
		arrows = new ArrayList<Arrow>();
	}
	
	public void newArrow(Move bMove, float x, float y){
		arrows.add(new Arrow(bMove, x, y));
	}
	
	public void update(float x, float y) {
		Arrow arrow;
		for(int i = 0; i < arrows.size(); i++)
		{
			arrow = arrows.get(i);
			arrows.get(i).update();
		}
		
	}
	
	public ArrayList<Arrow> getArrows() {
		return arrows;
	}
	
	public boolean isEmpty() {
		if(arrows.isEmpty())
			return true;
		else return false;
	}
}
