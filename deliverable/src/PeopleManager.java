package book;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

public class PeopleManager {

	//para la obtención de un objeto person por cada archivo foaf
	//se sobreentiende que sólo se define una persona por cada foaf
	public static TreeMap<String, Person> getPeople() throws IOException{
		
		TreeMap<String, Person> people = new TreeMap<String, Person>();
		
		//look into each file from directory people
		String peopleFolder = "./people";
		File folder = new File(peopleFolder);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	String fileName = file.getName();
		        String filePath = peopleFolder + "/" + fileName;
		        String foafName = FoafManager.getFoafName(filePath);
		        String[] foafFriends = FoafManager.getFoafFriends(filePath);
		        String[][] foafField = FoafManager.getFoafFields(filePath); 
		        people.put(foafName, new Person(foafName, foafFriends, foafField));
		    }
		}
		
		return people;
	}
}
