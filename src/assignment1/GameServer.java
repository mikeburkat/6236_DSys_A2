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
		
		if ( adminUserName.equals("Admin") && adminPassword.equals("Admin") ) {
			
			log.addToAdminLog(adminUserName, "requested player status.");
			
			String myStatus = getPlayerStatusString();
			String s2 = udpC1.getStatus();
			String s3 = udpC2.getStatus();

			String s = myStatus + ", " + s2 + ", " + s3 + ".";
			
			log.addToAdminLog(adminUserName, "got status back: " + s);
			return s;
		} else {
			log.addToServerLog("Admin: " + adminUserName + " requested player status, but user name or password was wrong.");
			return "Not allowed, wrong user name or password.";
		}
	}

	//------------------------------------------------------------------------
	
	@Override
	public String createPlayerAccount(String firstName, String lastName,
			int age, String userName, String password, String ipAddress) {
		
		PlayerData pd = getPlayer(userName);
		if (pd != null) {
			String s = "Player: " + userName + " already exists, try a different User Name.";
			log.addToServerLog(s);
			return s;
		}
		
		try {
			PlayerData p = new PlayerData(firstName, lastName, age, userName, password);
			char firstLetter = userName.charAt(0);
			ht.get(firstLetter).add(p);
			totalPlayers++;
			log.addToPlayerLog(userName, "created.");
			return "Created";
		} catch (Exception e) {
			String s = "Player: " + userName + " creation failed: " + e.getMessage();
			log.addToServerLog(s);
			return s;
		}
	}

	//------------------------------------------------------------------------
	
	@Override
	public String playerSignIn(String userName, String password,
			String ipAddress) {
		
		PlayerData pd = getPlayer(userName);
		if (pd == null) {
			log.addToServerLog("Sign In Failed, player "+userName+" not found");
			return "Sign In Failed, player not found";
		}
		
		String s = pd.signIn(password);
		
		if ( s.equals("Success") ) {
			playersOffline--;
			playersOnline++;
			log.addToPlayerLog(userName, "signed in.");
			return "Signed In";
		} else {
			log.addToPlayerLog(userName, s);
			return s;
		}
	}

	//------------------------------------------------------------------------
	
	private PlayerData getPlayer(String userName) {
		
		char firstLetter = userName.charAt(0);
		ArrayList<PlayerData> pd = ht.get(firstLetter);
		for (PlayerData p : pd) {
			if (userName.equals( p.getUserName() )) {
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
			log.addToServerLog("Sign Out Failed, player "+userName+" not found");
			return "Sign In Failed, player not found";
		}
		
		String s = pd.signOut();
		
		if ( s.equals("Success") ) {
			playersOffline++;
			playersOnline--;
			log.addToPlayerLog(userName, "signed out.");
			return "Signed Out";
		} else {
			log.addToPlayerLog(userName, "Sign Out Failed, user not currently signed in");
			return "Sign Out Failed, user not currently signed in";
		}
	}
	
	//------------------------------------------------------------------------

	public String getPlayerStatusString() {
		
		String s = "";
		s += serverName +": ";
		s += playersOnline +" online, ";
		s += (totalPlayers - playersOnline) +" offline";
		
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
	void initUDPserver() {
		udpS = new UDPserver(this, UDPserverPort);
		new Thread(udpS).start();
		
	}
	
	//------------------------------------------------------------------------

	void initUDPclients() {
		udpC1 = new UDPclient(UDPclientServer1Port);
		udpC2 = new UDPclient(UDPclientServer2Port);
	}

	//------------------------------------------------------------------------
	
	void initRMIserver() throws Exception {
		System.out.println("RMI: " + serverName);
		Remote obj = UnicastRemoteObject.exportObject(this, rmiPort);
		Registry r = LocateRegistry.getRegistry("localhost", RMI_PORT);
		
		r.rebind(serverName, obj);
		System.out.println("RMI Server is up in: " + serverName + " on port: " + rmiPort);
	}
	
	//------------------------------------------------------------------------
}
