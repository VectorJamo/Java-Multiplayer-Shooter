package networking.packets;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ServerAckPacket {
	public static final byte packetType = PacketType.ACK;

	private int serverNameSize;
	private String serverName;
	
	public ServerAckPacket(String serverName) {
		this.serverName = serverName;
		serverNameSize = serverName.getBytes().length;
	}
	
	public byte[] getPacketSerialized() {
		int packetSize = Byte.BYTES + Integer.BYTES + serverNameSize;
		
		ByteBuffer packetBuffer = ByteBuffer.allocate(packetSize);
		packetBuffer.put(packetType);
		packetBuffer.putInt(serverNameSize);
		
		byte[] stringBytes = serverName.getBytes(StandardCharsets.UTF_8);
		packetBuffer.put(stringBytes);
		
		packetBuffer.flip();
		
		byte[] buffer = new byte[packetSize];
		packetBuffer.get(buffer);
		
		return buffer;
	}
	
	public static ServerAckPacket getPacketDeserialized(byte[] buffer) {
		ByteBuffer packetBuffer = ByteBuffer.wrap(buffer);
		
		byte packetType = packetBuffer.get();
		int clientNameSize = packetBuffer.getInt();
		
		byte[] stringBuffer = new byte[clientNameSize];
		packetBuffer.get(stringBuffer);
		
		String clientName = new String(stringBuffer, StandardCharsets.UTF_8);
		
		return new ServerAckPacket(clientName);
	}
	
	public String getServerName() {
		return serverName;
	}

}
