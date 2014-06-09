package assignment1;

/**
 * This is the initialization of the Europe server. It's name is set to "EU"
 * and it uses the protected fields that are in the GameServer class to set
 * its RMI port, its UDP port, and both client ports. 
 * 
 * @author Mike
 */
public class EuropeServer extends GameServer {

	private final static String SERVER_NAME2 = "EU";
	
	
	public EuropeServer() {
		super(SERVER_NAME2, RMI_PORT, 
				EUROPE_UDP_PORT, 
				NORTH_AMERICA_UDP_PORT, ASIA_UDP_PORT);
		
	}
}
