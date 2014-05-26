package assignment1;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.regex.Pattern;

//----------------------------------------------------------------------------

public class PlayerClient implements Runnable {

	private final int RMI_PORT = 2020;
	private String firstName;
	private String lastName;
	private int age;
	private String userName;
	private String password;
	private String ipAddress;

	// ------------------------------------------------------------------------

	public PlayerClient() {
	}
	
	// ------------------------------------------------------------------------
	
	public PlayerClient(String fName, String lName,
			int a, String userN, String pass, String ip) {
		firstName = fName;
		lastName = lName;
		age = a;
		userName = userN;
		password = pass;
		ipAddress = ip;
	}

	// ------------------------------------------------------------------------

	synchronized public boolean createPlayerAccount(String fName, String lName,
			int a, String userN, String pass, String ip) {
		
		firstName = fName;
		lastName = lName;
		age = a;
		userName = userN;
		password = pass;
		ipAddress = ip;
		return createPlayerAccount();
	}
	
	// ------------------------------------------------------------------------
	
	synchronized public boolean createPlayerAccount() {
		PlayerInterface server = findServer(ipAddress);
		System.out.println(userName +" "+ password +" "+ ipAddress + " ");
		try {
			String out = server.createPlayerAccount(firstName, lastName, age, userName, password, ipAddress);
			System.out.println(out +"\n");
			boolean result = out.equals("Created") ? true : false;
			return result;
		} catch (RemoteException e) {
			e.printStackTrace();
			System.out.println(e.getMessage() +"\n");
			return false;
		}
	}

	// ------------------------------------------------------------------------

	synchronized public boolean playerSignIn(String uName, String pass, String ip) {
		userName = uName;
		password = pass;
		ipAddress = ip;
		return playerSignIn();
	}
	
	// ------------------------------------------------------------------------
	
	synchronized public boolean playerSignIn() {
		String out = "";
		PlayerInterface server = findServer(ipAddress);
		System.out.println(userName +" "+ password +" "+ ipAddress + " ");
		try {
			out = server.playerSignIn(userName, password, ipAddress);
			System.out.println(out +"\n");
			boolean result = out.equals("Signed In") ? true : false;
			return result;
		} catch (RemoteException e) {
			System.out.println(e.getMessage() +"\n");
			e.printStackTrace();
			return false;
		}
	}

	// ------------------------------------------------------------------------

	synchronized public boolean playerSignOut(String uName, String ip) {
		userName = uName;
		ipAddress = ip;
		return playerSignOut();
	}
	
	// ------------------------------------------------------------------------
	
	synchronized public boolean playerSignOut() {
		PlayerInterface server = findServer(ipAddress);
		System.out.println(userName + " " + ipAddress + " ");
		try {
			String out = server.playerSignOut(userName, ipAddress);
			System.out.println(out +"\n");
			boolean result = out.equals("Signed Out") ? true : false;
			return result;
		} catch (RemoteException e) {
			System.out.println(e.getMessage() +"\n");
			e.printStackTrace();
			return false;
		}
	}
	
	// ------------------------------------------------------------------------

	synchronized private PlayerInterface findServer(String ip) {
		PlayerInterface server = null;
		String s = null;

		boolean matches = Pattern.matches("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$", ip);
		if (!matches) {
			System.out.println("Invalid IP address: " + ip);
			return null;
		}
		
		s = ip.substring(0, 3);
		try {
			switch (s) {
			case "132":
				server = (PlayerInterface) Naming.lookup("//localhost:"+RMI_PORT+"/NA");
				break;
			case "93.":
				server = (PlayerInterface) Naming.lookup("//localhost:"+RMI_PORT+"/EU");
				break;
			case "182":
				server = (PlayerInterface) Naming.lookup("//localhost:"+RMI_PORT+"/AS");
				break;
			default:
				System.out.println("Invalid IP address: " + ip);
				break;
			};
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return server;
	}
	
	// ------------------------------------------------------------------------

	@Override
	public void run() {
		createPlayerAccount();
		
		for (int i = 0; i < 5; i++) {
			playerSignIn();
			playerSignOut();
		}
		
	}
	
	// ------------------------------------------------------------------------

}
