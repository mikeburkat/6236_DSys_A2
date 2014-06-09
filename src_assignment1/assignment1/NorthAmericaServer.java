package assignment1;

/**
 * This is the initialization of the NorthAmerica server. It's name is set to "NA"
 * and it uses the protected fields that are in the GameServer class to set
 * its RMI port, its UDP port, and both client ports. 
 * 
 * @author Mike
 */
public class NorthAmericaServer extends GameServer {

	private final static String SERVER_NAME1 = "NA";

	public NorthAmericaServer() {
		super(SERVER_NAME1, RMI_PORT, 
				NORTH_AMERICA_UDP_PORT,
				EUROPE_UDP_PORT, ASIA_UDP_PORT);
	}
	
}
