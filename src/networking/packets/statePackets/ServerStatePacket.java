package networking.packets.statePackets;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import math.vec2;
import networking.client.ServerState;
import networking.packets.PacketType;

public class ServerStatePacket {
	public static byte packetType = PacketType.SERVER_STATE;
	
	public static vec2 clientPosition = new vec2(0.0f, 0.0f);
	public static vec2 serverPosition = new vec2(0.0f, 0.0f);
	public static int numClientBullets = 0;
	public static int numServerBullets = 0;
	public static ArrayList<vec2> bulletPositions = new ArrayList<vec2>();
	
	public static ServerState deserializePacket(byte[] buffer) {
		ServerState statePacket = new ServerState();
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.get();
		statePacket.clientPosition.setX(byteBuffer.getFloat());
		statePacket.clientPosition.setY(byteBuffer.getFloat());
		statePacket.serverPosition.setX(byteBuffer.getFloat());
		statePacket.serverPosition.setY(byteBuffer.getFloat());
		
		statePacket.numClientBullets = byteBuffer.getInt();
		statePacket.numServerBullets = byteBuffer.getInt();
		
		for(int i = 0; i < statePacket.numClientBullets; i++) {
			vec2 bulletPosition = new vec2(byteBuffer.getFloat(), byteBuffer.getFloat());	
			statePacket.bulletPositions.add(bulletPosition);
		}
		for(int i = 0; i < statePacket.numServerBullets; i++) {
			vec2 bulletPosition = new vec2(byteBuffer.getFloat(), byteBuffer.getFloat());
			statePacket.bulletPositions.add(bulletPosition);
		}
		
		return statePacket;

	}
	
	public static byte[] getPacketSerialized() {
		byte[] buffer = new byte[getMaxPacketSize()];
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(getMaxPacketSize());
		byteBuffer.put(packetType);
		byteBuffer.putFloat(clientPosition.getX());
		byteBuffer.putFloat(clientPosition.getY());
		byteBuffer.putFloat(serverPosition.getX());
		byteBuffer.putFloat(serverPosition.getY());

		byteBuffer.putInt(numClientBullets);
		byteBuffer.putInt(numServerBullets);
		for(int i = 0; i < bulletPositions.size(); i++) {
			byteBuffer.putFloat(bulletPositions.get(i).getX());
			byteBuffer.putFloat(bulletPositions.get(i).getY());
		}
		
		byteBuffer.flip();
		
		byteBuffer.get(buffer);

		return buffer;
	}
	
	public static int getMaxPacketSize() {
		return (Byte.BYTES + vec2.getSizeInBytes()*2 + Integer.BYTES*2 + numClientBullets*vec2.getSizeInBytes() +
				numServerBullets*vec2.getSizeInBytes());
	}
}
