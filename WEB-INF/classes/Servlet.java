import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;

public class Servlet extends HttpServlet {

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

		if(numParam == 0) {

			printRequest(out, res);

		} else {

			switch(pantalla) {

			case "request":

				printRequest(out, res);
				break;

			case "response1":

				printResponse1(out, res);
				break;

			case "response2":

				printResponse2(out, res);
				break;

			case "response3":

				printResponse3(out, res);
				break;

			default:

				printError(out, res, "Se ha intentado acceder a una pantalla inexistente");
				break;

			}
		}

	}

	private void printRequest(PrintWriter out, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
		printHeader(out, res);
		
		out.println("<h1> Response 1 </h1>");
		
		//form req1
		out.println("<form action=\"" + this.getServletName() + "\">");

		out.println("<input type=\"text\" name=\"pantalla\" value=\"response1\"  hidden>");

		out.println("url");
		out.println("<input type=\"text\" name=\"url\"><br>");
		
		out.println("<input type=\"submit\" value=\"Send\">");

		out.println("</form>");
		//form req1 end
		
		
		//form req2
		out.println("<form action=\"" + this.getServletName() + "\">");

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

	private void printResponse1(PrintWriter out, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
		//construye la tabla
		String[] cabeceras;
		String[][] valores;
		
		cabeceras = new String[]{"Campo", "Valores"};
		
		String[] array1 = new String[] {"Nombre", "Beatriz"};
		String[] array2 = new String[] {"Amigos", "Manuel, Antón"};
		
		valores = new String[][]{array1, array2};
		
		//print
		printHeader(out, res);
		
		out.println("<h1> Response 1 </h1>");
		
		printTable(out, res, cabeceras, valores);
		
		printToRequestButton(out, res);
		
		printFooter(out, res);
	}

	private void printResponse2(PrintWriter out, HttpServletResponse res) {
		// TODO Auto-generated method stub

		//construye la tabla
		String[] cabeceras;
		String[][] valores;
		
		cabeceras = new String[]{"Nombre"};
		
		String[] array1 = new String[] {"Jose"};
		String[] array2 = new String[] {"Luis"};
		
		valores = new String[][]{array1, array2};
		
		//print
		printHeader(out, res);
		
		out.println("<h1> Response 2 </h1>");
		
		printTable(out, res, cabeceras,valores);
		
		printToRequestButton(out, res);
		
		printFooter(out, res);
	}

	private void printTable(PrintWriter out, HttpServletResponse res, String[] cabeceras, String[][] valores) {
		// TODO Auto-generated method stub
		
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

	private void printResponse3(PrintWriter out, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
		int grado = 0;
		
		printHeader(out, res);
		
		out.println("<h1> Response 3 </h1>");
		out.println("<h3> Grado: " + grado + " </h3>");
		
		printToRequestButton(out, res);
		
		printFooter(out, res);
	}

	private void printError(PrintWriter out, HttpServletResponse res, String error) {
		// TODO Auto-generated method stub

		printHeader(out, res);
		
		out.println("<h1> Error </h1>");
		out.println("<h3> " + error + " </h3>");

		printToRequestButton(out, res);
		
		printFooter(out, res);

		return;
	}

	private void printToRequestButton(PrintWriter out, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
		out.println("<form action=\"" + this.getServletName() + "\">");

		out.println("<input type=\"text\" name=\"pantalla\" value=\"request\"  hidden>");
		
		out.println("<input type=\"submit\" value=\"To request\">");

		out.println("</form>");
	}

	private void printHeader(PrintWriter out, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
		out.println("<html>");

		out.println("<head>");
		out.println("<title>NST User Interface</title>");
		out.println("<meta charset=\"UTF-8\">");
		
		out.println("</head>");

		out.println("<body>");
	}

	private void printFooter(PrintWriter out, HttpServletResponse res) {
		// TODO Auto-generated method stub

		out.println("</body>");
		out.println("</html>");
	}

}
