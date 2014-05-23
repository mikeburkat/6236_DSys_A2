package assignment1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ServerLog {

	String serverName;
	FileWriter fw;
	BufferedWriter bw;
	

	public ServerLog(String sN) {
		serverName = sN;
		initServerLog();
	}
	
	
	public void add(String s) {
		
		openFile();
		try {
			bw.write(s);
			bw.close();
		} catch (IOException e) {
			System.out.println("Failed to write to server log for " + serverName);
			e.printStackTrace();
		}
	}
	
	
	private void openFile() {
		try {
			fw = new FileWriter("" + serverName + "/" + serverName + "server.log", true);
		} catch (IOException e) {
			System.out.println("Failed to create server log for " + serverName);
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw);
	}


	public void initServerLog() {

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
	

}
