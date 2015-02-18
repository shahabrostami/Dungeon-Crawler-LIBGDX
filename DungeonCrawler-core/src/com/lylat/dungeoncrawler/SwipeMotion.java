package com.lylat.dungeoncrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SwipeMotion {
	Animation swipeAnimation;

	Move aMove;

	static TextureRegion[][] tmpSwipe;

	private static final float SWIPE_SPEED = 0.015f;

	float x = 0;
	float y = 0;
	final int FRAME_SIZE = 64;
	final int row = 1;
	final int col = 11;
	private TextureRegion[] swipeDownFrames, swipeUpFrames, swipeLeftFrames, swipeRightFrames;
	private Animation swipeDownAnimation, swipeUpAnimation, swipeLeftAnimation, swipeRightAnimation;

	private float swipeTime = 0f;
	private boolean swipeStart = true;
	public TextureRegion[] makeMotion(int start)
	{
		TextureRegion[] swipeFrames = new TextureRegion[row*col];
		int index = 0;
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++) {
				swipeFrames[index] = tmpSwipe[i + start][j];
				index++;
			}
		return swipeFrames;
	}
	public SwipeMotion() {
		Texture swipeSheet = new Texture(Gdx.files.internal("swipeanimation.png"));
		tmpSwipe = TextureRegion.split(swipeSheet, FRAME_SIZE, FRAME_SIZE);
		swipeDownFrames = makeMotion(5);
		swipeLeftFrames = makeMotion(1);
		swipeRightFrames = makeMotion(2);
		swipeUpFrames = makeMotion(3);
		
		swipeDownAnimation = new Animation(SWIPE_SPEED, swipeDownFrames);
		swipeLeftAnimation = new Animation(SWIPE_SPEED, swipeLeftFrames);
		swipeRightAnimation = new Animation(SWIPE_SPEED, swipeRightFrames);
		swipeUpAnimation = new Animation(SWIPE_SPEED, swipeUpFrames);
	}
	
	public TextureRegion[] getSwipeDownFrames(){
		return swipeDownFrames;
	}
	
	public TextureRegion[] getSwipeUpFrames(){
		return swipeUpFrames;
	}
	
	public TextureRegion[] getSwipeRightFrames(){
		return swipeRightFrames;
	}
	
	public TextureRegion[] getSwipeLeftFrames(){
		return swipeLeftFrames;
	}
	
	public Animation getAnimation(Move aMove)
	{
		switch(aMove)
		{
		case SWORD_UP:
			return swipeUpAnimation;
		case SWORD_DOWN:
			return swipeDownAnimation;
		case SWORD_RIGHT:
			return swipeRightAnimation;
		case SWORD_LEFT:
			return swipeLeftAnimation;
		default:
			break;
		}
		return swipeAnimation;
	}
	
	public TextureRegion getFrame(float stateTime, Move currentMove) {
		Animation currentAnimation = getAnimation(currentMove);
		if (swipeStart) {
			swipeTime = stateTime;
			swipeStart = false;
		}
		System.out.println(stateTime - swipeTime);
		TextureRegion nextFrame = currentAnimation.getKeyFrame(stateTime - swipeTime, false);
		if (currentAnimation.isAnimationFinished(stateTime - swipeTime)) {
			swipeTime = 0f;
			swipeStart = true;
		}
		return nextFrame;
	}
	public void updatePosition(Player player){
		this.x = player.getX();
		this.y = player.getY();
		switch(player.getMove())
		{
		case SWORD_UP:
			this.y += 5;
			this.x += 10;
			break;
		case SWORD_DOWN:
			this.y -= 20;
			this.x += 10;
			break;
		case SWORD_RIGHT:
			this.x += 25;
			this.y -= 10;
			break;
		case SWORD_LEFT:
			this.x -= 25;
			this.y -= 10;
			break;
		default:
			break;
		}
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
}
