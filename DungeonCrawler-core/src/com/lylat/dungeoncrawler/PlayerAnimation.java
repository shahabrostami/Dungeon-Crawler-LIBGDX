package com.lylat.dungeoncrawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerAnimation {
	Animation currentAnimation;
	Texture characterSheet;
	SpriteBatch spriteBatch;

	PlayerMotion right;
	PlayerMotion left;
	PlayerMotion up;
	PlayerMotion down;

	int WALK_ROW = 1;
	int WALK_COL = 9;
	int WALK_LR_COL = 8;
	int ATTACK_COL = 6;
	int ATTACK_ROW = 1;
	int BOW_COL = 12;
	int BOW_ROW = 1;

	public PlayerAnimation() {
		up = new PlayerMotion(Move.WALK_UP, Move.SWORD_UP, Move.BOW_UP);
		up.setWalk(8, WALK_ROW, WALK_COL);
		up.setSword(12, ATTACK_ROW, ATTACK_COL);
		up.setBow(16, BOW_ROW, BOW_COL);

		left = new PlayerMotion(Move.WALK_LEFT, Move.SWORD_LEFT, Move.BOW_LEFT);
		left.setWalk(9, WALK_ROW, WALK_LR_COL);
		left.setSword(13, ATTACK_ROW, ATTACK_COL);
		left.setBow(17, BOW_ROW, BOW_COL);

		down = new PlayerMotion(Move.WALK_DOWN, Move.SWORD_DOWN, Move.BOW_DOWN);
		down.setWalk(10, WALK_ROW, WALK_COL);
		down.setSword(14, ATTACK_ROW, ATTACK_COL);
		down.setBow(18, BOW_ROW, BOW_COL);

		right = new PlayerMotion(Move.WALK_RIGHT, Move.SWORD_RIGHT, Move.BOW_RIGHT);
		right.setWalk(11, WALK_ROW, WALK_LR_COL);
		right.setSword(15, ATTACK_ROW, ATTACK_COL);
		right.setBow(19, BOW_ROW, BOW_COL);

		currentAnimation = down.getWalkAnimation();
		spriteBatch = new SpriteBatch();
	}

	public Animation getAnimation(Move move) {
		switch (move) {
		case WALK_LEFT:
			currentAnimation = left.getWalkAnimation();
			break;
		case WALK_RIGHT:
			currentAnimation = right.getWalkAnimation();
			break;
		case WALK_DOWN:
			currentAnimation = down.getWalkAnimation();
			break;
		case WALK_UP:
			currentAnimation = up.getWalkAnimation();
			break;
		case SWORD_LEFT:
			currentAnimation = left.getSwordAnimation();
			break;
		case SWORD_RIGHT:
			currentAnimation = right.getSwordAnimation();
			break;
		case SWORD_DOWN:
			currentAnimation = down.getSwordAnimation();
			break;
		case SWORD_UP:
			currentAnimation = up.getSwordAnimation();
			break;
		case BOW_LEFT:
			currentAnimation = left.getBowAnimation();
			break;
		case BOW_RIGHT:
			currentAnimation = right.getBowAnimation();
			break;
		case BOW_DOWN:
			currentAnimation = down.getBowAnimation();
			break;
		case BOW_UP:
			currentAnimation = up.getBowAnimation();
			break;
		case NONE:
			break;
		default:
			break;
		}
		return currentAnimation;
	}

	public PlayerMotion right() {
		return right;
	}

	public PlayerMotion left() {
		return left;
	}

	public PlayerMotion down() {
		return down;
	}

	public PlayerMotion up() {
		return up;
	}
}
