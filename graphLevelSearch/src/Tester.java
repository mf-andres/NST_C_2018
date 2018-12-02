import java.io.IOException;
import java.util.TreeMap;

public class Tester {

	public static void main(String[] args) {
		
		TreeMap<String, Person> people = null;
		try {			
			people = PeopleManager.getPeople();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		
		String name = "Marta Rodriguez";
		String field = "http://xmlns.com/foaf/0.1/title";
		String value = "Sr";
		
		String[] friendsWCommonValue = null;
		
		try {
		
			friendsWCommonValue = GraphSearcher.levelSearchWValue(1, name, field, value, people);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		System.out.println("friends: " + friendsWCommonValue.length);
		for(String friend : friendsWCommonValue) {
			System.out.println("# " + friend);
		}
		
		String[] friends = null;
		
		System.out.println("More Friends");
		friends = GraphSearcher.levelSearch(3, name, people);
		for(String friend : friends) {
			System.out.println("# " + friend);
		}
		
	}
}
