package assignment1;

/**
 * This is the initialization of the Asia server. It's name is set to "AS"
 * and it uses the protected fields that are in the GameServer class to set
 * its RMI port, its UDP port, and both client ports. 
 * 
 * @author Mike
 */
public class AsiaServer extends GameServer {

	private final static String SERVER_NAME3 = "AS";
	
	public AsiaServer() {
		super(SERVER_NAME3, RMI_PORT, 
				ASIA_UDP_PORT, 
				NORTH_AMERICA_UDP_PORT, EUROPE_UDP_PORT);
		
	}
	
}
