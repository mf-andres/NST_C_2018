import java.io.IOException;

public class Tester {

	public static void main(String[] args) throws IOException {
		
		FoafManager.getFoafName();
		System.out.println("************************************************");
		
		FoafManager.getFoafFields();
		System.out.println("************************************************");
		
		FoafManager.getFoafFriends();
		System.out.println("************************************************");
		
		FoafManager.checkFieldIs("foaf:givenname","Andrés");
	}

}
