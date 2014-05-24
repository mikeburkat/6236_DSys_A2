package assignment1;


public class EuropeServer extends GameServer {

	private final static String SERVER_NAME2 = "EU";
	
	
	public EuropeServer() {
		super(SERVER_NAME2, RMI_PORT, 
				EUROPE_UDP_PORT, 
				NORTH_AMERICA_UDP_PORT, ASIA_UDP_PORT);
		
	}
}
