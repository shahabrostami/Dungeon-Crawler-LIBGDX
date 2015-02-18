package com.lylat.dungeoncrawler;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class DungeonCrawler extends ApplicationAdapter {
	final int GRID_SIZE = 16;
	final int SCREEN_W = 800;
	final int SCREEN_H = 480;
	final int GRID_SIZE_W = SCREEN_W / GRID_SIZE;
	final int GRID_SIZE_H = SCREEN_H / GRID_SIZE;

	OrthographicCamera camera;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	SpriteBatch batch;
	Texture img;
	Grid grid;
	GridSquare[][] gridSquares;
	Player player;
	ArrowFactory arrowFactory;
	SwipeMotion swipeMotion;
	Input input;
	PlayerState playerState;
	ArrayList<Arrow> arrowList;

	float stateTime;

	TextureRegion playerFrame;
	TextureRegion swipeFrame;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();
		tiledMap = new TmxMapLoader().load("testmap2.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		batch = new SpriteBatch();
		grid = new Grid(GRID_SIZE_W, GRID_SIZE_H, GRID_SIZE);
		gridSquares = grid.getGridSquares();
		player = new Player();
		swipeMotion = new SwipeMotion();
		playerState = PlayerState.NONE;

		input = new Input(player);
		shapeRenderer = new ShapeRenderer();
		stateTime = 0f;
		
		
	}

	public void drawGrid() {
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		for (int i = 0; i < GRID_SIZE_W; i++)
			for (int j = 0; j < GRID_SIZE_H; j++)
				shapeRenderer.rect(gridSquares[i][j].getX(),
						gridSquares[i][j].getY(), GRID_SIZE, GRID_SIZE);
		shapeRenderer.end();
	}

	@Override
	public void render() {
		stateTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		playerState = player.getState();
		playerFrame = player.getFrame(stateTime);

		player.updatePosition();

		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		batch.begin();
		// drawGrid();
		batch.end();
		batch.begin();
		

		arrowFactory = player.getArrows();
		arrowList = arrowFactory.getArrows();
		arrowFactory.update(player.getX(), player.getY());

		if (playerState == PlayerState.SWORD)
			batch.draw(playerFrame, player.getX()-64, player.getY()-64);
		else
			batch.draw(playerFrame, player.getX(), player.getY());
		
		if(!arrowList.isEmpty())
		{
			for(Arrow arrow : arrowList)
			{
				batch.draw(arrow.getTexture(), arrow.getX()+16, arrow.getY()+12);
			}
		}
		
		batch.end();
	}
}
