package networking.client;

import java.net.InetAddress;

public class ServerInfo {
	public static volatile boolean hasServerAcked = false;
	public static InetAddress serverAddress = null;
	
	public static String serverName = null;
}
