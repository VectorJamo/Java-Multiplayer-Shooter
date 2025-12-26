package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import main.Game;

public abstract class UIElement {
	
	protected float x, y;
	protected float width, height;
	
	protected Game game;
	
	public UIElement(float x, float y, Game game) {
		this.x = x;
		this.y = y;

		this.game = game;
	}
	public UIElement(float x, float y, float width, float height, Game game) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.game = game;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public abstract void update();
	public abstract void render(Graphics2D g2d);

}
