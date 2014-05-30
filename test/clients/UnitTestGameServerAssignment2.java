package clients;

import static org.junit.Assert.*;

import org.junit.Test;

import client.AdministratorClient;
import client.PlayerClient;

public class UnitTestGameServerAssignment2 {

	@Test
	public void test_suspend_account() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "mikeSuspend", "mmmmmm", "132.0.0.0");
		assertTrue(p.createPlayerAccount());
		AdministratorClient a = new AdministratorClient("Admin", "Admin", "132.0.0.0");
		assertTrue(a.suspendAccount("mikeSuspend"));
		
		assertFalse(a.suspendAccount("mikeSuspend"));
	}
	
	@Test
	public void test_suspend_account_double_suspend() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat", 26, "mikeSuspend2", "mmmmmm", "132.0.0.0");
		assertTrue(p.createPlayerAccount());
		AdministratorClient a = new AdministratorClient("Admin", "Admin", "132.0.0.0");
		assertTrue(a.suspendAccount("mikeSuspend2"));
		
		assertFalse(a.suspendAccount("mikeSuspend2"));
	}
	
	@Test
	public void test_suspend_account_nonExistant_player() {
		AdministratorClient a = new AdministratorClient("Admin", "Admin", "132.0.0.0");
		assertFalse(a.suspendAccount("mikeSuspend3"));
	}
	

}
