package assignment1;

import java.rmi.Naming;
import java.rmi.RemoteException;

//----------------------------------------------------------------------------

public class AdministratorClient implements Runnable {

	private final int RMI_PORT = 2020;
	private String adminUserName;
	private String adminPassword;
	private String ipAddress;

	// ------------------------------------------------------------------------

	public AdministratorClient() {
	}
	
	// ------------------------------------------------------------------------
	
	public AdministratorClient(String aUserN, String aPass, String ip) {
		adminUserName = aUserN;
		adminPassword = aPass;
		ipAddress = ip;
	}
	
	// ------------------------------------------------------------------------
	
	synchronized public String getPlayerStatus(String aUserN, String aPass, String ip) {
		adminUserName = aUserN;
		adminPassword = aPass;
		ipAddress = ip;
		return getPlayerStatus();
	}
	
	// ------------------------------------------------------------------------
	
	synchronized public String getPlayerStatus() {
		AdminInterface server = findServer(ipAddress);
		System.out.println(adminUserName +" "+ adminPassword +" "+ ipAddress + " ");
		try {
			String s = server.getPlayerStatus(adminUserName, adminPassword, ipAddress);
			System.out.println(s +"\n");
			return s;
		} catch (RemoteException e) {
			System.out.println(e.getMessage() +"\n");
			e.printStackTrace();
		}
		return null;
	}

	// ------------------------------------------------------------------------

	synchronized private AdminInterface findServer(String ipAddress) {
		AdminInterface server = null;

		String s = ipAddress.substring(0, 3);

		try {
			switch (s) {
			case "132":
				server = (AdminInterface) Naming.lookup("//localhost:"+RMI_PORT+"/NA");
				break;
			case "93.":
				server = (AdminInterface) Naming.lookup("//localhost:"+RMI_PORT+"/EU");
				break;
			case "182":
				server = (AdminInterface) Naming.lookup("//localhost:"+RMI_PORT+"/AS");
				break;
			};
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return server;
	}
	
	// ------------------------------------------------------------------------

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			getPlayerStatus();
		}
	}
	
	// ------------------------------------------------------------------------
}
