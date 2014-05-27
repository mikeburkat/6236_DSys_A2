package assignment1;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * This is the administrator interface.
 * 
 * @author Mike
 */
public interface AdminInterface extends Remote {
	
	public String getPlayerStatus (String adminUserName, String adminPassword, String ipAddress) throws RemoteException;

}
