package assignment1;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AsiaServer extends GameServer {

	private final static String SERVER_NAME3 = "AS";
	
	
	public AsiaServer() {
		super(SERVER_NAME3, ASIA_RMI_PORT, 
				ASIA_UDP_PORT, 
				NORTH_AMERICA_UDP_PORT, EUROPE_UDP_PORT);
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
