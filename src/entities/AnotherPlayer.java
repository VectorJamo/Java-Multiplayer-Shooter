package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.Game;
import networking.server.ClientInfo;
import networking.utilities.GeneralInfo;
import objects.Bullet;
import ui.DefaultTheme;
import utils.CollisionHandler;

// The client player on the server
public class AnotherPlayer extends Entity {
	
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public static int playerHealth = 100; // For the client player
	
	public AnotherPlayer(float x, float y, float width, float height, Game game, ENTITIES entityType) {
		super(x, y, width, height, game, entityType);
	}
	
	@Override
	public void update() {
		if(GeneralInfo.isServer) {
			// Update the client's bullets
			for(int i = 0;  i < bullets.size(); ) {
				bullets.get(i).update();
				
				if(bullets.get(i).isOutOfBounds()) {
					bullets.remove(i);
					continue;
				}
				i++;
			}
			
			// Check for server's bullets collision
			for(int i = 0;i < Player.bullets.size();) {
				if(CollisionHandler.checkEntityBulletCollision(this, Player.bullets.get(i))) {
					playerHealth -= 10;
					
					Player.bullets.remove(i);
					continue;
				}
				i++;
			}
			
			
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(DefaultTheme.warmWhite);
		g2d.fillRect((int)this.x, (int)this.y, (int)this.width, (int)this.height);
		
		// Client's name
		if(ClientInfo.hasEstablishedClient) {
			g2d.setColor(new Color(1.0f, 1.0f,0.f, 1.0f));
			g2d.setFont(DefaultTheme.defaultSmallFont);
			g2d.drawString(ClientInfo.clientName, this.x, this.y-5);
		}
	}
}
