package assignment1;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class EuropeServer extends GameServer {

	private final static String SERVER_NAME2 = "EU";
	
	
	public EuropeServer() {
		super(SERVER_NAME2, EUROPE_RMI_PORT, 
				EUROPE_UDP_PORT, 
				NORTH_AMERICA_UDP_PORT, ASIA_UDP_PORT);
		
		try {
			initRMIserver();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initRMIserver() throws Exception {
		Remote obj = UnicastRemoteObject.exportObject(this, rmiPort);
		Registry r = LocateRegistry.createRegistry(rmiPort);
		r.rebind(serverName, obj);
		System.out.println("RMI Server is up in: " + serverName + " on port: " + rmiPort);
	}
}
