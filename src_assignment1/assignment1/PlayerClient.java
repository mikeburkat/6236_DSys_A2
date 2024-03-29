package assignment1;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.regex.Pattern;

//----------------------------------------------------------------------------
/**
* This is the player client. This represents one player.
* A player can create its self on the server, sign in and sign out,
* by calling the appropriate methods.
* 
* @author Mike
*/
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

	public boolean createPlayerAccount(String fName, String lName,
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
	
	public boolean createPlayerAccount() {
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

	public boolean playerSignIn(String uName, String pass, String ip) {
		userName = uName;
		password = pass;
		ipAddress = ip;
		return playerSignIn();
	}
	
	// ------------------------------------------------------------------------
	
	public boolean playerSignIn() {
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

	public boolean playerSignOut(String uName, String ip) {
		userName = uName;
		ipAddress = ip;
		return playerSignOut();
	}
	
	// ------------------------------------------------------------------------
	
	public boolean playerSignOut() {
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

	private PlayerInterface findServer(String ip) {
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
	/**
	 * This is only used for testing concurrency. It is called from the UnitTestClients
	 */
	@Override
	public void run() {
		createPlayerAccount();
		
		for (int i = 0; i < 10; i++) {
			playerSignIn();
			playerSignOut();
		}
		
	}
	
	// ------------------------------------------------------------------------

}
