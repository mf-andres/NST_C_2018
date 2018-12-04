
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
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;

public class ReadFoaf {

    static File foaf1 = new File("foaf1.rdf");
    static File foaf2 = new File("foaf2.rdf");
    static File foaf3 = new File("foaf3.rdf");
    static File foaf4 = new File("foaf4.rdf");
    static File foaf5 = new File("foaf5.rdf");
    static File foaf6 = new File("foaf6.rdf");
    static File foaf7 = new File("foaf7.rdf");
    static File foaf8 = new File("foaf8.rdf");
    static File foaf9 = new File("foaf9.rdf");
    static File foaf10 = new File("foaf10.rdf");
    
    static ArrayList<File> ficheros = new ArrayList();
    
    

    public static int findShortestPath(File file1, File file2) throws IOException {
        

        boolean primerOrden = false, segundoOrden = false, tercerOrden = false;

        // leemos el nombre
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
             //   System.out.println("name: " + name);

        // leemos los friends
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

        /////// segundo foaf
        ResultSet results2 = query(queryString, file2);

        ArrayList<String> friends = new ArrayList<>();

        while (results2.hasNext()) {

            QuerySolution qsol = results2.next();
            RDFNode friendName = qsol.get("friendName");

            friends.add(friendName.toString());
        }

        String[] vFriends = friends.toArray(new String[0]);

        for (int i = 0; i < vAmigos.length; i++) {
            System.out.println(vAmigos[i]);
        }
        System.out.println("-------------");
        for (int i = 0; i < vFriends.length; i++) {
            System.out.println(vFriends[i]);
        }

        for (int i = 0; i < vFriends.length; i++) {
            if (vFriends[i].equalsIgnoreCase(name.toString())) {
                primerOrden = true;
                return 1;
            } // amigos de primer orden
        }

        for (int i = 0; i < vAmigos.length; i++) {
            for (int j = 0; j < vFriends.length; j++) {
                if (vAmigos[i].equalsIgnoreCase(vFriends[j])) {
                    segundoOrden = true;
                    return 2; // los dos foaf son amigos de segundo orden (un amigo en común)
                }
            }
        }

        // si llega aqui, no es ni primer orden ni segundo orden
        //leemos todos los amigos y vemos si hay alguno en común para nivel 3
       

        String[] fullFriends = null;
        for (int i = 0; i < vFriends.length; i++) {
           // System.out.println("fff:" + vFriends[i]);
           fullFriends = procesaFoaf(vFriends[i]);

        }
        
        for(int i = 0; i < fullFriends.length; i++){
            System.out.println("FRIEND: " + fullFriends[i]);
        }
        
        for(int i = 0; i < vAmigos.length; i++){
            for(int j = 0; j < fullFriends.length; j++){
                if(vAmigos[i].equalsIgnoreCase(fullFriends[j])) {
                    tercerOrden = true;
                    return 3;
                }
            }
        }
        
        if(!primerOrden && !segundoOrden && !tercerOrden) return 4;

       return -1;

    }

    public static String[] procesaFoaf(String amigo) throws IOException {
        
        boolean esFichero = true;
        String name = "";
        String[] fullFriends = null;
        
        for(int i = 0; i < ficheros.size(); i++){ 
            name = getMainName(ficheros.get(i).toString());
         //  System.out.println(name + "________" +  amigo);
            if(name.equalsIgnoreCase(amigo)){ // si es el nombre correcto, leemos todos sus amigos
              //  System.out.println("NOMBRE: " +ficheros.get(i).toString() );
                fullFriends = getAllFriends(ficheros.get(i).toString());
            }
        }

        
        
        return fullFriends;
        
    }
    
    
    public static String[] getAllFriends(String fichero) throws IOException{
     
        
        
         String queryString
                = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
                + "SELECT ?friendName "
                + "WHERE {"
                + "?doc foaf:primaryTopic ?me ."
                + "?me foaf:knows ?friend ."
                + "?friend foaf:name ?friendName"
                + "}";

        ResultSet results1 = query(queryString, new File(fichero));

        ArrayList<String> amigos = new ArrayList<>();

        while (results1.hasNext()) {

            QuerySolution qsol = results1.next();
            RDFNode friendName = qsol.get("friendName");

            amigos.add(friendName.toString());
        }
        
        String[] friends = amigos.toArray(new String[0]); 
      
        
        return friends;
     }
    
    
    
    public static String getMainName(String fichero) throws IOException{
        
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

    static private ResultSet query(String queryString, File file) throws IOException {

        InputStream in = new FileInputStream(new File(file.toString()));

        // Create an empty in-memory model and populate it from the graph
        Model model = ModelFactory.createMemModelMaker().createModel(null, false);
        model.read(in, null); // null base URI, since model URIs are absolute
        in.close();

        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results 
        //ResultSetFormatter.out(System.out, results, query);
        return results;
    }

    public static void main(String[] args) throws IOException {
        
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

        File file1 = new File("foaf1.rdf");
        File file2 = new File("foaf5.rdf");

        int distancia = 0;

        distancia = findShortestPath(file1, file2);
        System.out.println(distancia);

    }

}
