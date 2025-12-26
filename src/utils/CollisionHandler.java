package utils;

import entities.Entity;
import objects.Bullet;

public class CollisionHandler {
	
	public static boolean checkEntityBulletCollision(Entity entity, Bullet bullet) {
		if(entity.getX() > bullet.getX() + bullet.getHeight() || entity.getX() + entity.getWidth() < bullet.getX()
				|| entity.getY() > bullet.getY() + bullet.getHeight() || entity.getY() + entity.getHeight() < bullet.getY()) {
			return false;
		}
		return true;
	}

}
