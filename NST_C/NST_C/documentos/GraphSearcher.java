package book;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class GraphSearcher {

	//busca los amigos de la persona #person hasta el nivel #level de entre las que encuentran en #people
	//en caso de que el grafo esté incompleto salta una excepción
	public static String[] levelSearch(int level, Person person, TreeMap<String, Person> people) {
		Person root = person;
		int currentLevel = 1;
		int finalLevel = level;
		ArrayList<String> friendsList = new ArrayList<String>();
		String[] friends = null;
		try {
			friends = search4Friends(root, currentLevel, finalLevel, friendsList, people);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return friends;
	}
	private static String[] search4Friends(Person root, int currentLevel, int finalLevel, ArrayList<String> friends,
			TreeMap<String, Person> people) throws Exception {
		
		//el origen es amigo de sí mismo
		if(currentLevel == 1) {
			friends.add(root.getName());
		}
		//obten los amigos del nodo actual
		String[] rootFriends = root.getFriends();
		ArrayList<Person> newFriends = new ArrayList<Person>();
		//procesa los amigos
		for(String rootFriend : rootFriends) {
			//existe?
			Person rootFriendP = people.get(rootFriend);
			if(rootFriendP == null) {
				continue;
			}
			//lo conocemos ya?
			if(friends.contains(rootFriend)) {
				continue;
			} else {
				newFriends.add(rootFriendP); //ahora sí
				friends.add(rootFriend);
			}
		}
		//si no es el último nivel busca a partir de los nuevos amigos
		if(currentLevel < finalLevel) {
			for(Person newFriend : newFriends) {
				search4Friends(newFriend, currentLevel + 1, finalLevel, friends, people);
			}
		}
		return friends.toArray(new String[friends.size()]);
	}
	public static String[] levelSearch(int level, String name, TreeMap<String, Person> people) {
		Person root = people.get(name);
		if(root == null) {
			return null;
		}
		String[] friends = levelSearch(level, root, people);
		return friends;	
	}
	public static String[] levelSearchWValue(int level, Person person, String field, String fieldValue, TreeMap<String, Person> people) {
		Person root = person;
		int currentLevel = 1;
		int finalLevel = level;
		ArrayList<String> friendsList = new ArrayList<String>();
		ArrayList<String> friendWValueList = new ArrayList<String>();
		
		String[] friendsWValue = null;
		
		
		try {
			friendsWValue = search4FriendsWValue(root, field, fieldValue,currentLevel, finalLevel, friendsList, friendWValueList, people);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return friendsWValue;
	}
	private static String[] search4FriendsWValue(Person root,  String field, String fieldValue, int currentLevel, int finalLevel,
			ArrayList<String> friends,  ArrayList<String> friendsWValue, TreeMap<String, Person> people) throws Exception {
		//el origen es amigo de sí mismo
		if(currentLevel == 1) {
			friends.add(root.getName());
		}
		//obten los amigos del nodo actual
		String[] rootFriends = root.getFriends();
		ArrayList<Person> newFriends = new ArrayList<Person>();
		//procesa los amigos
		for(String rootFriend : rootFriends) {
			//existe?
			Person rootFriendP = people.get(rootFriend);
			if(rootFriendP == null) {
				continue;
			}
			//lo conocemos ya?
			if(friends.contains(rootFriend)) {
				continue;
			} else {
				newFriends.add(rootFriendP); //ahora sí
				friends.add(rootFriend);
				
				//comparamos el campo 
				String obtainedFieldValue = rootFriendP.getField(field);
				if((obtainedFieldValue.equals(fieldValue)) && (obtainedFieldValue!=null)) {
					friendsWValue.add(rootFriend);
				}
			}
		}
		
		//si no es el último nivel busca a partir de los nuevos amigos
		if(currentLevel < finalLevel) {
			for(Person newFriend : newFriends) {
				search4FriendsWValue(newFriend, field, fieldValue, currentLevel + 1, finalLevel, friends, friendsWValue, people);
			}
		}
		
		return friendsWValue.toArray(new String[friendsWValue.size()]);
	}
	public static String[] levelSearchWValue(int level, String name, String field, String value, TreeMap<String, Person> people) throws IOException {
		Person root = people.get(name);
		if(root == null) {
			return null;
		}
		String[] friendWValueList = levelSearchWValue(level, root, field, value, people);	
		return friendWValueList;
	}	
	
}
