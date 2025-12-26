package networking.packets.statePackets;

import java.nio.ByteBuffer;

import networking.packets.PacketType;

public class HealthStatePacket {
	
	public static final byte packetType = PacketType.HEALTH_STATE;
	
	public int clientHealth, serverHealth;
	
	public byte[] getPacketSerialized() {
		ByteBuffer byteBuffer = ByteBuffer.allocate(getMaxPacketSize());
		byteBuffer.put(packetType);
		byteBuffer.putInt(clientHealth);
		byteBuffer.putInt(serverHealth);
		
		byteBuffer.flip();
		
		byte[] buffer = new byte[getMaxPacketSize()];
		byteBuffer.get(buffer);
		
		return buffer;
	}
	
	public static HealthStatePacket deserializePacket(byte[] buffer) {
		HealthStatePacket healthPacket = new HealthStatePacket();

		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.get();
		healthPacket.clientHealth = byteBuffer.getInt();
		healthPacket.serverHealth = byteBuffer.getInt();
		
		return healthPacket;
	}
	
	public static int getMaxPacketSize() {
		return (Byte.BYTES + Integer.BYTES*2);
	}
}
