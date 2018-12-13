
package book;

import javax.ws.rs.Produces;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

@Path("/Consultas")  //URI
public class ApiRestMain {
	
	@GET
	@Produces(MediaType.TEXT_XML)                 // consumes
	@Path("{name}")  //Se introduciría como /name por ejemplo /Luis Sabucedo
	public List<Identifier> ConsultaPrimaria(@PathParam("name") String name) {
		return Consulta1(name);
		
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{data}/{data2}/{data3}") // Como /Daniel Couto/name/2
	public String ConsultaSecundaria(@PathParam("data") String data, @PathParam("data2") String data2, @PathParam("data3") int data3 ) {
		List<String> colection = Consulta2(data,data2,data3);
		String result = "";
		for(String colectable : colection) {
			result = result + "#" + colectable;
		}
		return result;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{person}/{person2}")
	public int ConsultaTerciaria(@PathParam("person") String a, @PathParam("person2") String b) {
		return Consulta3(a,b);
	}


	static List<Identifier> Consulta1(String nombre) { 
		List<Identifier> resultado = new ArrayList<>();
		ArrayList<Model> listado = new ArrayList<Model>();
		readFoaf();
		Model model1 = ModelFactory.createDefaultModel();
		Model model2 = ModelFactory.createDefaultModel();
		Model model3 = ModelFactory.createDefaultModel();
		Model model4 = ModelFactory.createDefaultModel();
		Model model5 = ModelFactory.createDefaultModel();
		Model model6 = ModelFactory.createDefaultModel();
		Model model7 = ModelFactory.createDefaultModel();
		Model model8 = ModelFactory.createDefaultModel();
		Model model9 = ModelFactory.createDefaultModel();
		Model model10 = ModelFactory.createDefaultModel();
		Model model11 = ModelFactory.createDefaultModel();  //Prueba para un usuario con 2 FOAF
 
		listado.add(model1.read("people/foaf1.rdf"));
		listado.add(model2.read("people/foaf2.rdf"));
		listado.add(model3.read("people/foaf3.rdf"));
		listado.add(model4.read("people/foaf4.rdf"));
		listado.add(model5.read("people/foaf5.rdf"));
		listado.add(model6.read("people/foaf6.rdf"));
		listado.add(model7.read("people/foaf7.rdf"));
		listado.add(model8.read("people/foaf8.rdf"));
		listado.add(model9.read("people/foaf9.rdf"));
		listado.add(model10.read("people/foaf10.rdf"));
		listado.add(model11.read("people/foaf11.rdf"));
		String Consulta1 =
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/> "+
				"SELECT DISTINCT ?name ?title ?givenname ?family ?nick ?mail ?page ?photo ?phone ?workp ?worki ?schoolhp where {" +
				" ?person foaf:name ?name ."+
				" ?person foaf:knows ?person2 ."+
				" ?a foaf:title ?title ."+
				" ?b foaf:givenname ?givenname ."+
				" ?k foaf:family_name ?family ."+
				" ?c foaf:nick ?nick ."+
				" ?d foaf:mbox_sha1sum ?mail ."+
				" ?d foaf:knows ?mail2 ."+
				" OPTIONAL {?e foaf:homepage ?page} ."+
				" ?f foaf:depiction ?photo ."+
				" ?g foaf:phone ?phone ."+
				" OPTIONAL {?h foaf:workplaceHomepage ?workp } ."+
				" OPTIONAL {?i foaf:workInfoHomepage ?worki }." +
				" ?j foaf:schoolHomepage ?schoolhp ."+
				"}";
		
		Query Quest1 = QueryFactory.create(Consulta1);
		
		Iterator<Model> itera = listado.iterator();
		while(itera.hasNext()) {
			Model elemento = itera.next();
			QueryExecution qquest = QueryExecutionFactory.create(Quest1, elemento);
			try {
				ResultSet results = qquest.execSelect();
				while( results.hasNext()) {
					
					QuerySolution soln = results.nextSolution();
					String name = soln.getLiteral("name").toString();
					if (name.equals(nombre)) {
						String title = soln.getLiteral("title").toString();
						String givenname = soln.getLiteral("givenname").toString();
						String family = soln.getLiteral("family").toString();
						String nick = soln.getLiteral("nick").toString();
						String mail = soln.getLiteral("mail").toString();
						String page, workplace,photo;
						try {
							page = soln.getResource("page").toString();
						} catch (NullPointerException e) {
							page = "None";
						}
						try {
							photo = soln.getResource("photo").toString();
						}catch (NullPointerException e) {
							photo = "None";
						}
						String phone = soln.getResource("phone").toString();
						try {
							workplace = soln.getResource("workp").toString();
						} catch (NullPointerException e) {
							workplace = "None";
						}
						String workinfo = soln.getResource("worki").toString();
						String schoolhp = soln.getResource("schoolhp").toString();
						Identifier data = new Identifier(name,title,givenname,family,nick,mail,page,workplace,photo,phone,workinfo,schoolhp);
						resultado.add(data);
					}else {
						
					}
				}		
			}finally {
				qquest.close();
			}
		}
		return resultado;
	}

	static List<String> Consulta2(String name, String field, int level){
		readFoaf();
		List<String> result = new ArrayList<>();
		TreeMap<String, Person> people = null;
		try {			
			people = PeopleManager.getPeople();
		} catch (IOException e1) {
			e1.printStackTrace();
			result.add("Resultado no válido");
			return result;
		}
		
	
		String full_field = "http://xmlns.com/foaf/0.1/"+field;
		if(field.equals("None")) {
			String[] friends = null;
			
			friends = GraphSearcher.levelSearch(level, name, people);
			for(String friend : friends) {
				result.add(friend);
			}
			return result;
		}else {
			Person start = people.get(name);
			if(start.equals(null)) {
				result.add("Persona no encontrada");
				return result;
			}
			String value = start.getField(full_field);
			String[] friendsWCommonValue = null;
			
			if(value == null) {
				result.add("La persona de la que parte esa búsqueda no tiene ese campo");
			}
			
			try {
				friendsWCommonValue = GraphSearcher.levelSearchWValue(level, name, full_field, value, people);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				result.add(e.getMessage());
				return result;
			}
			if(friendsWCommonValue==(null)) {
				result.add("Resultado vacío (friendsWCommonValue)");
				return result;
			}
			for(String friend : friendsWCommonValue) {
				result.add(friend);
			}
			return result;
		}
	}

	static int Consulta3(String name1, String name2) {
		File foaf1 = new File("people/foaf1.rdf");
	    File foaf2 = new File("people/foaf2.rdf");
	    File foaf3 = new File("people/foaf3.rdf");
	    File foaf4 = new File("people/foaf4.rdf");
	    File foaf5 = new File("people/foaf5.rdf");
	    File foaf6 = new File("people/foaf6.rdf");
	    File foaf7 = new File("people/foaf7.rdf");
	    File foaf8 = new File("people/foaf8.rdf");
	    File foaf9 = new File("people/foaf9.rdf");
	    File foaf10 = new File("people/foaf10.rdf");
	    try {
			URL url1= new URL("http://localhost:8080/DATA/foaf1.rdf");
			FileUtils.copyURLToFile(url1, foaf1);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url2= new URL("http://localhost:8080/DATA/foaf2.rdf");
			FileUtils.copyURLToFile(url2, foaf2);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url3= new URL("http://localhost:8080/DATA/foaf3.rdf");
			FileUtils.copyURLToFile(url3, foaf3);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url4= new URL("http://localhost:8080/DATA/foaf4.rdf");
			FileUtils.copyURLToFile(url4, foaf4);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url5= new URL("http://localhost:8080/DATA/foaf5.rdf");
			FileUtils.copyURLToFile(url5, foaf5);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url6= new URL("http://localhost:8080/DATA/foaf6.rdf");
			FileUtils.copyURLToFile(url6, foaf6);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url7= new URL("http://localhost:8080/DATA/foaf7.rdf");
			FileUtils.copyURLToFile(url7, foaf7);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url8= new URL("http://localhost:8080/DATA/foaf8.rdf");
			FileUtils.copyURLToFile(url8, foaf8);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url9= new URL("http://localhost:8080/DATA/foaf9.rdf");
			FileUtils.copyURLToFile(url9, foaf9);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url10= new URL("http://localhost:8080/DATA/foaf10.rdf");
			FileUtils.copyURLToFile(url10, foaf10);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    
	    ArrayList<File> ficheros = new ArrayList<File>();
	    ficheros.add(foaf1);
        ficheros.add(foaf2);
        ficheros.add(foaf3);
        ficheros.add(foaf4);
        ficheros.add(foaf5);
        ficheros.add(foaf6);
        ficheros.add(foaf7);
        ficheros.add(foaf8);
        ficheros.add(foaf9);
        ficheros.add(foaf10);
        
        File file1=null;
		try {
			file1 = getFoaf(name1,ficheros);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        File file2=null;
		try {
			file2 = getFoaf(name2,ficheros);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        int distancia = 0;

        try {
			distancia = findShortestPath(file1, file2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			distancia = 0;
		}
        
		return distancia;
	}

    public static int findShortestPath(File file1, File file2) throws IOException {
    	String cadena
                = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
                + "PREFIX rdfsynt: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "SELECT ?name "
                + "WHERE {"
                + "?doc foaf:primaryTopic ?me ."
                + "?me foaf:name ?name"
                + "}";

        ResultSet resultado = query(cadena, file1);
        RDFNode name = resultado.nextSolution().get("name");
        String queryString
                = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
                + "SELECT ?friendName "
                + "WHERE {"
                + "?doc foaf:primaryTopic ?me ."
                + "?me foaf:knows ?friend ."
                + "?friend foaf:name ?friendName"
                + "}";
        ResultSet results1 = query(queryString, file1);
        ArrayList<String> amigos = new ArrayList<>();
        while (results1.hasNext()) {
            QuerySolution qsol = results1.next();
            RDFNode friendName = qsol.get("friendName");
            amigos.add(friendName.toString());
        }
        String[] vAmigos = amigos.toArray(new String[0]);
        ResultSet results2 = query(queryString, file2);
        ResultSet resultado2 = query(cadena, file2);
        RDFNode nombre = resultado2.nextSolution().get("name");
        ArrayList<String> friends = new ArrayList<>();
        while (results2.hasNext()) {
            QuerySolution qsol = results2.next();
            RDFNode friendName = qsol.get("friendName");
            friends.add(friendName.toString());
        }
        String[] vFriends = friends.toArray(new String[0]);
        for (int i = 0; i < vFriends.length; i++) {
            if (vFriends[i].equalsIgnoreCase(name.toString())) {
                return 1; // amigos de primer orden
            } 
        }
        for (int i = 0; i < vAmigos.length; i++) {
            if (vAmigos[i].equalsIgnoreCase(nombre.toString())) {
                return 1; // amigos de primer orden
            }
        }
        for (int i = 0; i < vAmigos.length; i++) {
            for (int j = 0; j < vFriends.length; j++) {
                if (vAmigos[i].equalsIgnoreCase(vFriends[j])) {
                    return 2; // los dos foaf son amigos de segundo orden (un amigo en común)
                }
            }
        }
        return 3; // amigos de tercer orden
    }

    public static String getMainName(String fichero) throws IOException {
        String cadena
                = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
                + "PREFIX rdfsynt: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "SELECT ?name "
                + "WHERE {"
                + "?doc foaf:primaryTopic ?me ."
                + "?me foaf:name ?name"
                + "}";
        ResultSet resultado = query(cadena, new File(fichero));
        RDFNode name = resultado.nextSolution().get("name");
        return name.toString();
    }
    
    static public File getFoaf(String nombre,ArrayList<File> a) throws IOException {

        for (int i = 0; i < a.size(); i++) {
            String n = getMainName(a.get(i).toString());
            if (n.equalsIgnoreCase(nombre)) {
                return a.get(i);
            }
        }

        return null;
    }
    
    static private ResultSet query(String queryString, File file) throws IOException {
        InputStream in = new FileInputStream(new File(file.toString()));
        Model model = ModelFactory.createMemModelMaker().createModel(null, false);
        model.read(in, null);
        in.close();
        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();
        return results;
    }

    static private void readFoaf() {
		File foaf1 = new File("people/foaf1.rdf");
	    File foaf2 = new File("people/foaf2.rdf");
	    File foaf3 = new File("people/foaf3.rdf");
	    File foaf4 = new File("people/foaf4.rdf");
	    File foaf5 = new File("people/foaf5.rdf");
	    File foaf6 = new File("people/foaf6.rdf");
	    File foaf7 = new File("people/foaf7.rdf");
	    File foaf8 = new File("people/foaf8.rdf");
	    File foaf9 = new File("people/foaf9.rdf");
	    File foaf10 = new File("people/foaf10.rdf");
	    try {
			URL url1= new URL("http://localhost:8080/DATA/foaf1.rdf");
			FileUtils.copyURLToFile(url1, foaf1);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url2= new URL("http://localhost:8080/DATA/foaf2.rdf");
			FileUtils.copyURLToFile(url2, foaf2);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url3= new URL("http://localhost:8080/DATA/foaf3.rdf");
			FileUtils.copyURLToFile(url3, foaf3);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url4= new URL("http://localhost:8080/DATA/foaf4.rdf");
			FileUtils.copyURLToFile(url4, foaf4);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url5= new URL("http://localhost:8080/DATA/foaf5.rdf");
			FileUtils.copyURLToFile(url5, foaf5);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url6= new URL("http://localhost:8080/DATA/foaf6.rdf");
			FileUtils.copyURLToFile(url6, foaf6);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url7= new URL("http://localhost:8080/DATA/foaf7.rdf");
			FileUtils.copyURLToFile(url7, foaf7);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url8= new URL("http://localhost:8080/DATA/foaf8.rdf");
			FileUtils.copyURLToFile(url8, foaf8);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url9= new URL("http://localhost:8080/DATA/foaf9.rdf");
			FileUtils.copyURLToFile(url9, foaf9);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    try {
			URL url10= new URL("http://localhost:8080/DATA/foaf10.rdf");
			FileUtils.copyURLToFile(url10, foaf10);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    }
}

  