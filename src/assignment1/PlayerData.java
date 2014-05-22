package assignment1;

public class PlayerData {

	private String userName;
	private String firstName;
	private String lastName;
	private String age;
	private String password;
	
	public PlayerData (String fN, String lN, String a, String uN, String p) throws Exception {
		setUserName(uN);
		firstName = fN;
		lastName = lN;
		age = a;
		password = p;
	}
	
	public void setUserName (String uN) throws Exception {
		throw new Exception("User name too short.");
	}
	
	public String toString () {
		return userName;
	}
	
}
