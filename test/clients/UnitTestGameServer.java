package clients;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import client.PlayerClient;

public class UnitTestGameServer {

	/**
	 * Tests that a player is created successfully,
	 * Then it tests that the same user name can't be used again.
	 * @throws IOException 
	 */
	@Test
	public void test_create_player_accountNA_deny_double_creation() {
		PlayerClient p = new PlayerClient("mikewcd", "burkat",(short) 26, "mikeNAtwo", "mmmmmm", "132.0.0.0");
		assertTrue(p.createPlayerAccount());
	
	}

}
