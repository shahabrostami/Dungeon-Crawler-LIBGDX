package com.lylat.dungeoncrawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerMotion {
	Animation walkAnimation;
	TextureRegion[] walkFrames;

	Animation swordAnimation;
	TextureRegion[] swordFrames;

	Animation bowAnimation;
	TextureRegion[] bowFrames;

	Move swordMove;
	Move walkMove;
	Move bowMove;

	static TextureRegion[][] tmpSwordBig;
	static TextureRegion[][] tmpSword;
	static TextureRegion[][] tmpBow;

	private static final float SWORD_SPEED = 0.025f;
	private static final float BOW_SPEED = 0.025f;
	private static final float WALK_SPEED = 0.1f;

	static {
		final int FRAME_SIZE = 64;
		Texture characterSheetSwordBig = new Texture(Gdx.files.internal("character_big_sword.png"));
		Texture characterSheetSword = new Texture(Gdx.files.internal("character_sword.png"));
		Texture characterSheetBow = new Texture(Gdx.files.internal("character_bow.png"));
		tmpSwordBig = TextureRegion.split(characterSheetSwordBig, FRAME_SIZE*3, FRAME_SIZE*3);
		tmpSword = TextureRegion.split(characterSheetSword, FRAME_SIZE, FRAME_SIZE);
		tmpBow = TextureRegion.split(characterSheetBow, FRAME_SIZE, FRAME_SIZE);
	}

	public PlayerMotion(Move wMove, Move aMove, Move bMove) {
		this.walkMove = wMove;
		this.swordMove = aMove;
		this.bowMove = bMove;
	}

	public void setWalk(int start, int row, int col) {
		walkFrames = new TextureRegion[row * col];
		int index = 0;
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++) {
				this.walkFrames[index] = tmpSword[i + start][j];
				index++;
			}
		walkAnimation = new Animation(WALK_SPEED, walkFrames);
	}

	public void setSword(int start, int row, int col) {
		swordFrames = new TextureRegion[row * col];
		int index = 0;
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++) {
				this.swordFrames[index] = tmpSwordBig[i + start][j];
				index++;
			}
		swordAnimation = new Animation(SWORD_SPEED, swordFrames);
	}

	public void setBow(int start, int row, int col) {
		bowFrames = new TextureRegion[row * col];
		int index = 0;
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++) {
				this.bowFrames[index] = tmpBow[i + start][j];
				index++;
			}
		bowAnimation = new Animation(BOW_SPEED, bowFrames);
	}

	public Animation getWalkAnimation() {
		return walkAnimation;
	}

	public Animation getSwordAnimation() {
		return swordAnimation;
	}

	public Animation getBowAnimation() {
		return bowAnimation;
	}

	public TextureRegion[] getWalkFrames() {
		return walkFrames;
	}

	public TextureRegion[] getSwordFrames() {
		return swordFrames;
	}

	public TextureRegion[] getBowFrames() {
		return bowFrames;
	}

	public Move getSwordMove() {
		return swordMove;
	}

	public Move getWalkMove() {
		return walkMove;
	}

	public Move getBowMove() {
		return bowMove;
	}
}
