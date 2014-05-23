package assignment1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerInterface extends Remote {
	
	public boolean createPlayerAccount (String firstName, String lastName, int age, String userName, String password, String ipAddress) throws RemoteException;
	public boolean playerSignIn (String userName, String password, String ipAddress) throws RemoteException;
	public boolean playerSignOut (String userName, String ipAddress) throws RemoteException;

}
