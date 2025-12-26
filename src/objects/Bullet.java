package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import graphics.Window;
import main.Game;
import math.vec2;

public class Bullet extends Object {
	
	public static final float BULLET_SIZE = 10.0f;
	public static Color bulletColor = new Color(0.2f, 0.8f, 0.1f, 1.0f);
	public static float bulletSpeed = 200.0f;
	
	private vec2 bulletVelocity;

	public Bullet(float x, float y, vec2 bulletVelocity, Game game, OBJECTS entityType) {
		super(x, y, BULLET_SIZE, BULLET_SIZE, game, entityType);
		
		this.bulletVelocity = bulletVelocity;
	}

	@Override
	public void update() {
		this.x += (this.bulletVelocity.getX()*bulletSpeed) * game.getDeltaTime();
		this.y += (this.bulletVelocity.getY()*bulletSpeed) * game.getDeltaTime();
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(bulletColor);
		g2d.fillRect((int)this.x, (int)this.y, (int)BULLET_SIZE, (int)BULLET_SIZE);
	}
	
	public boolean isOutOfBounds() {
		if(this.x > 0.0f && this.x + this.width < Window.WIDTH && this.y > 0.0f && this.y + this.height < Window.HEIGHT) {
			return false;
		}
		return true;
	}

}
