package com.azo.flybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class FlyBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	Texture enemy4;
	Texture enemy5;
	Texture enemy6;
	float birdX = 0;
	float birdY = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.1f;
	float enemyVelocity = 2;
	Random random;
	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;

	Circle birdCricle;
	//siyah noktalar
	ShapeRenderer shapeRenderer;

	int numberOfEnemies = 4;
	float[] enemyX = new float[numberOfEnemies];
	float[] enemyOffSet = new float[numberOfEnemies];
	float[] enemyOffSet2 = new float[numberOfEnemies];
	float[] enemyOffSet3 = new float[numberOfEnemies];
	float distance =0;


	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;





	@Override
	public void create () {
		//bağladığında ne olacaksa
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		enemy1 = new Texture("enemy.png");
		enemy2 = new Texture("enemy.png");
		enemy3 = new Texture("enemy.png");


		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();

		birdX = Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2;
		birdY = Gdx.graphics.getHeight() / 3;

		shapeRenderer = new ShapeRenderer();

		birdCricle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];


		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);


		for (int i =0; i<numberOfEnemies; i++) {

			enemyOffSet[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
			enemyOffSet2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
			enemyOffSet3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());


			enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2 + i * distance;

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();


		}


	}

	@Override
	public void render () {
		//oyun devam ederken ne olacaksa
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState ==1){

			if (enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2 ){
				score++;

				if (scoredEnemy < numberOfEnemies - 1) {
					scoredEnemy++;
				}else {
					scoredEnemy = 0;
				}

			}

			if(Gdx.input.justTouched()){
				velocity = -3;
			}

			for(int i = 0; i< numberOfEnemies; i++){


				if (enemyX[i] < Gdx.graphics.getWidth() / 15) {
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat()- 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat()- 0.5f) * (Gdx.graphics.getHeight() - 200);



				}else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}


				enemyX[i] = enemyX[i] - enemyVelocity;

				batch.draw(enemy1,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffSet[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
				batch.draw(enemy2,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffSet2[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
				batch.draw(enemy3,enemyX[i],Gdx.graphics.getHeight() / 2 + enemyOffSet3[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);


				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, + Gdx.graphics.getHeight() / 2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, + Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, + Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);

			}

			if(birdY > 0 ){
				velocity = velocity + gravity;
				birdY = birdY -velocity;
			}else {
				gameState = 2;
			}

		}else if (gameState == 0){
			if(Gdx.input.justTouched()) {
				gameState = 1;
			}
			}else if (gameState ==2){

			font2.draw(batch,"Game Over! Tap To Play Again",100,Gdx.graphics.getHeight() / 2);

				if(Gdx.input.justTouched()) {
				gameState = 1;

				birdY = Gdx.graphics.getHeight() / 3;

					for (int i =0; i<numberOfEnemies; i++) {

						enemyOffSet[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
						enemyOffSet2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
						enemyOffSet3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());


						enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2 + i * distance;

						enemyCircles[i] = new Circle();
						enemyCircles2[i] = new Circle();
						enemyCircles3[i] = new Circle();

					}
					velocity = 0;
					scoredEnemy = 0;
					score = 0;
				}
		}


		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10 );
		font.draw(batch,String.valueOf(score),100,200);


		batch.end();

		birdCricle.set(birdX + Gdx.graphics.getWidth() / 30 ,birdY + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCricle.x,birdCricle.y,birdCricle.radius);
		//ARI üzeri Siyah nokta
		for (int i=0; i < numberOfEnemies; i++){
//			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, + Gdx.graphics.getHeight() / 2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
//			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, + Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
//			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, + Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
//			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, + Gdx.graphics.getHeight() / 2 + enemyOffSet4[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
//			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, + Gdx.graphics.getHeight() / 2 + enemyOffSet5[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
//			shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30, + Gdx.graphics.getHeight() / 2 + enemyOffSet6[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);

			if (Intersector.overlaps(birdCricle,enemyCircles[i]) || Intersector.overlaps(birdCricle,enemyCircles2[i]) || Intersector.overlaps(birdCricle,enemyCircles3[i])) {
				gameState = 2;
			}

		}

		//shapeRenderer.end();
	}

	@Override
	public void dispose () {

	}
}
