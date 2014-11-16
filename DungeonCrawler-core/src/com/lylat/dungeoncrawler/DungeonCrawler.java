package com.lylat.dungeoncrawler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class DungeonCrawler extends ApplicationAdapter {
	final int GRID_SIZE = 20;
	final int GRID_SIZE_W = 800 / GRID_SIZE;
	final int GRID_SIZE_H = 480 / GRID_SIZE;
	final int MOVEMENT_SPEED_WALK = 4;
	final int MOVEMENT_SPEED_RUN = 12;

	SpriteBatch batch;
	Texture img;
	Grid grid;
	GridSquare[][] gridSquares;
	Player player;
	Input input;
	
	float stateTime;

	TextureRegion playerFrame;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create() {
		batch = new SpriteBatch();
		grid = new Grid(GRID_SIZE_W, GRID_SIZE_H, GRID_SIZE);
		gridSquares = grid.getGridSquares();
		player = new Player();
		input = new Input(player);
		shapeRenderer = new ShapeRenderer();
		stateTime = 0f;
	}

	public void drawGrid() {
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		for (int i = 0; i < GRID_SIZE_W; i++)
			for (int j = 0; j < GRID_SIZE_H; j++)
				shapeRenderer.rect(gridSquares[i][j].getX(), gridSquares[i][j].getY(), GRID_SIZE, GRID_SIZE);
		shapeRenderer.end();
	}

	@Override
	public void render() {
		stateTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		drawGrid();
		batch.end();
		
		playerFrame = player.getFrame(stateTime);
				
		player.updatePosition();
		batch.begin();
		batch.draw(playerFrame, player.getX(), player.getY());
		batch.end();
	}
}
