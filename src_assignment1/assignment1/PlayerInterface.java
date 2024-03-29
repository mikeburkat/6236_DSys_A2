package assignment1;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * This is the player interface.
 * 
 * @author Mike
 */
public interface PlayerInterface extends Remote {
	
	public String createPlayerAccount (String firstName, String lastName, int age, String userName, String password, String ipAddress) throws RemoteException;
	public String playerSignIn (String userName, String password, String ipAddress) throws RemoteException;
	public String playerSignOut (String userName, String ipAddress) throws RemoteException;

}
