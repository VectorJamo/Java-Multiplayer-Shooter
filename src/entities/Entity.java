package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;

import main.Game;

public abstract class Entity {
	
	protected Game game;
	protected float x, y, width, height;
	protected ENTITIES entityType;
	
	public Entity(Game game, ENTITIES entityType) {
		this.game = game;
		this.entityType = entityType;
	}

	public Entity(float x, float y, float width, float height, Game game, ENTITIES entityType) {
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
	public ENTITIES getEntityType() {
		return entityType;
	}

	public void setEntityType(ENTITIES entityType) {
		this.entityType = entityType;
	}

	

}
