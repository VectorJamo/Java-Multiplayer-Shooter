package objects;

import java.awt.Graphics;
import java.awt.Graphics2D;

import main.Game;

public abstract class Object {
	
	protected Game game;
	protected float x, y, width, height;
	protected OBJECTS objectType;
	
	public Object(Game game, OBJECTS objectType) {
		this.game = game;
		this.objectType = objectType;
	}

	public Object(float x, float y, float width, float height, Game game, OBJECTS entityType) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.game = game;
	}
	
	public abstract void update();
	public abstract void render(Graphics2D g2d);

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
	public OBJECTS getObjectType() {
		return objectType;
	}

	public void setObjectType(OBJECTS objectType) {
		this.objectType = objectType;
	}

	

}
