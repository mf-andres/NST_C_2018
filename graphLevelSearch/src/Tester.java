import java.util.TreeMap;

public class Tester {

	public static void main(String[] args) {

//		//construye el grafo
//		Person A = new Person("A",new String[]{"B","C"});
//		Person B = new Person("B",new String[]{"A","C","D"});
//		Person C = new Person("C",new String[]{"A","C","E"});
//		Person D = new Person("D",new String[]{"B"});
//		Person E = new Person("E",new String[]{"C"});
//		
//		//lista de persona para simular el input
//		TreeMap<String, Person> people = new TreeMap<>();
//		people.put(A.getName(), A);
//		people.put(B.getName(), B);
//		people.put(C.getName(), C);
//		people.put(D.getName(), D);
//		people.put(E.getName(), E);
		
//		//construye el grafo
//		Person A = new Person("A",new String[]{"B"});
//		Person B = new Person("B",new String[]{"A","C","D"});
//		Person C = new Person("C",new String[]{"B"});
//		Person D = new Person("D",new String[]{"B","E","F"});
//		Person E = new Person("E",new String[]{"D","G"});
//		Person F = new Person("F",new String[]{"D"});
//		Person G = new Person("G",new String[]{"E","H"});
//		Person H = new Person("H",new String[]{"G"});
//		
//		
//		
//		//lista de persona para simular el input
//		TreeMap<String, Person> people = new TreeMap<>();
//		people.put(A.getName(), A);
//		people.put(B.getName(), B);
//		people.put(C.getName(), C);
//		people.put(D.getName(), D);
//		people.put(E.getName(), E);
//		people.put(F.getName(), F);
//		people.put(G.getName(), G);
//		people.put(H.getName(), H);
		
		//construye el grafo
				Person A = new Person("1","A",new String[]{"B"});
				Person B = new Person("2","B",new String[]{"A","C","D"});
				Person C = new Person("1","C",new String[]{"B"});
				Person D = new Person("2","D",new String[]{"B","E","F"});
				Person E = new Person("1","E",new String[]{"D","G"});
				Person F = new Person("2","F",new String[]{"D"});
				Person G = new Person("1","G",new String[]{"E","H"});
				Person H = new Person("2","H",new String[]{"G"});
				
				
				
				//lista de persona para simular el input
				TreeMap<String, Person> people = new TreeMap<>();
				people.put(A.getName(), A);
				people.put(B.getName(), B);
				people.put(C.getName(), C);
				people.put(D.getName(), D);
				people.put(E.getName(), E);
				people.put(F.getName(), F);
				people.put(G.getName(), G);
				people.put(H.getName(), H);
		
		//busca los amigos hasta el nivel cuatro de a
		String[] friends = GraphSearcher.levelSearch(7, B, people);
		
		for(String friend : friends) {
			System.out.println("# " + friend);
		}
		
		String[] friendsWCommonValue = GraphSearcher.levelSearchWValue(7, H, people);
		
		for(String friend : friendsWCommonValue) {
			System.out.println("# " + friend);
		}
	}

}
