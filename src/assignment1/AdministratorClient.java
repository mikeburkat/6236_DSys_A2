package assignment1;

import java.rmi.Naming;
import java.rmi.RemoteException;

//----------------------------------------------------------------------------

public class AdministratorClient{

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
	
	public String getPlayerStatus(String aUserN, String aPass, String ip) {
		adminUserName = aUserN;
		adminPassword = aPass;
		ipAddress = ip;
		return getPlayerStatus();
	}
	
	// ------------------------------------------------------------------------
	
	public String getPlayerStatus() {
		AdminInterface server = findServer(ipAddress);
		try {
			String s = server.getPlayerStatus(adminUserName, adminPassword, ipAddress);
			return s;
		} catch (RemoteException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	// ------------------------------------------------------------------------

	private AdminInterface findServer(String ipAddress) {
		AdminInterface server = null;

		String s = ipAddress.substring(0, 3);

		System.out.println(s);
		try {
			switch (s) {
			case "132":
				server = (AdminInterface) Naming.lookup("//localhost:"+RMI_PORT+"/NA");
				System.out.println(s);
				break;
			case "93.":
				server = (AdminInterface) Naming.lookup("//localhost:"+RMI_PORT+"/EU");
				System.out.println(s);
				break;
			case "182":
				server = (AdminInterface) Naming.lookup("//localhost:"+RMI_PORT+"/AS");
				System.out.println(s);
				break;
			};
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return server;
	}
	
	// ------------------------------------------------------------------------
}
