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
			System.out.println(adminNA);
			adminEU = (AdminInterface) Naming.lookup("rmi://localhost:" 
					+ GameServer.EUROPE_RMI_PORT + "/EU");
			playerEU = (PlayerInterface) adminNA;
			System.out.println(adminEU);
			
			adminAS = (AdminInterface) Naming.lookup("rmi://localhost:" 
					+ GameServer.ASIA_RMI_PORT + "/AS");
			playerAS = (PlayerInterface) adminNA;
			System.out.println(adminAS);
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		try {
			System.out.println(playerNA.createPlayerAccount("mike", "burkat", 26,
					"mikebk", "mmm", "192.xxx"));
			System.out.println(playerNA);
			
			System.out.println( adminNA.getPlayerStatus("admin", "admin", "3") );
			
			System.out.println(playerEU);
			System.out.println(playerEU.createPlayerAccount("mikebbb", "burkat", 26,
					"mikebk", "mmm", "192.xxx"));
			
			System.out.println(playerAS.createPlayerAccount("ebbb", "burkat", 26,
					"mikebk", "mmm", "192.xxx"));
			
			System.out.println( adminNA.getPlayerStatus("admin", "admin", "3") );
			
			System.out.println(playerNA.createPlayerAccount("mic", "burkat", 26,
					"mikebk", "mmm", "192.xxx"));
			
			System.out.println( adminNA.getPlayerStatus("admin", "admin", "3") );
			
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
}
