package client;

import gameserver.GameServer;
import gameserver.GameServerHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.regex.Pattern;

import org.omg.CORBA.ORB;

//----------------------------------------------------------------------------
/**
 * This is the player client. This represents one player. A player can create
 * its self on the server, sign in and sign out, by calling the appropriate
 * methods.
 * 
 * @author Mike
 */
public class PlayerClient implements Runnable {

	private String firstName;
	private String lastName;
	private short age;
	private String userName;
	private String password;
	private String ipAddress;

	// ------------------------------------------------------------------------

	public PlayerClient() {
	}

	// ------------------------------------------------------------------------

	public PlayerClient(String fName, String lName, short a, String userN,
			String pass, String ip) {
		firstName = fName;
		lastName = lName;
		age = a;
		userName = userN;
		password = pass;
		ipAddress = ip;
	}

	// ------------------------------------------------------------------------

	public boolean createPlayerAccount() {
		System.out.println(userName + " " + password + " " + ipAddress + " ");
		GameServer server = findServer(ipAddress);
		System.out.println(userName + " " + password + " " + ipAddress + " ");

		String out = server.createPlayerAccount(firstName, lastName, age,
				userName, password, ipAddress);
		System.out.println(out + "\n");
		boolean result = out.equals("Created") ? true : false;
		return result;

	}

	// ------------------------------------------------------------------------

	public boolean playerSignIn() {
		String out = "";
		GameServer server = findServer(ipAddress);
		System.out.println(userName + " " + password + " " + ipAddress + " ");

		out = server.playerSignIn(userName, password, ipAddress);
		System.out.println(out + "\n");
		boolean result = out.equals("Signed In") ? true : false;
		return result;

	}

	// ------------------------------------------------------------------------

	public boolean playerSignOut() {
		GameServer server = findServer(ipAddress);
		System.out.println(userName + " " + ipAddress + " ");

		String out = server.playerSignOut(userName, ipAddress);
		System.out.println(out + "\n");
		boolean result = out.equals("Signed Out") ? true : false;
		return result;

	}

	// ------------------------------------------------------------------------

	private GameServer findServer(String ip) {
		GameServer server = null;
		String s = null;
		
		String[] args = new String[1];
		ORB orb = ORB.init(args, null);
		BufferedReader br;
		String na = null;
		try {
			br = new BufferedReader(new FileReader("NA.txt"));

			na = br.readLine();
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(na);

		org.omg.CORBA.Object naObj = orb.string_to_object(na);
		System.out.println("here");
		GameServer naServer = GameServerHelper.narrow(naObj);

		boolean matches = Pattern.matches(
				"^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$", ip);
		if (!matches) {
			System.out.println("Invalid IP address: " + ip);
			return null;
		}

		s = ip.substring(0, 3);
		try {
			switch (s) {
			case "132":
				server = naServer;
				break;
			case "93.":
				server = naServer;
				break;
			case "182":
				server = naServer;
				break;
			default:
				System.out.println("Invalid IP address: " + ip);
				break;
			}
			;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return server;
	}

	// ------------------------------------------------------------------------
	/**
	 * This is only used for testing concurrency. It is called from the
	 * UnitTestClients
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
