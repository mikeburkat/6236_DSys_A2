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
	// Variables to quickly manage ports for servers
	//------------------------------------------------------------------------
	public final static int RMI_PORT = 2020;
	
	public final static int NORTH_AMERICA_UDP_PORT = 2030;
	public final static int EUROPE_UDP_PORT = 2031;
	public final static int ASIA_UDP_PORT = 2032;
	
	//------------------------------------------------------------------------
	// Fields to easily set ports and run UDP server and 
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
	private ServerLog log;
	//------------------------------------------------------------------------
	
	// Constructor called by extender
	public GameServer( String sName, int rmiP, int usp, int uc1, int uc2) { 
		System.out.println("Creating game server: "+ sName 
							+ ", RMIport: " + rmiP
							+ ", UDPserver: " + usp 
							+ ", UDPclient1: " + uc1 
							+ ", UDPclient2: " + uc2);
		
		serverName = sName;
		rmiPort = rmiP;
		UDPserverPort = usp;
		UDPclientServer1Port = uc1;
		UDPclientServer2Port = uc2;
		
		playersOnline = 0;
		playersOffline = 0;
		totalPlayers = 0;
		
		log = new ServerLog(serverName);
		
		initHashTable();
		initUDPclients();
		
		try {
			initRMIserver();
		} catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		initUDPserver();
	}

	//------------------------------------------------------------------------
	@Override
	public String getPlayerStatus(String adminUserName, String adminPassword,
			String ipAddress) {
		
		log.addToServerLog("Admin: " + adminUserName + " requested player status.");
		
		if ( adminUserName.equals("admin") && adminPassword.equals("admin") ) {
			String s1 = udpC1.getStatus();
			String s2 = udpC2.getStatus();
			String s3 = getPlayerStatusString();
			
			String s = s1 + " " + s2 + " " + s3;
			
			System.out.println(s);
			log.addToServerLog("Admin: " + adminUserName + " got status back: " + s);
			return s;
		}
		
		log.addToServerLog("Admin: " + adminUserName + " user name or password.");
		
		return "Not allowed, wrong user name or password.";
	}

	//------------------------------------------------------------------------
	
	@Override
	public boolean createPlayerAccount(String firstName, String lastName,
			int age, String userName, String password, String ipAddress) {
		
		log.addToPlayerLog(userName, "Player: " + userName + " created.");
		
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
	public String playerSignIn(String userName, String password,
			String ipAddress) {
		
		PlayerData pd = getPlayer(userName);
		if (pd == null) {
			return "Sign In Failed, player not found";
		}
		
		String s = pd.signIn(password);
		
		System.out.println(s);
		
		if ( s.equals("Success") ) {
			playersOffline--;
			playersOnline++;
			log.addToPlayerLog(userName, "Player: " + userName + " signed in.");
			return "Sign In Succesful";
		} else {
			return s;
		}
	}

	//------------------------------------------------------------------------
	
	private PlayerData getPlayer(String userName) {
		
		char firstLetter = userName.charAt(0);
		System.out.println(firstLetter);
		ArrayList<PlayerData> pd = ht.get(firstLetter);
		System.out.println(pd);
		for (PlayerData p : pd) {
			System.out.println(p);
			if (userName.equals( p.getUserName() )) {
				System.out.println("player found");
				return p;
			}
		}
		return null;
	}
	
	//------------------------------------------------------------------------

	@Override
	public String playerSignOut(String userName, String ipAddress) {
		
		PlayerData pd = getPlayer(userName);
		if (pd == null) {
			return "Sign In Failed, player not found";
		}
		
		String s = pd.signOut();
		
		if ( s.equals("Success") ) {
			playersOffline++;
			playersOnline--;
			log.addToPlayerLog(userName, "Player: " + userName + " signed out.");
			return "Sign Out Succesful";
		} else {
			return "Sign Out Failed, user not currently signed in";
		}
		
		
		
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
		System.out.println("RMI: " + serverName);
		Remote obj = UnicastRemoteObject.exportObject(this, rmiPort);
		Registry r = LocateRegistry.getRegistry("localhost", RMI_PORT);
		
		r.rebind(serverName, obj);
		System.out.println("RMI Server is up in: " + serverName + " on port: " + rmiPort);
	}
	
	//------------------------------------------------------------------------
	
	
}
