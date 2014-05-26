package assignment1;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//----------------------------------------------------------------------------

public class ClientApp {

	private final int RMI_PORT = 2020;

	// ------------------------------------------------------------------------

	public ClientApp() {
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

	public String playerSignIn(String userName, String password, String ipAddress) {
		String out = "";
		AdminInterface server = findServer(ipAddress);
		System.out.println(userName +" "+ password +" "+ ipAddress);
		try {
			System.out.println(userName +" "+ password +" "+ ipAddress);
			out = server.playerSignIn(userName, password, ipAddress);
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println(out);
		
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
	
	public String getPlayerStatus(String adminUserName, String adminPassword, String ipAddress) {
		AdminInterface server = findServer(ipAddress);
		try {
			server.getPlayerStatus(adminUserName, adminPassword, ipAddress);
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
				server = (AdminInterface) Naming.lookup("//localhost:2020/NA");
				System.out.println(s);
				break;
			case "93.":
				server = (AdminInterface) Naming.lookup("//localhost:2020/EU");
				System.out.println(s);
				break;
			case "182":
				server = (AdminInterface) Naming.lookup("//localhost:2020/AS");
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

	public static void main(String args[]) {

		
		
		
	}
}
