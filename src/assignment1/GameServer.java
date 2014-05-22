package assignment1;

import java.util.Hashtable;

public class GameServer implements PlayerInterface, AdminInterface {
	
	private Hashtable<Character, Player> ht;
	
	public GameServer() { 
		ht = new Hashtable();
	}
	
	
	@Override
	public String getPlayerStatus(String adminUserName, String adminPassword,
			String ipAddress) {
		// TODO Auto-generated method stub
		return adminUserName+adminPassword+ipAddress;
	}

	@Override
	public boolean createPlayerAccount(String firstName, String lastName,
			int age, String userName, String password, String ipAddress) {
		// TODO Auto-generated method stub
		
		try {
			PlayerData p = new PlayerData(firstName, lastName, userName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}

	@Override
	public boolean playerSignIn(String userName, String password,
			String ipAddress) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean playerSignOut(String userName, String ipAddress) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
