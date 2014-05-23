package assignment1;

public class ClientApp {

	public static void main( String args[]) {
		
		AdminInterface adminNA = (AdminInterface) new GameServer();
		PlayerInterface playerNA = (PlayerInterface) adminNA;
		
		System.out.println( playerNA.createPlayerAccount("mike", "burkat", 26, "mikebk", "mmm", "192.xxx") );
		System.out.println( adminNA.getPlayerStatus("admin", "admin", "3") );
		
		
		
	}
}
