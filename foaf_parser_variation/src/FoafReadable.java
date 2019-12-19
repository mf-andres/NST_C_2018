
import java.io.File;
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
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;

public class FoafReadable {

    // representacion de los foaf en forma de listas que incorporan aquellos
    // otros foaf que estan unicamente a distancia 1
    
    private static ArrayList<String> FoafDaniel = new ArrayList<>();
    private static ArrayList<String> FoafAndres = new ArrayList<>();
    private static ArrayList<String> FoafManuel = new ArrayList<>();
    private static ArrayList<String> FoafLuis = new ArrayList<>();
    private static ArrayList<String> FoafJuan = new ArrayList<>();
    private static ArrayList<String> FoafIago = new ArrayList<>();
    private static ArrayList<String> FoafAndrea = new ArrayList<>();
    private static ArrayList<String> FoafMarta = new ArrayList<>();
    private static ArrayList<String> FoafAbel = new ArrayList<>();
    private static ArrayList<String> FoafJaime = new ArrayList<>();

    public static void main(String args[]) {

        /*
        FileManager.get().addLocatorClassLoader(FoafReadable.class.getClassLoader());
        Model model = FileManager.get().loadModel("C:\\Users\\Manuel\\Documents\\NetBeansProjects\\FoafReadable\\src\\foaf1.rdf");
        model.write(System.out, "RDF/XML"); */ // obtener un modelo de los datos en otro formato de representacion
        
        ArrayList<String> foafs = new ArrayList();

        File folder = new File("C:\\Users\\Manuel\\Documents\\NetBeansProjects\\FoafReadable\\src"); //pendiente de actualizacion
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && file.toString().contains(".rdf")) {
                foafs.add(file.getName());
            }
        }

        for (int i = 0; i < foafs.size(); i++) {

            String route = foafs.get(i).toString();
            readFoafs(route);
        }
        
       
    }

    static void readFoafs(String route) {

      
        String name = route.substring(4, route.indexOf("."));  // pertenencia del foaf
     

        FileManager.get().addLocatorClassLoader(FoafReadable.class.getClassLoader());
        Model model = FileManager.get().loadModel(route);
        String queryString
                = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
                + "SELECT * WHERE { "
                + " ?person foaf:name ?x ."
                + "}";

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);

        try {

           

            ResultSet resultados = qexec.execSelect();
            while (resultados.hasNext()) {
                QuerySolution sol = resultados.nextSolution();
                Literal nombre = sol.getLiteral("x");
                
             
             
             if(name.equalsIgnoreCase("Daniel") && !nombre.toString().contains(name)) FoafDaniel.add(nombre.toString());
             if(name.equalsIgnoreCase("Andres") && !nombre.toString().contains(name)) FoafAndres.add(nombre.toString());
             if(name.equalsIgnoreCase("Manuel") && !nombre.toString().contains(name)) FoafManuel.add(nombre.toString());
             if(name.equalsIgnoreCase("Luis") && !nombre.toString().contains(name)) FoafLuis.add(nombre.toString());
             if(name.equalsIgnoreCase("Juan") && !nombre.toString().contains(name)) FoafJuan.add(nombre.toString());
             if(name.equalsIgnoreCase("Iago") && !nombre.toString().contains(name)) FoafIago.add(nombre.toString());
             if(name.equalsIgnoreCase("Marta") && !nombre.toString().contains(name)) FoafMarta.add(nombre.toString());
             if(name.equalsIgnoreCase("Andrea") && !nombre.toString().contains(name)) FoafAndrea.add(nombre.toString());
             if(name.equalsIgnoreCase("Abel") && !nombre.toString().contains(name)) FoafAbel.add(nombre.toString());
             if(name.equalsIgnoreCase("Jaime") && !nombre.toString().contains(name)) FoafJaime.add(nombre.toString());

                
                
            }

        } finally {
            qexec.close();
        }
    }

}
