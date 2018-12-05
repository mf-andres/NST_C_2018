

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String url = req.getParameter("url");
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

				if(url == null)
					printError(out, res, "Parámetros incorrectos para la pantalla response1");
				String response1 = request1(url);
				printResponse1(out, res, response1, url);
				break;

			case "response2":

				if(url == null || field == null || level == null)
					printError(out, res, "Parámetros incorrectos para la pantalla response2");
				String[] response2 = request2(url, field, level);
				printResponse2(out, res, response2, url, field, level);
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
		
		int grade;
		
		//aqui hay que poner la invo TODO
		grade = 1;
		
		return grade;
	}

	private String[] request2(String url, String field, String level) {
		
		String response2String;
		
		if(field.equals("")) {
			field = "None";
		}
		
		//aqui hay que poner la invo TODO
		response2String = "esto#es#un#ejemplo#";
		
		String[] response2Array = parseRequest2(response2String);
		
		return response2Array;
	}

	private String[] parseRequest2(String response2String) {

		String[] parts = response2String.split("#");
		
		for(String part : parts) {
			System.out.println("part " + part);
		}
		
		return parts;
	}

	private String request1(String url) {
		
		//aquí hay que poner la invocación
		
		String xmlCode;
		
		//aqui hay que poner la invo TODO
		xmlCode = "De momento este es el código xml jajajaj";
		
		return xmlCode;
	}

	private void printRequest(PrintWriter out, HttpServletResponse res) {
		
		printHeader(out, res);
		
		out.println("<h1> Response 1 </h1>");
		
		//form req1
		out.println("<form action=\"" + this.getServletName() + "\">");


		out.println("<h3> request 1 </h3>");
		out.println("<input type=\"text\" name=\"pantalla\" value=\"response1\"  hidden>");

		out.println("url");
		out.println("<input type=\"text\" name=\"url\"><br>");
		
		out.println("<input type=\"submit\" value=\"Send\">");

		out.println("</form>");
		//form req1 end
		
		
		//form req2
		out.println("<form action=\"" + this.getServletName() + "\">");

		out.println("<h3> request 2 </h3>");
		out.println("<input type=\"text\" name=\"pantalla\" value=\"response2\"  hidden>");
		
		out.println("url");
		out.println("<input type=\"text\" name=\"url\"><br>");
		
		out.println("field");
		out.println("<input type=\"text\" name=\"field\"><br>");
		
		out.println("level");
		out.println("<input type=\"text\" name=\"level\"><br>");

		out.println("<input type=\"submit\" value=\"Send\">");

		out.println("</form>");
		//form req2 end
		
		
		//form req3
		out.println("<form action=\"" + this.getServletName() + "\">");

		out.println("<h3> request 3 </h3>");
		out.println("<input type=\"text\" name=\"pantalla\" value=\"response3\"  hidden>");
		
		out.println("name1");
		out.println("<input type=\"text\" name=\"name1\"><br>");
		
		out.println("name2");
		out.println("<input type=\"text\" name=\"name2\"><br>");
		
		out.println("<input type=\"submit\" value=\"Send\">");

		out.println("</form>");
		//form req3 end
		
		printFooter(out, res);
	}

	private void printResponse1(PrintWriter out, HttpServletResponse res, String response1, String url) {
		
		printHeader(out, res);
		
		out.println("<h1> Response 1 </h1>");
		
		//form req1
		out.println("<form action=\"" + this.getServletName() + "\">");


		out.println("<h3> request 1 </h3>");
		out.println("<input type=\"text\" name=\"pantalla\" value=\"response1\"  hidden>");

		out.println("url");
		out.println("<input type=\"text\" name=\"url\"><br>");
		
		out.println("<input type=\"submit\" value=\"Send\">");

		out.println("</form>");
		//form req1 end
		
		//returned data from request1
		out.println("<textarea>");
		
		out.println(response1);
		
		out.println("</textarea>");
		
		
		//form req2
		out.println("<form action=\"" + this.getServletName() + "\">");

		out.println("<h3> request 2 </h3>");
		out.println("<input type=\"text\" name=\"pantalla\" value=\"response2\"  hidden>");
		
		out.println("url");
		out.println("<input type=\"text\" name=\"url\"><br>");
		
		out.println("field");
		out.println("<input type=\"text\" name=\"field\"><br>");
		
		out.println("level");
		out.println("<input type=\"text\" name=\"level\"><br>");

		out.println("<input type=\"submit\" value=\"Send\">");

		out.println("</form>");
		//form req2 end
		
		
		//form req3
		out.println("<form action=\"" + this.getServletName() + "\">");

		out.println("<h3> request 3 </h3>");
		out.println("<input type=\"text\" name=\"pantalla\" value=\"response3\"  hidden>");
		
		out.println("name1");
		out.println("<input type=\"text\" name=\"name1\"><br>");
		
		out.println("name2");
		out.println("<input type=\"text\" name=\"name2\"><br>");
		
		out.println("<input type=\"submit\" value=\"Send\">");

		out.println("</form>");
		//form req3 end
		
		printFooter(out, res);
	}
	
//	private void printResponse1(PrintWriter out, HttpServletResponse res) {
//		// TODO Auto-generated method stub
//		
//		//construye la tabla
//		String[] cabeceras;
//		String[][] valores;
//		
//		cabeceras = new String[]{"Campo", "Valores"};
//		
//		String[] array1 = new String[] {"Nombre", "Beatriz"};
//		String[] array2 = new String[] {"Amigos", "Manuel, Antón"};
//		
//		valores = new String[][]{array1, array2};
//		
//		//print
//		printHeader(out, res);
//		
//		out.println("<h1> Response 1 </h1>");
//		
//		printTable(out, res, cabeceras, valores);
//		
//		printToRequestButton(out, res);
//		
//		printFooter(out, res);
//	}

	private void printResponse2(PrintWriter out, HttpServletResponse res, String[] response2, String url, String field, String level) {

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
		
		out.println("<h1> Response 2 </h1>");

		out.println("<h3> For: " + url + " " + field + " " + level + " </h3>");
		
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
		
		out.println("<h1> Response 3 </h1>");
		out.println("<h3> For: " + name1 + " " + name2 + " </h3>");
		out.println("<h3> Grado: " + grado + " </h3>");
		
		printToRequestButton(out, res);
		
		printFooter(out, res);
	}

	private void printError(PrintWriter out, HttpServletResponse res, String error) {
		
		printHeader(out, res);
		
		out.println("<h1> Error </h1>");
		out.println("<h3> " + error + " </h3>");

		printToRequestButton(out, res);
		
		printFooter(out, res);

		return;
	}

	private void printToRequestButton(PrintWriter out, HttpServletResponse res) {
		
		out.println("<form action=\"" + this.getServletName() + "\">");

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
