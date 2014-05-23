package assignment1;

public class NorthAmericaServer extends GameServer {

	private final static String SERVER_NAME1 = "NA";

	public NorthAmericaServer() {
		super(SERVER_NAME1, NORTH_AMERICA_RMI_PORT, 
				NORTH_AMERICA_UDP_PORT,
				EUROPE_UDP_PORT, ASIA_UDP_PORT);
	}
	
}
