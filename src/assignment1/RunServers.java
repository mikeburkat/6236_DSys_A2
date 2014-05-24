package assignment1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
