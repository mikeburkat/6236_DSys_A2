package assignment1;

import java.rmi.Remote;

public interface PlayerInterface extends Remote {
	
	public boolean createPlayerAccount (String firstName, String lastName, int age, String userName, String password, String ipAddress);
	public boolean playerSignIn (String userName, String password, String ipAddress);
	public boolean playerSignOut (String userName, String ipAddress);

}
