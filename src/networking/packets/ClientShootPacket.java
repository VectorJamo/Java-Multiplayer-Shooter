package networking.packets;

import java.nio.ByteBuffer;

import math.vec2;

public class ClientShootPacket {
	public static final byte packetType = PacketType.CLIENT_SHOOT;
	public vec2 shootDirection;
	
	public ClientShootPacket(vec2 shootDirection) {
		this.shootDirection = shootDirection;
	}
	
	public byte[] getPacketSerialized() {
		int packetSize = getPacketSize();
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(packetSize);
		byteBuffer.put(packetType);
		byteBuffer.putFloat(shootDirection.getX());
		byteBuffer.putFloat(shootDirection.getY());
		byteBuffer.flip();
		
		byte[] buffer = new byte[packetSize];
		byteBuffer.get(buffer);
		
		return buffer;
	}
	
	public static ClientShootPacket getDeserialized(byte[] buffer) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.get(); // Packet type
		
		vec2 clientShootDirection = new vec2(byteBuffer.getFloat(), byteBuffer.getFloat());
		ClientShootPacket clientShootPacket = new ClientShootPacket(clientShootDirection);
		
		return clientShootPacket;
	}
	
	public static int getPacketSize() {
		return (Byte.BYTES + vec2.getSizeInBytes());
	}
}
