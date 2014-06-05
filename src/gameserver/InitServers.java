package gameserver;

public class InitServers {

	public static void main(String[] args) {
		
		NorthAmericaServer na = new NorthAmericaServer();
		EuropeServer eu = new EuropeServer();
		AsiaServer as = new AsiaServer();

		new Thread (na).start();
		new Thread (eu).start();
		new Thread (as).start();
		
	}
}
