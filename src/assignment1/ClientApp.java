package assignment1;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class ClientApp {
	

	public static void main(String args[]) {

		NorthAmericaServer nas = new NorthAmericaServer();
		EuropeServer eus = new EuropeServer();
		AsiaServer ass = new AsiaServer();
		
		AdminInterface adminNA = null;
		PlayerInterface playerNA = null;
		
		AdminInterface adminEU = null;
		PlayerInterface playerEU = null;
		
		AdminInterface adminAS = null;
		PlayerInterface playerAS = null;
		
		try {
			adminNA = (AdminInterface) Naming.lookup("rmi://localhost:" 
					+ GameServer.NORTH_AMERICA_RMI_PORT + "/NA");
			playerNA = (PlayerInterface) adminNA;
			
			adminEU = (AdminInterface) Naming.lookup("rmi://localhost:" 
					+ GameServer.EUROPE_RMI_PORT + "/EU");
			playerEU = (PlayerInterface) adminNA;
			
			adminAS = (AdminInterface) Naming.lookup("rmi://localhost:" 
					+ GameServer.ASIA_RMI_PORT + "/AS");
			playerAS = (PlayerInterface) adminNA;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.out.println(playerNA.createPlayerAccount("mike", "burkat", 26,
					"mikebk", "mmm", "192.xxx"));
			
			System.out.println( adminNA.getPlayerStatus("admin", "admin", "3") );
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
}
