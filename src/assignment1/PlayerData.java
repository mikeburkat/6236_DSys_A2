package assignment1;

public class PlayerData {

	private String userName;
	private String firstName;
	private String lastName;
	private int age;
	private String password;
	private boolean online;
	
	public PlayerData (String fN, String lN, int a, String uN, String p) throws Exception {
		setUserName(uN);
		firstName = fN;
		lastName = lN;
		age = a;
		password = p;
		online = false;
	}
	
	public void setUserName (String uN) throws Exception {
		if (uN.length() >= 6 && uN.length() <= 15) {
			userName = uN;
		} else {
			throw new Exception("User name too short.");
		}
	}
	
	
	public String signIn(String pass) {
		if (online) {
			return "Sign In Failed, player already signed in";
		} else if ( password.equals(pass) ) {
			online = true;
			return "Success";
		} else {
			return "Sign In Failed, password authentification failed";
		}
	}
	
	public String signOut() {
		if (!online) {
			return "Sign out failed, user already signed out";
		} else {
			online = false;
			return "Success";
		}
	}
	
	public String toString () {
		return userName;
	}

	public String getUserName() {
		return userName;
	}
	
}
