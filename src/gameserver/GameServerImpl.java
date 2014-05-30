package gameserver;

import java.util.ArrayList;
import java.util.Hashtable;

import fromAss1.ServerLog;
import fromAss1.UDPclient;
import fromAss1.UDPserver;
import fromAss1.PlayerData;

public class GameServerImpl extends GameServerPOA {

	//------------------------------------------------------------------------
		// Variables to quickly manage ports for servers
		//------------------------------------------------------------------------
		protected final static int RMI_PORT = 2020;
		
		protected final static int NORTH_AMERICA_UDP_PORT = 2030;
		protected final static int EUROPE_UDP_PORT = 2031;
		protected final static int ASIA_UDP_PORT = 2032;
		
		//------------------------------------------------------------------------
		// Fields to easily set ports and run UDP server and 
		// set the server name string when extending this class
		//------------------------------------------------------------------------
		private String serverName;
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
		
		/**
		 * This constructor gets called by the parent class which extends the GameServer.
		 * It sets all the important variables that distinguish each server.
		 * 
		 * @param sName Server Name
		 * @param rmiP RMI port
		 * @param usp UDP server port
		 * @param uc1 UDP client port 1
		 * @param uc2 UDP client port 2
		 */
		public GameServerImpl( String sName, int usp, int uc1, int uc2) { 
			System.out.println("Creating game server: "+ sName 
								+ ", UDPserver: " + usp 
								+ ", UDPclient1: " + uc1 
								+ ", UDPclient2: " + uc2);
			
			serverName = sName;
			UDPserverPort = usp;
			UDPclientServer1Port = uc1;
			UDPclientServer2Port = uc2;
			
			playersOnline = 0;
			playersOffline = 0;
			totalPlayers = 0;
			
			log = new ServerLog(serverName);
			
			initHashTable();
			initUDPclients();
			initUDPserver();
		}
	
	@Override
	public String getPlayerStatus(String adminUserName, String adminPassword,
			String ipAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createPlayerAccount(String firstName, String lastName,
			short age, String userName, String password, String ipAddress) {
		// TODO Auto-generated method stub
		return "Created";
	}

	@Override
	public String playerSignIn(String userName, String password,
			String ipAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String playerSignOut(String userName, String ipAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String transferAccount(String userName, String password,
			String oldIpAddress, String newIpAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String suspendAccount(String adminUserName, String adminPassword,
			String ipAddress, String userNameToSuspend) {
		// TODO Auto-generated method stub
		return null;
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
				ht.put((char) ('A'+i), new ArrayList<PlayerData> ());
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
		

		
		//------------------------------------------------------------------------

}
