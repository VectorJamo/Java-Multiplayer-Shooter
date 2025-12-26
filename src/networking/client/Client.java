package networking.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import entities.AnotherPlayer;
import entities.ClientPlayerState;
import entities.Player;
import math.vec2;
import networking.packets.*;
import networking.packets.statePackets.ClientStatePacket;
import networking.packets.statePackets.HealthStatePacket;
import networking.packets.statePackets.ServerStatePacket;
import networking.utilities.*;

public class Client implements Runnable {
	private static DatagramSocket socket;
	private Thread thread;
	
	public static ServerInfo serverInfo = new ServerInfo();
	
	public Client() throws SocketException {
		socket = new DatagramSocket(Ports.CLIENT_PORT);
	}

	@Override
	public void run() {
		while(true) {
			byte[] receiveBuffer = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			
			try {
				socket.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte packetType = receiveBuffer[0];
			
			switch(packetType) {
			case PacketType.ACK:
				if(!serverInfo.hasServerAcked) {
					serverInfo.hasServerAcked = true;
					
					ServerAckPacket ackPacket = ServerAckPacket.getPacketDeserialized(receiveBuffer);
					serverInfo.serverName = ackPacket.getServerName();
					
					System.out.println("Connection with the server has initiated!");
					System.out.println("Server name: " + serverInfo.serverName);
				}
				break;
			case PacketType.SERVER_STATE:
				ServerState serverState = ServerStatePacket.deserializePacket(receiveBuffer);
				handleServerStatePacket(serverState);
				break;
			case PacketType.HEALTH_STATE:
				HealthStatePacket healthPacket = HealthStatePacket.deserializePacket(receiveBuffer);
				handleHealthStatePacket(healthPacket);
				break;
			}
		}
	}
	
	// A blocking function.
	public void initiateConnectionWithServer(String clientName, String serverIP) throws IOException {
		thread = new Thread(this);
		thread.start();
		
		serverInfo.serverAddress = InetAddress.getByName(serverIP);
		
		ConReqPacket conReqPacket = new ConReqPacket(clientName);
		byte[] buffer = conReqPacket.getPacketSerialized();
		
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverInfo.serverAddress, Ports.SERVER_PORT);
		
		while(!serverInfo.hasServerAcked) {
			socket.send(packet);
		}
		System.out.println("DONE!");
	}
	
	public static void handleServerStatePacket(ServerState serverState) {
		ServerStateManager.currentServerState = serverState;
	}
	
	public static void sendClientStatePacket(ClientStatePacket statePacket) throws IOException {
		byte[] statePacketSerialized = statePacket.getPacketSerialized();
		DatagramPacket packet = new DatagramPacket(statePacketSerialized, statePacketSerialized.length, 
				serverInfo.serverAddress, Ports.SERVER_PORT);
		
		socket.send(packet);
	}
	public static void handleHealthStatePacket(HealthStatePacket healthPacket) {
		HealthStates.clientHealth = healthPacket.clientHealth;
		HealthStates.serverHealth = healthPacket.serverHealth;
	}
	
	public static void sendClientShootPacket(vec2 shootDirection) {
		if(serverInfo.hasServerAcked) {
			ClientShootPacket shootPacket = new ClientShootPacket(shootDirection);
			byte[] shootPacketSerialized = shootPacket.getPacketSerialized();

			DatagramPacket packet = new DatagramPacket(shootPacketSerialized, shootPacketSerialized.length, 
					serverInfo.serverAddress, Ports.SERVER_PORT);
			
			try {
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void update() {
		if(serverInfo.hasServerAcked) {
			ClientStatePacket statePacket = new ClientStatePacket();
			
			statePacket.moveLeft = (byte)(ClientPlayerState.moveLeft? 1 : 0);
			statePacket.moveRight = (byte)(ClientPlayerState.moveRight? 1 : 0);
			statePacket.moveUp = (byte)(ClientPlayerState.moveUp? 1 : 0);
			statePacket.moveDown = (byte)(ClientPlayerState.moveDown? 1 : 0);
			
			try {
				sendClientStatePacket(statePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}