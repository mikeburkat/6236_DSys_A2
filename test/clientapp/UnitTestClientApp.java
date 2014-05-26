package clientapp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import assignment1.AdministratorClient;
import assignment1.PlayerClient;
import assignment1.RunServers;

public class UnitTestClientApp {
	
	@Test
	public void test() {
//		client.getPlayerStatus("admin", "admin", "132.xxx.xxx.xxx");
	}
	
	@Test
	public void test_create_player_accountNA_deny_double_creation() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "mikeNA", "mmmmmm", "132.0.0.0");
		assertTrue(p.createPlayerAccount());
		PlayerClient p2 = new PlayerClient("mikewcd", "burkat", 26, "mikeNA", "mmmmmm", "132.0.0.0");
		assertFalse(p2.createPlayerAccount());
	}
	
	@Test
	public void test_create_player_accountEU() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "mikeEU", "mmmmmm", "93.0.0.0");
		assertTrue(p.createPlayerAccount());
	}
	
	@Test
	public void test_create_player_accountAS() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "mikeAS", "mmmmmm", "182.0.0.0");
		assertTrue(p.createPlayerAccount());
	}
	
	@Test
	public void test_signIn_and_signOut(){
		PlayerClient p = new PlayerClient("mike", "burkat", 26, "anotherAS", "mmmmmm", "182.0.0.0");
		assertTrue(p.createPlayerAccount());
		assertTrue(p.playerSignIn());
		assertTrue(p.playerSignOut());
	}
	
	@Test
	public void test_SignIn_deny_double_signIn(){
		PlayerClient p = new PlayerClient("mike", "burkat", 26, "anotheroneEU", "mmmmmm", "93.0.0.0");
		assertTrue(p.createPlayerAccount());
		assertTrue(p.playerSignIn());
		assertFalse(p.playerSignIn());
	}
	
	@Test
	public void test_deny_signOut_on_offline_user(){
		PlayerClient p = new PlayerClient("mike", "burkat", 26, "anotherNA", "mmmmmm", "132.0.0.0");
		assertTrue(p.createPlayerAccount());
		assertFalse(p.playerSignOut());
	}
	
	@Test
	public void test_deny_short_or_long_userName(){
		PlayerClient p1 = new PlayerClient("mike", "burkat", 26, "a2345", "mmmmmm", "132.0.0.0");
		assertFalse(p1.createPlayerAccount());
		
		PlayerClient p2 = new PlayerClient("mike", "burkat", 26, "a234567890123456", "mmmmmm", "132.0.0.0");
		assertFalse(p2.createPlayerAccount());
	}
	
	@Test
	public void test_accept_6_char_or_15_char_userName(){
		PlayerClient p1 = new PlayerClient("mike", "burkat", 26, "a23456", "mmmmmm", "132.0.0.0");
		assertTrue(p1.createPlayerAccount());
		
		PlayerClient p2 = new PlayerClient("mike", "burkat", 26, "a23456789012345", "mmmmmm", "132.0.0.0");
		assertTrue(p2.createPlayerAccount());
	}
	
	@Test
	public void test_deny_short_password(){
		PlayerClient p1 = new PlayerClient("mike", "burkat", 26, "short", "12345", "132.0.0.0");
		assertFalse(p1.createPlayerAccount());
	}
	
	@Test
	public void test_accept_6_char_password(){
		PlayerClient p1 = new PlayerClient("mike", "burkat", 26, "goodUname", "123456", "132.0.0.0");
		assertTrue(p1.createPlayerAccount());
	}
	
	@Test
	public void test_get_status_NA(){
		AdministratorClient a = new AdministratorClient("Admin", "Admin", "132.0.0.0");
		
		assertEquals("", a.getPlayerStatus());
	}
	
	
	
	

}
