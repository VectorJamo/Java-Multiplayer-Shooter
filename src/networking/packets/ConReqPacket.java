package networking.packets;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ConReqPacket {
	public static final byte packetType = PacketType.CON_REQ;

	private int clientNameSize;
	private String clientName;
	
	public ConReqPacket(String clientName) {
		this.clientName = clientName;
		clientNameSize = clientName.getBytes().length;
	}
	
	public byte[] getPacketSerialized() {
		int packetSize = Byte.BYTES + Integer.BYTES + clientNameSize;
		
		ByteBuffer packetBuffer = ByteBuffer.allocate(packetSize);
		packetBuffer.put(packetType);
		packetBuffer.putInt(clientNameSize);
		
		byte[] stringBytes = clientName.getBytes(StandardCharsets.UTF_8);
		packetBuffer.put(stringBytes);
		
		packetBuffer.flip();
		
		byte[] buffer = new byte[packetSize];
		packetBuffer.get(buffer);
		
		return buffer;
	}
	
	public static ConReqPacket getPacketDeserialized(byte[] buffer) {
		ByteBuffer packetBuffer = ByteBuffer.wrap(buffer);
		
		byte packetType = packetBuffer.get();
		int clientNameSize = packetBuffer.getInt();
		
		byte[] stringBuffer = new byte[clientNameSize];
		packetBuffer.get(stringBuffer);
		
		String clientName = new String(stringBuffer, StandardCharsets.UTF_8);
		
		return new ConReqPacket(clientName);
	}
	
	public String getClientName() {
		return clientName;
	}

}
