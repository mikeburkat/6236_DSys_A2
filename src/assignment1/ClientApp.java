package assignment1;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//----------------------------------------------------------------------------

public class ClientApp {

	private final int RMI_PORT = 2020;

	private AdminInterface adminNA = null;
	private PlayerInterface playerNA = null;
	private AdminInterface adminEU = null;
	private PlayerInterface playerEU = null;
	private AdminInterface adminAS = null;
	private PlayerInterface playerAS = null;

	// ------------------------------------------------------------------------

	public ClientApp() {
		initLinksToServers();
	}

	// ------------------------------------------------------------------------

	public String createPlayerAccount(String firstName, String lastName,
			int age, String userName, String password, String ipAddress) {

		AdminInterface server = findServer(ipAddress);
		try {
			server.createPlayerAccount(firstName, lastName, age, userName, password, ipAddress);
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

	// ------------------------------------------------------------------------

	public String playerSignIn(String userName, String password,
			String ipAddress) {
		
		AdminInterface server = findServer(ipAddress);
		try {
			server.playerSignIn(userName, password, ipAddress);
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

	// ------------------------------------------------------------------------

	public String playerSignOut(String userName, String ipAddress) {

		AdminInterface server = findServer(ipAddress);
		try {
			server.playerSignOut(userName, ipAddress);
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

	// ------------------------------------------------------------------------

	private AdminInterface findServer(String ipAddress) {
		AdminInterface server = null;

		String s = ipAddress.substring(0, 3);

		System.out.println(s);
		try {
			switch (s) {
			case "132":
				adminNA = (AdminInterface) Naming.lookup("//localhost:2020/NA");
				System.out.println(s);
				break;
			case "93.":
				System.out.println(s);
				break;
			case "182":
				System.out.println(s);
				break;
			};
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return server;
	}

	// ------------------------------------------------------------------------

	private void initLinksToServers() {
		try {
			adminNA = (AdminInterface) Naming.lookup("//localhost:2020/NA");
			playerNA = (PlayerInterface) adminNA;
			System.out.println(adminNA);

			adminEU = (AdminInterface) Naming.lookup("//localhost:2020/EU");
			playerEU = (PlayerInterface) adminEU;
			System.out.println(adminEU);

			adminAS = (AdminInterface) Naming.lookup("//localhost:2020/AS");
			playerAS = (PlayerInterface) adminAS;
			System.out.println(adminAS);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	// ------------------------------------------------------------------------

	public static void main(String args[]) {

		
		
		
	}
}
