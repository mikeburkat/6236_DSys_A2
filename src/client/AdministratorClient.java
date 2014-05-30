package client;

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
public class AdministratorClient {

	private String adminUserName;
	private String adminPassword;
	private String ipAddress;
	
	// ------------------------------------------------------------------------
	
	public AdministratorClient(String aUserN, String aPass, String ip) {
		adminUserName = aUserN;
		adminPassword = aPass;
		ipAddress = ip;
	}
	
	// ------------------------------------------------------------------------
	
	public String getPlayerStatus(String aUserN, String aPass, String ip) {
		adminUserName = aUserN;
		adminPassword = aPass;
		ipAddress = ip;
		return getPlayerStatus();
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

	private GameServer findServer(String ip) {
		GameServer server = null;
		String s = null;
		
		GameServer naServer = getServer("NA");
		GameServer euServer = getServer("EU");
		GameServer asServer = getServer("AS");
		
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
				server = euServer;
				break;
			case "182":
				server = asServer;
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

}
