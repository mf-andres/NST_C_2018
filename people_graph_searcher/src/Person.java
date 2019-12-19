import java.util.ArrayList;
import java.util.TreeMap;

public class Person {

	String name;
	String[] friends;
	String[][] fields;
	
	Person(String name, String[] friends){
		this.name = name;
		this.friends = friends;
	}
	
	public Person(String foafName, String[] foafFriends, String[][] foafField) {
		
		this.name = foafName;
		this.friends = foafFriends;
		this.fields = foafField;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String[] getFriends() {
		return friends;
	}

	public void setFriends(String[] friends) {
		this.friends = friends;
	}
	
	public String getField(String field) {
		
		for(String[] parCampoValor : fields) {
			
			if(parCampoValor[0].equals(field)) {
				
				return parCampoValor[1];
			}
		}
		
		return null;
	}

	public void setFields(String[][] fields) {
		this.fields = fields;
	}	
}
