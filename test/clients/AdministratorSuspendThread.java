package clients;

import gameserver.GameServer;
import gameserver.GameServerHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import org.omg.CORBA.ORB;

//----------------------------------------------------------------------------
/**
 * This is the administrator client. This represents one administrator.
 * An administrator can get the player status.
 * 
 * @author Mike
 */
public class AdministratorSuspendThread implements Runnable {

	private String adminUserName;
	private String adminPassword;
	private String ipAddress;
	private String userNameToSuspend;
	
	// ------------------------------------------------------------------------
	
	public AdministratorSuspendThread(String aUserN, String aPass, String ip, String userSuspend) {
		adminUserName = aUserN;
		adminPassword = aPass;
		ipAddress = ip;
		userNameToSuspend = userSuspend;
	}
	
	// ------------------------------------------------------------------------
	
	public String getPlayerStatus() {
		GameServer server = findServer(ipAddress);
		System.out.println(adminUserName +" "+ adminPassword +" "+ ipAddress + " ");
		
		String s = server.getPlayerStatus(adminUserName, adminPassword, ipAddress);
		System.out.println(s +"\n");
		return s;
	}

	// ------------------------------------------------------------------------
	
	public boolean suspendAccount() {
		GameServer server = findServer(ipAddress);
		System.out.println(adminUserName +" "+ adminPassword +" "+ ipAddress + " "+ userNameToSuspend);
		
		String out = server.suspendAccount(adminUserName, adminPassword, ipAddress, userNameToSuspend);
		System.out.println(out +"\n");
		
		boolean result = out.equals("Success") ? true : false;
		return result;
	}
	
	// ------------------------------------------------------------------------

	private GameServer findServer(String ip) {
		GameServer server = null;
		String s = null;
		
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
				server = getServer("NA");
				break;
			case "93.":
				server = getServer("EU");
				break;
			case "182":
				server = getServer("AS");
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
	
	public GameServer getServer(String serverName) {
		
		String[] args = new String[1];
		ORB orb = ORB.init(args, null);
		BufferedReader br;
		String na = null;
		try {
			br = new BufferedReader(new FileReader(serverName+".txt"));
			na = br.readLine();
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		org.omg.CORBA.Object naObj = orb.string_to_object(na);
		GameServer serv = GameServerHelper.narrow(naObj);
		
		return serv;
	
	}
	
	// ------------------------------------------------------------------------
	
	/**
	 * This is only used for testing concurrency. It is called from the UnitTestClients
	 */
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			suspendAccount();
		}
	}
	
	// ------------------------------------------------------------------------

}
