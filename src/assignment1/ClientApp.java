package assignment1;

public class ClientApp {

	public static void main( String args[]) {
		AdminInterface admin = (AdminInterface) new GameServer();
		PlayerInterface player = (PlayerInterface) new GameServer();
		
		System.out.println( admin.getPlayerStatus("1", "2", "3") );
		System.out.println( player.playerSignIn("", "", "") );
		
	}
}
