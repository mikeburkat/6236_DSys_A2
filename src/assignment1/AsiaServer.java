package assignment1;

public class AsiaServer extends GameServer {

	private final static String SERVER_NAME3 = "AS";
	
	
	public AsiaServer() {
		super(SERVER_NAME3, ASIA_RMI_PORT, 
				ASIA_UDP_PORT, 
				NORTH_AMERICA_UDP_PORT, EUROPE_UDP_PORT);
	}
	
}
