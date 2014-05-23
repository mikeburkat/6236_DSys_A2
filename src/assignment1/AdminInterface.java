package assignment1;

import java.rmi.RemoteException;

public interface AdminInterface extends PlayerInterface {
	
	public String getPlayerStatus (String adminUserName, String adminPassword, String ipAddress) throws RemoteException;


}
