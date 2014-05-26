package assignment1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//------------------------------------------------------------------------

public class ServerLog {

	String serverName;
	FileWriter fw;
	BufferedWriter bw;
	
	//------------------------------------------------------------------------

	public ServerLog(String sN) {
		serverName = sN;
		resetServerLogs(); 
		initServerLog();
	}

	//------------------------------------------------------------------------

	public void addToServerLog(String s) {
		
		openFile("server");
		write(s);
		System.out.println(s);
	}
	
	//------------------------------------------------------------------------
	
	public void addToPlayerLog(String userName, String s) {
		s = "Player: " + userName + " " + s;
		
		openFile(userName);
		write(s);
		addToServerLog(s);
	}
	
	//------------------------------------------------------------------------
	
	public void addToAdminLog(String userName, String s) {
		s = "Admin: " + userName + " " + s;
		
		openFile(userName);
		write(s);
		addToServerLog(s);
	}
	
	//------------------------------------------------------------------------
	
	private void openFile(String fileName) {
		try {
			fw = new FileWriter("" + serverName + "/" + serverName + fileName + ".log", true);
		} catch (IOException e) {
			System.out.println("Failed to create server log for " + serverName);
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw);
	}
	
	//------------------------------------------------------------------------
	
	private void write(String s) {
		String out = "";
		out += new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		out += " " + s;
		
		try {
			bw.write(out + "\n");
			bw.close();
		} catch (IOException e) {
			System.out.println("Failed to write to player log.");
			e.printStackTrace();
		}
	}
	
	//------------------------------------------------------------------------

	private void initServerLog() {

		File serverFolder = new File("" + serverName);
		if (!serverFolder.exists()) {
			boolean success = (serverFolder).mkdirs();
			if (!success) {
				System.out.println("Failed to create server folder for maintaining logs for " + serverName);
			} else {
				System.out.println("Server log folder for " + serverName
						+ " was created at location: "
						+ serverFolder.getAbsolutePath());
			}
		}
	}
	
	//------------------------------------------------------------------------
	
	private void resetServerLogs() {
		
		System.out.println("You may want to reset logs manually for " + serverName);
		
	}
	
	//------------------------------------------------------------------------
}
