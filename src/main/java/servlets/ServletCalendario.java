package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Dispatch;

import negocio.NegocioReporte;
import negocioImpl.NegocioReporteImpl;

/**
 * Servlet implementation class ServletCalendario
 */
@WebServlet("/ServletCalendario")
public class ServletCalendario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	NegocioReporte negRep = new NegocioReporteImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletCalendario() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//Getting the current date value
	      LocalDate currentdate = LocalDate.now();
	      int dia = currentdate.getDayOfMonth();
	      int mes = currentdate.getMonthValue();		
	      int anio = currentdate.getYear();

		
		if (request.getParameter("dia") != null) {dia = Integer.parseInt(request.getParameter("dia"));}
		if (request.getParameter("mes") != null) {mes = Integer.parseInt(request.getParameter("mes"));}
		if (request.getParameter("anio") != null) {anio = Integer.parseInt(request.getParameter("anio"));}
						
		boolean Estado = false;
		if (request.getParameter("infoMed") != null) {
			System.out.println("BOTON INFO MEDICO");

			System.out.println(dia + "  DIA");
			System.out.println(mes + "  MES");
			System.out.println(anio + "  anio");
			ArrayList<String> lista = negRep.obtenerListado(dia, mes, anio);
			if (lista != null) {
				Estado = true;
				System.out.println("TIENE VALOR ");
				request.setAttribute("lista", lista);
				request.setAttribute("estado", Estado);
			} else {
				System.out.println("LLEGO NULL");
				Estado = false;
				request.setAttribute("estado", Estado);
			}
		}
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/ReporteCalendario.jsp?year=" + anio + "&month=" + mes);
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
