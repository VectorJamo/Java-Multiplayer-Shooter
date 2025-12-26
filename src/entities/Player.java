package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import audio.audioHandler.SoundEffects;
import input.KeyManager;
import input.MouseManager;
import main.Game;
import math.vec2;
import networking.client.Client;
import networking.client.ServerInfo;
import networking.client.ServerState;
import networking.client.ServerStateManager;
import networking.packets.statePackets.ClientStatePacket;
import networking.packets.statePackets.ServerStatePacket;
import networking.server.ClientInfo;
import networking.server.Server;
import networking.utilities.GeneralInfo;
import objects.Bullet;
import objects.OBJECTS;
import ui.DefaultTheme;
import utils.CollisionHandler;

public class Player extends Entity {
	
	private static String name = null;
	
	public static final float playerSpeed = 200.0f;
	public static int playerHealth = 100; // For the server player
	
	// For player as client
	public static ClientPlayerState clientState = new ClientPlayerState();
	
	// For player as either client or server
	private vec2 shootDirection;
	private float shootRadius;
	private boolean isAiming = true;
	
	public boolean canShoot = true;
	public double shootTimer = 0.0;
	
	// For player as server
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
		
	public Player(float x, float y, float width, float height, Game game, ENTITIES entityType) {
		super(x, y, width, height, game, entityType);
		
		shootDirection = new vec2(0.0f, 0.0f);		
		shootRadius = 100.0f;
	}

	@Override
	public void update() {
		isAiming = false;
		
		if(GeneralInfo.isServer) {
			if(KeyManager.keyState[KeyEvent.VK_W]) {
				this.y -= playerSpeed * game.getDeltaTime();
			}
			if(KeyManager.keyState[KeyEvent.VK_A]) {
				this.x -= playerSpeed * game.getDeltaTime();
			}
			if(KeyManager.keyState[KeyEvent.VK_S]) {
				this.y += playerSpeed * game.getDeltaTime();
			}
			if(KeyManager.keyState[KeyEvent.VK_D]) {
				this.x += playerSpeed * game.getDeltaTime();
			}
			
			// Check for client's bullets collision
			for(int i = 0;i < AnotherPlayer.bullets.size();) {
				if(CollisionHandler.checkEntityBulletCollision(this, AnotherPlayer.bullets.get(i))) {
					playerHealth -= 10;
					
					AnotherPlayer.bullets.remove(i);
					continue;
				}
				i++;
			}
		}else {
			clientState.moveLeft = false;
			clientState.moveRight = false;
			clientState.moveUp = false;
			clientState.moveDown = false;

			if(KeyManager.keyState[KeyEvent.VK_W]) {
				clientState.moveUp = true;
			}
			if(KeyManager.keyState[KeyEvent.VK_A]) {
				clientState.moveLeft = true;
			}
			if(KeyManager.keyState[KeyEvent.VK_S]) {
				clientState.moveDown = true;
			}
			if(KeyManager.keyState[KeyEvent.VK_D]) {
				clientState.moveRight = true;
			}
		}
		
		// Shoot mechanics
		if(GeneralInfo.isServer) {
			float playerCenterX = this.x + this.width/2;
			float playerCenterY = this.y + this.height/2;
			float mouseX = MouseManager.mouseX;
			float mouseY = MouseManager.mouseY;
			float dxSquared = (playerCenterX - mouseX)*(playerCenterX - mouseX);
			float dySquared = (playerCenterY - mouseY)*(playerCenterY - mouseY);
			
			float distance = (float) Math.sqrt(dxSquared + dySquared);
			if(distance < shootRadius) {
				isAiming = true;
				
				vec2 playerCenterReversed = new vec2(-playerCenterX, -playerCenterY);
				vec2 mousePos = new vec2(mouseX, mouseY);
				
				vec2 direction = playerCenterReversed.add(mousePos);
				shootDirection = direction.getNormalized();
			}

			if(MouseManager.buttonState[MouseEvent.BUTTON1] && isAiming && canShoot) {
				canShoot = false;
				
				Bullet bullet = new Bullet(playerCenterX-Bullet.BULLET_SIZE/2, playerCenterY-Bullet.BULLET_SIZE/2,
						shootDirection, game, OBJECTS.Bullet);
				bullets.add(bullet);
				
				SoundEffects.shootSound.play();
			}
		}else {
			float playerCenterX = ServerStateManager.currentServerState.clientPosition.getX() + this.width/2;
			float playerCenterY = ServerStateManager.currentServerState.clientPosition.getY() + this.height/2;
			float mouseX = MouseManager.mouseX;
			float mouseY = MouseManager.mouseY;
			float dxSquared = (playerCenterX - mouseX)*(playerCenterX - mouseX);
			float dySquared = (playerCenterY - mouseY)*(playerCenterY - mouseY);
			
			float distance = (float) Math.sqrt(dxSquared + dySquared);
			if(distance < shootRadius) {
				isAiming = true;
				
				vec2 playerCenterReversed = new vec2(-playerCenterX, -playerCenterY);
				vec2 mousePos = new vec2(mouseX, mouseY);
				
				vec2 direction = playerCenterReversed.add(mousePos);
				shootDirection = direction.getNormalized();
			}

			if(MouseManager.buttonState[MouseEvent.BUTTON1] && canShoot && isAiming) {
				canShoot = false;
				
				shootDirection.printVector("Shoot Direction Packet sent!. Shoot Direction: ");
				Client.sendClientShootPacket(shootDirection);
				
				SoundEffects.shootSound.play();
			}
		}
		
		if(!canShoot) {
			shootTimer += game.getDeltaTime();
			if(shootTimer > 0.5) { // 0.5 seconds
				canShoot = true;
				shootTimer = 0.0;
			}
		}
		
		// Update the bullets
		if(GeneralInfo.isServer) {
			// Update the server's bullets. The client's bullet is updated from the AnotherPlayer class.
			for(int i = 0;  i < bullets.size(); ) {
				bullets.get(i).update();
				
				if(bullets.get(i).isOutOfBounds()) {
					bullets.remove(i);
					continue;
				}
				i++;
			}
			if(Server.clientPlayer != null) {
				Server.updateServerState(this);
			}
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		if(GeneralInfo.isServer) {
			g2d.setColor(DefaultTheme.warmWhite);
			g2d.fillRect((int)this.x, (int)this.y, (int)this.width, (int)this.height);

			if(isAiming) {
				g2d.drawLine((int)(this.x + this.width/2), (int)(this.y + this.height/2), MouseManager.mouseX, MouseManager.mouseY);
			}
			
			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).render(g2d);
			}
			
			if(Server.clientPlayer != null) {
				Server.clientPlayer.render(g2d);
				for(int i = 0; i < AnotherPlayer.bullets.size(); i++) {
					AnotherPlayer.bullets.get(i).render(g2d);
				}
			}
			// Server's name
			g2d.setColor(new Color(1.0f, 1.0f,0.f, 1.0f));
			g2d.setFont(DefaultTheme.defaultSmallFont);
			g2d.drawString(name, this.x, this.y-5);
		}else {
			ServerState currentState = ServerStateManager.currentServerState;
			
			g2d.setColor(DefaultTheme.warmWhite);
			g2d.fillRect((int)currentState.clientPosition.getX(),
					(int)currentState.clientPosition.getY(), (int)this.width, (int)this.height);
			
			g2d.fillRect((int)currentState.serverPosition.getX(),
					(int)currentState.serverPosition.getY(), (int)this.width, (int)this.height);
			
			if(isAiming) {
				g2d.drawLine((int)(currentState.clientPosition.getX() + this.width/2), 
						(int)(currentState.clientPosition.getY() + this.height/2), MouseManager.mouseX, MouseManager.mouseY);
			}
		
			for(int i = 0; i < currentState.bulletPositions.size(); i++) {
				vec2 bulletPos = currentState.bulletPositions.get(i);
				
				g2d.setColor(new Color(0.0f, 1.0f, 0.0f, 1.0f));
				g2d.fillRect((int)bulletPos.getX(), (int)bulletPos.getY(), 10, 10);
			}
			
			// Client's name
			g2d.setColor(new Color(1.0f, 1.0f,0.f, 1.0f));
			g2d.setFont(DefaultTheme.defaultSmallFont);
			g2d.drawString(name, currentState.clientPosition.getX(), currentState.clientPosition.getY()-5);

			// Server's name
			g2d.setColor(new Color(1.0f, 1.0f,0.f, 1.0f));
			g2d.setFont(DefaultTheme.defaultSmallFont);
			g2d.drawString(ServerInfo.serverName, currentState.serverPosition.getX(), currentState.serverPosition.getY()-5);
		}
	}

	public static void setName(String name) {
		Player.name = name;
	}
	
	public static String getName() {
		return name;
	}

}
