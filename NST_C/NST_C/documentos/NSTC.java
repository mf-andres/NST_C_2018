package book;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;

/**
 * Servlet implementation class NSTC
 */
@WebServlet("/NSTC")
public class NSTC extends HttpServlet {

	public void init() throws ServletException {
		
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException{

		//encoding
		res.setCharacterEncoding("UTF-8");

		//output
		PrintWriter out = res.getWriter();

		//número de parametros
		Enumeration<String> paramE = req.getParameterNames();
		ArrayList<String> paramL = new ArrayList<String>();

		while(paramE.hasMoreElements()){

			paramL.add(paramE.nextElement());
		}

		int numParam = paramL.size();

		//parámetros
		String pantalla = req.getParameter("pantalla");
		String name = req.getParameter("name");
		String field = req.getParameter("field");
		String level = req.getParameter("level");
		String name1 = req.getParameter("name1");
		String name2 = req.getParameter("name2");

		if(numParam == 0) {

			printRequest(out, res);

		} else {

			switch(pantalla) {

			case "request":

				printRequest(out, res);
				break;

			case "response1":

				if(name == null)
				printError(out, res, "Parámetros incorrectos para la pantalla response1");
				String response1 = request1(name);
				printResponse1(out, res, response1, name);
				break;

			case "response2":

				if(name == null || field == null || level == null)
					printError(out, res, "Parámetros incorrectos para la pantalla response2");
				String[] response2 = request2(name, field, level);
				printResponse2(out, res, response2, name, field, level);
				break;

			case "response3":

				if(name1 == null || name2 == null)
					printError(out, res, "Parámetros incorrectos para la pantalla response3");
				int response3 = request3(name1, name2);
				printResponse3(out, res, response3, name1, name2);
				break;

			default:

				printError(out, res, "Se ha intentado acceder a una pantalla inexistente");
				break;

			}
		}

	}

	private int request3(String name1, String name2) {
		
		int grade=0;
		ClientConfig config = new ClientConfig();
		
		Client client = ClientBuilder.newClient(config);
		
		WebTarget target = client.target("http://localhost:8080/RestDemo/Consultas");
		try {
		grade = target.path(name1).path(name2).request().accept(MediaType.TEXT_PLAIN).get(Integer.class);
		}catch(Exception e) {
			grade=0;
		}
		
		return grade;
	}

	private String[] request2(String name, String field, String level) {
		
		String response2String;
		String[] response2Array;
		if(field.equals("")) {
			field = "None";
		}
		ClientConfig config = new ClientConfig();
		
		Client client = ClientBuilder.newClient(config);
		
		WebTarget target = client.target("http://localhost:8080/RestDemo/Consultas");
		try {
		response2String = target.path(name).path(field).path(level).request().accept(MediaType.TEXT_PLAIN).get(String.class);
		response2Array = parseRequest2(response2String);
		}catch(Exception e) {
		response2Array = parseRequest2("Consulta#No#Existente");
		}
		
		return response2Array;
	}

	private String[] parseRequest2(String response2String) {
		String[] parts = response2String.split("#");
		return parts;
	}

	private String request1(String name) {
		
		ClientConfig config = new ClientConfig();
		
		Client client = ClientBuilder.newClient(config);
		
		WebTarget target = client.target("http://localhost:8080/RestDemo/Consultas");
		
		String xmlCode;
		
		//aqui hay que poner la invo TODO
		xmlCode = target.path(name).request().accept(MediaType.TEXT_XML).get(String.class);
		
		return xmlCode;
	}

	private void printRequest(PrintWriter out, HttpServletResponse res) {
		
		printHeader(out, res);
		
		out.println("<h1>Consulta de FOAF</h1>");
		
		//form req1
		out.println("<form action=\"NSTC\">");


		out.println("<h3>Consulta1</h3>");
		out.println("<input type=\"text\" name=\"pantalla\" value=\"response1\"  hidden>");

		out.println("Nombre");
		out.println("<input type=\"text\" name=\"name\"><br>");
		
		out.println("<input type=\"submit\" value=\"Send\">");

		out.println("</form>");
		//form req1 end
		
		
		//form req2
		out.println("<form action=\"NSTC\">");

		out.println("<h3>Consulta2</h3>");
		out.println("<input type=\"text\" name=\"pantalla\" value=\"response2\"  hidden>");
		
		out.println("Nombre");
		out.println("<input type=\"text\" name=\"name\"><br>");
		
		out.println("Campo");
		out.println("<input type=\"text\" name=\"field\"><br>");
		
		out.println("Nivel");
		out.println("<input type=\"text\" name=\"level\"><br>");

		out.println("<input type=\"submit\" value=\"Send\">");

		out.println("</form>");
		//form req2 end
		
		
		//form req3
		out.println("<form action=\"NSTC\">");

		out.println("<h3>Consulta3</h3>");
		out.println("<input type=\"text\" name=\"pantalla\" value=\"response3\"  hidden>");
		
		out.println("Nombre 1");
		out.println("<input type=\"text\" name=\"name1\"><br>");
		
		out.println("Nombre 2");
		out.println("<input type=\"text\" name=\"name2\"><br>");
		
		out.println("<input type=\"submit\" value=\"Send\">");

		out.println("</form>");
		//form req3 end
		
		printFooter(out, res);
	}

	private void printResponse1(PrintWriter out, HttpServletResponse res, String response1, String name) {
		
		printHeader(out, res);
		
		out.println("<h1>Consulta de FOAF</h1>");
		
		//form req1
		out.println("<form action=\"NSTC\">");


		out.println("<h3>Consulta1</h3>");
		out.println("<input type=\"text\" name=\"pantalla\" value=\"response1\"  hidden>");

		out.println("Nombre");
		out.println("<input type=\"text\" name=\"name\"><br>");
		
		out.println("<input type=\"submit\" value=\"Send\">");

		out.println("</form>");
		//form req1 end
		
		//returned data from request1
		out.println("<textarea style=\"margin: 0px; width: 541px; height: 326px;\">");
		out.println(response1);
		out.println("</textarea>");
		
		
		//form req2
		out.println("<form action=\"NSTC\">");

		out.println("<h3>Consulta2</h3>");
		out.println("<input type=\"text\" name=\"pantalla\" value=\"response2\"  hidden>");
		
		out.println("Nombre");
		out.println("<input type=\"text\" name=\"name\"><br>");
		
		out.println("Campo");
		out.println("<input type=\"text\" name=\"field\"><br>");
		
		out.println("Nivel");
		out.println("<input type=\"text\" name=\"level\"><br>");

		out.println("<input type=\"submit\" value=\"Send\">");

		out.println("</form>");
		//form req2 end
		
		
		//form req3
		out.println("<form action=\"NSTC\">");

		out.println("<h3>Consulta3</h3>");
		out.println("<input type=\"text\" name=\"pantalla\" value=\"response3\"  hidden>");
		
		out.println("Nombre1");
		out.println("<input type=\"text\" name=\"name1\"><br>");
		
		out.println("Nombre2");
		out.println("<input type=\"text\" name=\"name2\"><br>");
		
		out.println("<input type=\"submit\" value=\"Send\">");

		out.println("</form>");
		//form req3 end
		
		printFooter(out, res);
	}
	
	private void printResponse2(PrintWriter out, HttpServletResponse res, String[] response2, String name, String field, String level) {

		//construye la tabla
		String[] cabeceras;
		String[][] valores;
		
		cabeceras = new String[]{"Nombre"};
		valores = new String[response2.length][1];
		
		for (int i = 0; i < valores.length; i++) {
			valores[i][0] = response2[i];
		}
		
		//print
		printHeader(out, res);
		
		out.println("<h1>Consulta2</h1>");

		out.println("<h3>For:"+name+" "+field+" "+level+"</h3>");
		
		printTable(out, res, cabeceras,valores);
		
		printToRequestButton(out, res);
		
		printFooter(out, res);
	}

	private void printTable(PrintWriter out, HttpServletResponse res, String[] cabeceras, String[][] valores) {
		
		out.println("<table style=\"width:100%\">");
		
		//cabeceras
		out.println("<tr>");
		for(int c = 0; c < cabeceras.length; c++) {
			
			out.println("<th>" + cabeceras[c] + "</th>");
		}
		out.println("</tr>");
		
		//valores
		for(int f = 0; f < valores.length; f++) {
		
			out.println("<tr>");
			for(int v = 0; v < valores[f].length; v++) {
				
				out.println("<th>" + valores[f][v] + "</th>");
			}
			out.println("</tr>");
		}
		
		out.println("</table>");
		
	}

	private void printResponse3(PrintWriter out, HttpServletResponse res, int grado, String name1, String name2) {
		
		printHeader(out, res);
		
		out.println("<h1>Consulta3</h1>");
		out.println("<h3> For: " + name1 + " " + name2 + " </h3>");
		out.println("<h3> Grado: " + grado + " </h3>");
		
		printToRequestButton(out, res);
		
		printFooter(out, res);
	}

	private void printError(PrintWriter out, HttpServletResponse res, String error) {
		
		printHeader(out, res);
		
		out.println("<h1>Error</h1>");
		out.println("<h3>"+error+"</h3>");

		printToRequestButton(out, res);
		
		printFooter(out, res);

		return;
	}

	private void printToRequestButton(PrintWriter out, HttpServletResponse res) {
		
		out.println("<form action=\"NSTC\">");

		out.println("<input type=\"text\" name=\"pantalla\" value=\"request\"  hidden>");
		
		out.println("<input type=\"submit\" value=\"To request\">");

		out.println("</form>");
	}

	private void printHeader(PrintWriter out, HttpServletResponse res) {
		
		out.println("<html>");

		out.println("<head>");
		out.println("<title>NST User Interface</title>");
		out.println("<meta charset=\"UTF-8\">");
		
		out.println("</head>");

		out.println("<body>");
	}
	
    private void printFooter(PrintWriter out, HttpServletResponse res) {
		
		out.println("</body>");
		out.println("</html>");
	}
}