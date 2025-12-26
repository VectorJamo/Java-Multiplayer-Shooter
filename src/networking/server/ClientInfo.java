package networking.server;

import java.net.InetAddress;

public class ClientInfo {
	public static volatile boolean hasEstablishedClient = false;
	public static volatile boolean hasClientAcknowledged = false;
	
	public static InetAddress clientAddress = null;
	public static String clientName = null;
}
