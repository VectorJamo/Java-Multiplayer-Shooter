package networking.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import entities.AnotherPlayer;
import entities.ENTITIES;
import entities.Player;
import main.Game;
import math.vec2;
import networking.packets.*;
import networking.packets.statePackets.ClientStatePacket;
import networking.packets.statePackets.HealthStatePacket;
import networking.packets.statePackets.ServerStatePacket;
import networking.utilities.Ports;
import objects.Bullet;
import objects.OBJECTS;

// 1 Server - 1 client model
public class Server implements Runnable {
	
	private static Game game;
	
	private static DatagramSocket socket;
	private Thread thread;
	
	public static boolean isServerRunning = true;
	public static ClientInfo clientInfo = new ClientInfo();
	
	public static AnotherPlayer clientPlayer = null;
	
	// Packet timers
	private static double healthPacketTimer = 0.0;
	
	public Server(Game game) throws SocketException {
		Server.game = game;
		
		socket = new DatagramSocket(Ports.SERVER_PORT);
		System.out.println("Server is listening on port: " + Ports.SERVER_PORT + ".");
		
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while(isServerRunning) {
			byte[] receiveBuffer = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			
			try {
				socket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			byte packetType = receiveBuffer[0];
			switch(packetType) {
			case PacketType.CON_REQ:
				if(!clientInfo.hasEstablishedClient) {
					clientInfo.hasEstablishedClient = true;
					clientInfo.clientAddress = receivePacket.getAddress();
					System.out.println("CLIENT PORT: " + receivePacket.getPort());
					
					ConReqPacket conReqPacket = ConReqPacket.getPacketDeserialized(receiveBuffer);
					clientInfo.clientName = conReqPacket.getClientName();

					System.out.println("New client discovered. Client name: " + clientInfo.clientName);
					
					// Create the client player instance
					clientPlayer = new AnotherPlayer(200.0f, 200.0f, 50.0f, 50.0f, game, ENTITIES.Player);
				}
				break;
			case PacketType.CLIENT_STATE:
				if(clientInfo.hasEstablishedClient && !clientInfo.hasClientAcknowledged) {
					clientInfo.hasClientAcknowledged = true;
				}
				
				ClientStatePacket statePacket = ClientStatePacket.deserializePacket(receiveBuffer);
				handleClientStatePacket(statePacket);
				break;
			case PacketType.CLIENT_SHOOT:
				handleClientShootPacket(receiveBuffer);
				break;
			}
		}
	}
	
	public static void sendAckPacketToClient() throws IOException {
		ServerAckPacket packet = new ServerAckPacket(Player.getName());
		byte[] packetBuffer = packet.getPacketSerialized();
		
		DatagramPacket ackPacket = new DatagramPacket(packetBuffer, packetBuffer.length, clientInfo.clientAddress, Ports.CLIENT_PORT);
		socket.send(ackPacket);
	}
	
	public static void handleClientStatePacket(ClientStatePacket statePacket) {
		boolean moveUp = statePacket.moveUp == 1 ? true: false;
		boolean moveDown = statePacket.moveDown == 1 ? true: false;
		boolean moveLeft = statePacket.moveLeft == 1 ? true: false;
		boolean moveRight = statePacket.moveRight == 1 ? true: false;
		
		float currentClientX = clientPlayer.getX();
		float currentClientY = clientPlayer.getY();
		
		if(moveUp) {
			currentClientY -= Player.playerSpeed * game.getDeltaTime();
		}
		if(moveDown) {
			currentClientY += Player.playerSpeed * game.getDeltaTime();
		}
		if(moveLeft) {
			currentClientX -= Player.playerSpeed * game.getDeltaTime();
		}
		if(moveRight) {
			currentClientX += Player.playerSpeed * game.getDeltaTime();
		}
		
		clientPlayer.setX(currentClientX);
		clientPlayer.setY(currentClientY);
	}
	
	public void handleClientShootPacket(byte[] buffer) {
		ClientShootPacket packet = ClientShootPacket.getDeserialized(buffer);
		
		int bulletX = (int)clientPlayer.getX() + (int)clientPlayer.getWidth()/2;
		int bulletY = (int)clientPlayer.getY() + (int)clientPlayer.getHeight()/2;
		
		packet.shootDirection.printVector("Client Shoot direction:");
		clientPlayer.bullets.add(new Bullet(bulletX, bulletY, packet.shootDirection, game, OBJECTS.Bullet));
	}
	
	public static void sendServerState() throws IOException {
		byte[] statePacketSerialized = ServerStatePacket.getPacketSerialized();
		
		DatagramPacket packet = new DatagramPacket(statePacketSerialized, statePacketSerialized.length, clientInfo.clientAddress, Ports.CLIENT_PORT);
		socket.send(packet);
	}
	
	public static void sendHealthState() throws IOException {
		healthPacketTimer += game.getDeltaTime();
		if(healthPacketTimer > 0.1) { // 0.1 seconds
			healthPacketTimer = 0.0;
			
			HealthStatePacket healthState = new HealthStatePacket();
			healthState.clientHealth = clientPlayer.playerHealth;
			healthState.serverHealth = Player.playerHealth;
			
			byte[] healthStatePacketSerialized = healthState.getPacketSerialized();
			
			DatagramPacket packet = new DatagramPacket(healthStatePacketSerialized, healthStatePacketSerialized.length, clientInfo.clientAddress, Ports.CLIENT_PORT);
			socket.send(packet);
		}
	}
	
	public static void update(){
		
		if(clientInfo.hasEstablishedClient && !clientInfo.hasClientAcknowledged) {
			try {
				sendAckPacketToClient();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(clientPlayer != null) {
			clientPlayer.update();
		}
		
		
		if(clientInfo.hasClientAcknowledged) {
			try {
				// Send the server state packet
				sendServerState();
				
				sendHealthState();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void updateServerState(Player player) {
		ServerStatePacket.clientPosition.setX(clientPlayer.getX());
		ServerStatePacket.clientPosition.setY(clientPlayer.getY());
		ServerStatePacket.serverPosition.setX(player.getX());
		ServerStatePacket.serverPosition.setY(player.getY());
		ServerStatePacket.numClientBullets = AnotherPlayer.bullets.size();
		ServerStatePacket.numServerBullets = player.bullets.size();
		
		ServerStatePacket.bulletPositions.clear();
		for(int i = 0; i < ServerStatePacket.numClientBullets; i++) {
			ServerStatePacket.bulletPositions.add(new vec2(AnotherPlayer.bullets.get(i).getX(), 
					AnotherPlayer.bullets.get(i).getY()));
		}
		for(int i = 0; i < ServerStatePacket.numServerBullets; i++) {
			ServerStatePacket.bulletPositions.add(new vec2(player.bullets.get(i).getX(), 
					player.bullets.get(i).getY()));
		}
	}
}