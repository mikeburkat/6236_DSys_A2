package assignment1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * This is only used to set up the game servers, first it creates a registry and
 * then each server binds to the registry under it's server name.
 * 
 * @author Mike
 */
public class RunServers {
	
	public static void main (String args[]) {
	
		Registry r = null;
		
		try {
			r = LocateRegistry.createRegistry(2020);
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		NorthAmericaServer nas = new NorthAmericaServer();
		EuropeServer eus = new EuropeServer();
		AsiaServer ass = new AsiaServer();
		
	}
	

}
