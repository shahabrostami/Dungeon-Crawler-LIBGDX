package com.lylat.dungeoncrawler;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
	private boolean attackStart = false;;
	private float attackTime = 0f;
	private PlayerAnimation animation;
	private PlayerState state;
	private float x;
	private float y;
	private Stack<Move> moves;
	private PlayerMotion currentMotion;
	private Move currentMove;

	private int MOVEMENT_SPEED_WALK = 100;

	public Player() {
		this.x = 0;
		this.y = 0;
		moves = new Stack<Move>();
		this.state = PlayerState.NONE;
		this.currentMove = Move.NONE;
		this.animation = new PlayerAnimation();
		this.currentMotion = animation.down();
	}

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x += x * (MOVEMENT_SPEED_WALK * Gdx.graphics.getDeltaTime());
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y += y * (MOVEMENT_SPEED_WALK * Gdx.graphics.getDeltaTime());
	}

	public void updatePosition() {
		switch (currentMove) {
		case WALK_LEFT:
			setX(-1);
			break;
		case WALK_RIGHT:
			setX(1);
			break;
		case WALK_UP:
			setY(1);
			break;
		case WALK_DOWN:
			setY(-1);
			break;
		}
	}

	public int getMovementSpeed() {
		return MOVEMENT_SPEED_WALK;
	}

	public void setMovementSpeed(int newSpeed) {
		this.MOVEMENT_SPEED_WALK = newSpeed;
	}

	public void setStateWalk() {
		state = PlayerState.WALK;
	}

	public void setStateNone() {
		moves.clear();
		currentMove = Move.NONE;
		state = PlayerState.NONE;
	}

	public void setStateSword() {
		attackTime = 0f;
		attackStart = true;
		state = PlayerState.SWORD;
	}

	public void setStateBow() {
		attackTime = 0f;
		attackStart = true;
		state = PlayerState.BOW;
	}

	public PlayerState getState() {
		return state;
	}

	public TextureRegion getFrame(float stateTime) {
		Animation currentAnimation = animation.getAnimation(currentMove);
		TextureRegion nextFrame = currentAnimation.getKeyFrame(0, true);
		switch (state) {
		case SWORD:
			if (attackStart) {
				attackTime = stateTime;
				attackStart = false;
			}
			nextFrame = currentAnimation.getKeyFrame(stateTime - attackTime, false);
			if (currentAnimation.isAnimationFinished(stateTime - attackTime)) {
				if (moves.isEmpty()) {
					currentMove = Move.NONE;
					setStateNone();
				} else {
					currentMove = moves.pop();
					setStateWalk();
				}
			}
			break;
		case BOW:
			if (attackStart) {
				attackTime = stateTime;
				attackStart = false;
			}
			System.out.printf("%s\n", stateTime - attackTime);
			nextFrame = currentAnimation.getKeyFrame(stateTime - attackTime, false);
			if (currentAnimation.isAnimationFinished(stateTime - attackTime)) {
				if (moves.isEmpty()) {
					currentMove = Move.NONE;
					setStateNone();
				} else {
					currentMove = moves.pop();
					setStateWalk();
				}
			}
			break;
		case WALK:
			nextFrame = currentAnimation.getKeyFrame(stateTime, true);
			break;
		default:
			break;
		}
		return nextFrame;
	}

	public void updateDirection(Move newMove) {
		switch (newMove) {
		case WALK_LEFT:
			currentMotion = animation.left();
			break;
		case WALK_RIGHT:
			currentMotion = animation.right();
			break;
		case WALK_DOWN:
			currentMotion = animation.down();
			break;
		case WALK_UP:
			currentMotion = animation.up();
			break;
		}
	}

	public void addWalk(Move move) {
		if (isMoveWalk(currentMove))
			moves.add(currentMove);
	}

	public Move updateWalk(Move newMove, boolean isKeyDown) {
		// If adding and is not current move, add move.
		if (isKeyDown) {
			if (!currentMove.isEqual(newMove)) {
				setStateWalk();
				addWalk(currentMove);
				currentMove = newMove;
			}
			// If key not down.
		} else if (!isKeyDown) {
			// And the move is the current one.
			if (currentMove.isEqual(newMove)) {
				// If there are no more keys pressed, movement is none.
				if (moves.isEmpty()) {
					currentMove = Move.NONE;
					setStateNone();
					// If there are moves, set to most recent move from stack.
				} else
					currentMove = moves.pop();
				// And the move is not current move, remove from stack.
			} else if (!currentMove.isEqual(newMove))
				moves.remove(newMove);
		}
		updateDirection(currentMove);
		System.out.println(moves.toString());
		return currentMove;
	}

	public void doWalk(int keycode, boolean t) {
		System.out.printf("Handling Move: %s\n", Keys.toString(keycode));
		if (keycode == Keys.LEFT)
			currentMove = updateWalk(Move.WALK_LEFT, t);
		else if (keycode == Keys.RIGHT)
			currentMove = updateWalk(Move.WALK_RIGHT, t);
		else if (keycode == Keys.UP)
			currentMove = updateWalk(Move.WALK_UP, t);
		else if (keycode == Keys.DOWN)
			currentMove = updateWalk(Move.WALK_DOWN, t);
	}

	public void doSword() {
		setStateSword();
		addWalk(currentMove);
		currentMove = currentMotion.getSwordMove();
	}

	public void doBow() {
		setStateBow();
		addWalk(currentMove);
		currentMove = currentMotion.getBowMove();
	}

	public void doAction(int keycode, boolean t) {
		if (isKeyWalk(keycode))
			doWalk(keycode, t);
		if (isKeySword(keycode) && t && !isSword())
			doSword();
		if (isKeyBow(keycode) && t && !isBow())
			doBow();
	}

	public boolean isKeyBow(int keycode) {
		if (keycode == Keys.X)
			return true;
		else
			return false;
	}

	public boolean isKeySword(int keycode) {
		if (keycode == Keys.Z)
			return true;
		else
			return false;
	}

	public boolean isKeyWalk(int keycode) {
		if (keycode == Keys.DOWN || keycode == Keys.UP || keycode == Keys.RIGHT || keycode == Keys.LEFT)
			return true;
		else
			return false;
	}

	public boolean isMoveWalk(Move move) {
		if (move == Move.WALK_DOWN || move == Move.WALK_UP || move == Move.WALK_RIGHT || move == Move.WALK_LEFT)
			return true;
		else
			return false;
	}

	public Move getMove() {
		return currentMove;
	}

	public boolean isSword() {
		if (state == PlayerState.SWORD)
			return true;
		else
			return false;
	}

	public boolean isBow() {
		if (state == PlayerState.BOW)
			return true;
		else
			return false;
	}

	public Animation getAnimation(Move move) {
		return animation.getAnimation(move);
	}
}
