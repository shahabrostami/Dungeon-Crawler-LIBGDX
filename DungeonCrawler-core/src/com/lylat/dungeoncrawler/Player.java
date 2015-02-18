package com.lylat.dungeoncrawler;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {

	private final int MOVEMENT_SPEED_WALK = 100;
	private final int MOVEMENT_SPEED_RUN = 200;
	
	private boolean attackStart = false;
	private float attackTime = 0f;
	private Animation previousAnimation;
	private Animation currentAnimation;
	private PlayerAnimation animation;
	private PlayerState state;
	private float x;
	private float y;
	private int playerSpeed = MOVEMENT_SPEED_WALK;
	private Stack<Move> moves;
	private PlayerMotion currentMotion;
	private Move currentMove;
	ArrowFactory arrows;

	public Player() {
		this.x = 0;
		this.y = 0;
		moves = new Stack<Move>();
		this.state = PlayerState.NONE;
		this.currentMove = Move.NONE;
		this.animation = new PlayerAnimation();
		this.currentMotion = animation.down();
		this.arrows = new ArrowFactory(800, 480);
	}

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x += x * (playerSpeed * Gdx.graphics.getDeltaTime());
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y += y * (playerSpeed * Gdx.graphics.getDeltaTime());
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
		this.playerSpeed = newSpeed;
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
		currentAnimation = animation.getAnimation(currentMove);
		TextureRegion nextFrame = currentAnimation.getKeyFrame(0, false);
		switch (state) {
		case NONE:
			nextFrame = currentMotion.getWalkAnimation().getKeyFrame(0, false);
			break;
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
		arrows.newArrow(currentMove, x, y);
	}

	public void doAction(int keycode, boolean t) {
		if (isKeyWalk(keycode))
			doWalk(keycode, t);
		if (isKeySword(keycode) && t && !isSword())
			doSword();
		if (isKeyBow(keycode) && t && !isBow())
			doBow();
		if(isKeyRun(keycode))
			doRun(t);
		
	}
	
	public void doRun(boolean t){
		if(t) playerSpeed = MOVEMENT_SPEED_RUN;
		else playerSpeed = MOVEMENT_SPEED_WALK;
	}
	
	public boolean isKeyRun(int keycode)
	{
		if(keycode == Keys.SPACE)
			return true;
		else return false;
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
	
	public ArrowFactory getArrows() {
		return arrows;
	}

	public Animation getAnimation(Move move) {
		return animation.getAnimation(move);
	}
}
