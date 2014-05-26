package clientapp;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import assignment1.ClientApp;
import assignment1.RunServers;

public class UnitTestClientApp {

	
	ClientApp client;
	
//	@Before
//	public void initServers () {
//		RunServers rs = new RunServers();
//	}
	
	
	@Before
	public void launchClientApp () {
		RunServers rs = new RunServers();
		client = new ClientApp();
	}
	
	
	@Test
	public void test() {
		client.createPlayerAccount("mikewcd", "burkat", 26, "mikeNA", "mmm", "132.xxx.xxx.xxx");
		client.createPlayerAccount("mikewcd", "burkat", 26, "mikeEU", "mmm", "93.xxx.xxx.xxx");
		client.createPlayerAccount("mikewcd", "burkat", 26, "mikeAS", "mmm", "182.xxx.xxx.xxx");
		client.getPlayerStatus("admin", "admin", "132.xxx.xxx.xxx");
		
		client.createPlayerAccount("mikewcd", "burkat", 26, "dannaNA", "mmm", "132.xxx.xxx.xxx");
		client.createPlayerAccount("mikewcd", "burkat", 26, "dannaEU", "mmm", "93.xxx.xxx.xxx");
		client.createPlayerAccount("mikewcd", "burkat", 26, "dannaAS", "mmm", "182.xxx.xxx.xxx");
		client.getPlayerStatus("admin", "admin", "93.xxx.xxx.xxx");
		
		client.createPlayerAccount("mikewcd", "burkat", 26, "meliNA", "mmm", "132.xxx.xxx.xxx");
		client.createPlayerAccount("mikewcd", "burkat", 26, "meliEU", "mmm", "93.xxx.xxx.xxx");
		client.createPlayerAccount("mikewcd", "burkat", 26, "meliAS", "mmm", "182.xxx.xxx.xxx");
		client.getPlayerStatus("admin", "admin", "182.xxx.xxx.xxx");
		
		client.playerSignIn("mikeNA", "mmm", "132.xxx.xxx.xxx");
		client.getPlayerStatus("admin", "admin", "132.xxx.xxx.xxx");
		client.playerSignOut("mikeNA", "132.xxx.xxx.xxx");
		client.getPlayerStatus("admin", "admin", "132.xxx.xxx.xxx");
		
	}
	

}
