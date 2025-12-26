package networking.packets.statePackets;

import java.nio.ByteBuffer;

import math.vec2;
import networking.packets.PacketType;

public class ClientStatePacket {
	public static byte packetType = PacketType.CLIENT_STATE;
	
	public byte moveLeft, moveRight, moveUp, moveDown; 
	
	public static ClientStatePacket deserializePacket(byte[] buffer) {
		ClientStatePacket statePacket = new ClientStatePacket();
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.get();
		statePacket.moveLeft = byteBuffer.get();
		statePacket.moveRight = byteBuffer.get();
		statePacket.moveUp = byteBuffer.get();
		statePacket.moveDown = byteBuffer.get();
		
		return statePacket;
	}
	
	public byte[] getPacketSerialized() {
		byte[] buffer = new byte[getMaxPacketSize()];
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(getMaxPacketSize());
		byteBuffer.put(packetType);
		byteBuffer.put(moveLeft);
		byteBuffer.put(moveRight);
		byteBuffer.put(moveUp);
		byteBuffer.put(moveDown);
		
		byteBuffer.flip();
		
		byteBuffer.get(buffer);
		
		return buffer;
	}
	
	public static int getMaxPacketSize() {
		return (Byte.BYTES + Byte.BYTES*4);
	}
}
