package book;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

//para la ejecución de las querys con las que se obtendrán los campos de un foaf
public class FoafManager {
	static private ResultSet query(String queryString, String file) throws IOException {
		InputStream in = new FileInputStream(new File(file));
		Model model = ModelFactory.createMemModelMaker().createModel(null, false);
		model.read(in,null);
		in.close();
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		return results;
	}
	static public String getFoafName(String file) throws IOException {

		// Create a new query
		String queryString = 
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
						+ "PREFIX rdfsynt: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ "SELECT ?name "
						+ "WHERE {"
						+ "?doc foaf:primaryTopic ?me ."
						+ "?me foaf:name ?name"
						+ "}";

		ResultSet results = query(queryString, file);

		RDFNode name = results.nextSolution().get("name");
		
//		System.out.println("name: " + name);
		
		return name.toString();
	}
	
	static public String[][] getFoafFields(String file) throws IOException {
		// Create a new query
		String queryString = 
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
						+ "SELECT ?campo ?valor "
						+ "WHERE {"
						+ "?doc foaf:primaryTopic ?me ."
						+ "?me ?campo ?valor"
						+ "}";

		ResultSet results = query(queryString, file);	
		
		ArrayList<String> campos = new ArrayList<>();
		ArrayList<String> valores = new ArrayList<>();
		
		while(results.hasNext()) {
			
			QuerySolution qsol = results.next();
			RDFNode campo = qsol.get("campo");
			RDFNode valor = qsol.get("valor");
				
			campos.add(campo.toString());
			valores.add(valor.toString());
		}
	
		String[][] ret = new String[campos.size()][2];
		
		for(int i = 0; i < campos.size(); i++) {
			ret[i][0] = campos.get(i);
			ret[i][1] = valores.get(i);
		}
		
		return ret;
	}
	static public String[] getFoafFriends(String file) throws IOException {
		// Create a new query
		String queryString = 
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
						+ "SELECT ?friendName "
						+ "WHERE {"
						+ "?doc foaf:primaryTopic ?me ."
						+ "?me foaf:knows ?friend ."
						+ "?friend foaf:name ?friendName"
						+ "}";

		ResultSet results = query(queryString, file);	
		
		ArrayList<String> amigos = new ArrayList<>();
		
		while(results.hasNext()) {
			
			QuerySolution qsol = results.next();
			RDFNode friendName = qsol.get("friendName");
			
			amigos.add(friendName.toString());
		}
		
		String[] vAmigos = amigos.toArray(new String[amigos.size()]);
		
		return vAmigos;
	}
}
