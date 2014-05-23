package assignment1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Hashtable;

	//------------------------------------------------------------------------

public class GameServer implements PlayerInterface, AdminInterface {
	
	private Hashtable<Character, ArrayList<PlayerData>> ht;
	private int PORT1;
	private int PORT2;
	
	private int NORTH_AMERICA_RMI_PORT = 2020;
	private int EUROPE_RMI_PORT = 2021;
	private int ASIA_RMI_PORT = 2022;
	
	private int NORTH_AMERICA_UDP_PORT = 2030;
	private int EUROPE_UDP_PORT = 2031;
	private int ASIA_UDP_PORT = 2032;

	//------------------------------------------------------------------------
	
	public GameServer() { 
		initHashTable();
		initRMIserver();
		initUDPserver();
		
		
	}

	//------------------------------------------------------------------------


	@Override
	public String getPlayerStatus(String adminUserName, String adminPassword,
			String ipAddress) {
		if (adminUserName == "admin" && adminPassword == "admin") {
			return ht.toString();
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
	
	void initHashTable (){
		ht = new Hashtable<Character, ArrayList<PlayerData>> ();
		for (int i = 0; i < 26; i++) {
			ht.put((char) ('a'+i), new ArrayList<PlayerData> ());
		}
	}
	
	//------------------------------------------------------------------------
	private void initUDPserver() {
		DatagramSocket server1 = null;
		DatagramSocket server2 = null;
		
		try {
			server1 = new DatagramSocket(PORT1);
			server2 = new DatagramSocket(PORT2);
			byte[] buffer = new byte[1000];
			
			while (true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				server1.receive(request);
				String s = new String(request.getData());
				s = s.trim();
				
				byte [] b = s.getBytes();
				
				DatagramPacket reply = new DatagramPacket(b,  b.length, request.getAddress(), request.getPort());
				server1.send(reply);
			}
			
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		
		} catch ( IOException e ) {
			System.out.println("IO: " + e.getMessage());
		} finally { 
			if (server1 != null) server1.close();
		}
	}
	

	//------------------------------------------------------------------------
	
	private void initRMIserver() {
		// TODO Auto-generated method stub
		
	}
	
	//------------------------------------------------------------------------
	
	
}
