import java.util.ArrayList;

public class Person {

	String name;
	String[] friends;
	String fieldValue;
	
	Person(String name, String[] friends){
		this.name = name;
		this.friends = friends;
	}
	
	Person(String fieldValue, String name, String[] friends){
		this.name = name;
		this.friends = friends;
		this.fieldValue = fieldValue;
	}
	
	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
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
}
