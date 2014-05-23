package assignment1;


import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;

	//------------------------------------------------------------------------

public class GameServer implements PlayerInterface, AdminInterface {
	
	//------------------------------------------------------------------------
	// Variables to quickly and manage ports and servers
	//------------------------------------------------------------------------
	public final static int NORTH_AMERICA_RMI_PORT = 2020;
	public final static int EUROPE_RMI_PORT = 2021;
	public final static int ASIA_RMI_PORT = 2022;
	
	public final static int NORTH_AMERICA_UDP_PORT = 2030;
	public final static int EUROPE_UDP_PORT = 2031;
	public final static int ASIA_UDP_PORT = 2032;
	
	//------------------------------------------------------------------------
	// Fields to easily set ports and run UDP server  and 
	// set the server name string when extending this class
	//------------------------------------------------------------------------
	private String serverName;
	private int rmiPort;
	private int UDPclientServer1Port;
	private int UDPclientServer2Port;
	private int UDPserverPort;
	private UDPserver udpS;
	private UDPclient udpC1;
	private UDPclient udpC2;
	
	
	//------------------------------------------------------------------------
	// hash table and other data
	private Hashtable<Character, ArrayList<PlayerData>> ht;
	private int playersOnline;
	private int playersOffline;
	private int totalPlayers;
	//------------------------------------------------------------------------
	
	// Constructor called by extender
	public GameServer( String sName, int rmiP, int usp, int uc1, int uc2) { 
		System.out.println("Creating game server: "+ sName 
							+ " RMIport: " + rmiP
							+ " UDPserver: " + usp 
							+ " UDPclient1: " + uc1 
							+ " UDPclient2: " + uc2);
		
		serverName = sName;
		rmiPort = rmiP;
		UDPserverPort = usp;
		UDPclientServer1Port = uc1;
		UDPclientServer2Port = uc2;
		
		playersOnline = 0;
		playersOffline = 0;
		totalPlayers = 0;
		
		
		initHashTable();
		initUDPclients();
		
		try {
			initRMIserver();
			System.out.println("Server is up");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		initUDPserver();
	}

	//------------------------------------------------------------------------
	@Override
	public String getPlayerStatus(String adminUserName, String adminPassword,
			String ipAddress) {
		if ( adminUserName.equals("admin") && adminPassword.equals("admin") ) {
			String s1 = udpC1.getStatus();
			String s2 = udpC2.getStatus();
			String s3 = getPlayerStatusString();
			
			return s1 + " " + s2 + " " + s3;
		}
		
		return "not allowed";
	}

	//------------------------------------------------------------------------
	
	@Override
	public boolean createPlayerAccount(String firstName, String lastName,
			int age, String userName, String password, String ipAddress) {
		// TODO Auto-generated method stub
		
		try {
			PlayerData p = new PlayerData(firstName, lastName, age, userName, password);
			char firstLetter = userName.charAt(0);
			System.out.println(firstLetter);
			ht.get(firstLetter).add(p);
			System.out.println( ht.get(firstLetter) );
			totalPlayers++;
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}

	//------------------------------------------------------------------------
	
	@Override
	public boolean playerSignIn(String userName, String password,
			String ipAddress) {
		// TODO Auto-generated method stub
		return false;
	}

	//------------------------------------------------------------------------
	
	@Override
	public boolean playerSignOut(String userName, String ipAddress) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//------------------------------------------------------------------------

	public String getPlayerStatusString() {
		
		String s = "";
		s += serverName +": ";
		s += playersOnline +" online, ";
		s += (totalPlayers - playersOnline) +" offline. ";
		
		return  s;
	}
	
	//------------------------------------------------------------------------
	
	void initHashTable (){
		ht = new Hashtable<Character, ArrayList<PlayerData>> ();
		for (int i = 0; i < 26; i++) {
			ht.put((char) ('a'+i), new ArrayList<PlayerData> ());
		}
	}
	
	//------------------------------------------------------------------------
	private void initUDPserver() {
		udpS = new UDPserver(this, UDPserverPort);
		new Thread(udpS).start();
		
	}
	
	//------------------------------------------------------------------------

	private void initUDPclients() {
		udpC1 = new UDPclient(UDPclientServer1Port);
		udpC2 = new UDPclient(UDPclientServer2Port);
		
	}

	//------------------------------------------------------------------------
	
	private void initRMIserver() throws Exception {
		Remote obj = UnicastRemoteObject.exportObject(this, rmiPort);
		Registry r = LocateRegistry.createRegistry(rmiPort);
		r.rebind(serverName, obj);
	}
	
	//------------------------------------------------------------------------
	
	
}
