package ru.lockezone.pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

class Box {
	int x, y;
	int width, height;
	Box(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}

class Pad extends Box {
	int player;
	Pad(int x, int y, int player) {
		super(x, y, 30, 100);
		this.player = player;
	}
}

class Ball extends Box {
	boolean up = false;
	boolean right = false;
	Ball() {
		super(320, 240, 30, 30);
	}

	void fallDown() {
		if (!right) {
			this.x -= 1;
		} else {
			this.x += 1;
		}
		if (!up) {
			this.y -= 1;
		} else {
			this.y += 1;
		}
		if (this.y <= 0) {
			up = true;
		} else if (this.y >= 480) {
			up = false;
		}
	}

	void checkCollision(Pad player) {
		if (player.x <= this.x && this.x <= player.x + player.width &&
				player.y <= this.y && this.y <= player.y + player.height &&
				player.player == 1) {
			this.right = true;
		}
		if (player.x <= this.x && this.x <= player.x + player.width &&
				player.y <= this.y && this.y <= player.y + player.height &&
				player.player == 2) {
			this.right = false;
		}
	}

	String whichSide() {
		if (x < 0) {
			this.x = 320;
			this.y = 240;
			return "Left";
		} else if (x > 640) {
			this.x = 320;
			this.y = 240;
			return "Right";
		} else {
			return "Neither";
		}
	}
}

public class Pong extends ApplicationAdapter {
	ShapeRenderer renderer;
	Pad player1, player2;
	Ball ball;
	int player1Score = 0;
	int player2Score = 0;

	@Override
	public void create () {
		renderer = new ShapeRenderer();
		player1 = new Pad(50, 50, 1);
		player2 = new Pad(580, 50, 2);
		ball = new Ball();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.WHITE);
		renderer.rect(player1.x, player1.y, player1.width, player1.height);
		renderer.end();

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.WHITE);
		renderer.rect(player2.x, player2.y, player2.width, player2.height);
		renderer.end();

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.WHITE);
		renderer.rect(ball.x, ball.y, ball.width, ball.height);
		renderer.end();

		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player1.y += 1;
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			player1.y -= 1;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			player2.y += 1;
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			player2.y -= 1;
		}

		ball.fallDown();
		if (ball.whichSide().equals("Left")) {
			player1Score += 1;
			System.out.println("Tadah! Player 1 Scored!");
		} else if (ball.whichSide().equals("Right")) {
			player2Score += 1;
			System.out.println("Tadah! Player 2 Scored!");
		}
		ball.checkCollision(player1);
		ball.checkCollision(player2);
	}
	
	@Override
	public void dispose () {
	}
}
