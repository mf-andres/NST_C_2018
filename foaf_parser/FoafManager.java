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

public class FoafManager {


	static private ResultSet query(String queryString) throws IOException {

		InputStream in = new FileInputStream(new File("andres.rdf"));

		// Create an empty in-memory model and populate it from the graph
		Model model = ModelFactory.createMemModelMaker().createModel(null, false);
		model.read(in,null); // null base URI, since model URIs are absolute
		in.close();

		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();

		// Output query results 
		//ResultSetFormatter.out(System.out, results, query);

		return results;
	}


	static public String getFoafName() throws IOException {

		// Create a new query
		String queryString = 
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
						+ "PREFIX rdfsynt: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ "SELECT ?name "
						+ "WHERE {"
						+ "?doc foaf:primaryTopic ?me ."
						+ "?me foaf:name ?name"
						+ "}";

		ResultSet results = query(queryString);

		RDFNode name = results.nextSolution().get("name");
		
		System.out.println("name: " + name);
		
		return name.toString();
	}


	static public String[][] getFoafFields() throws IOException {

		// Create a new query
		String queryString = 
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
						+ "SELECT ?campo ?valor "
						+ "WHERE {"
						+ "?doc foaf:primaryTopic ?me ."
						+ "?me ?campo ?valor"
						+ "}";

		ResultSet results = query(queryString);	
		
		ArrayList<String> campos = new ArrayList<>();
		ArrayList<String> valores = new ArrayList<>();
		
		while(results.hasNext()) {
			
			QuerySolution qsol = results.next();
			RDFNode campo = qsol.get("campo");
			RDFNode valor = qsol.get("valor");
			
			campos.add(campo.toString());
			valores.add(valor.toString());
			
			System.out.println("campo: " + campo + " valor: " + valor);
		}
	
		String[] vCampos = (String[]) campos.toArray();
		String[] vValores = (String[]) valores.toArray();
		
		String[][] ret = new String[][] {vCampos, vValores};
		
		return ret;
	}

	static public String[] getFoafFriends() throws IOException {

		// Create a new query
		String queryString = 
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
						+ "SELECT ?friendName "
						+ "WHERE {"
						+ "?doc foaf:primaryTopic ?me ."
						+ "?me foaf:knows ?friend ."
						+ "?friend foaf:name ?friendName"
						+ "}";

		ResultSet results = query(queryString);	
		
		ArrayList<String> amigos = new ArrayList<>();
		
		while(results.hasNext()) {
			
			QuerySolution qsol = results.next();
			RDFNode friendName = qsol.get("friendName");
			
			System.out.println("friendName:" + friendName);
			
			amigos.add(friendName.toString());
		}
		
		String[] vAmigos = (String[]) amigos.toArray();
		
		return vAmigos;
	}

	static public Boolean checkFieldIs(String field, String value) throws IOException {

		// Create a new query
		String queryString = 
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
						+ "ASK"
						+ "{"
						+ "?doc foaf:primaryTopic ?me ."
						+ "?me " + field + " \"" + value + "\" "
						+ "}";

		InputStream in = new FileInputStream(new File("andres.rdf"));

		// Create an empty in-memory model and populate it from the graph
		Model model = ModelFactory.createMemModelMaker().createModel(null, false);
		model.read(in,null); // null base URI, since model URIs are absolute
		in.close();

		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		Boolean result = qe.execAsk();

		// Output query results 
		System.out.println(result);

		// Important - free up resources used running the query
		qe.close();	
		
		return result;
	}

}
