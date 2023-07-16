package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.Especialidad;
import dominio.Medico;
import negocio.NegocioEspecialidad;
import negocio.NegocioMedico;
import negocio.NegocioReporte;
import negocioImpl.NegocioEspecialidadImpl;
import negocioImpl.NegocioMedicoImpl;
import negocioImpl.NegocioReporteImpl;

@WebServlet("/ServletReporte")
public class ServletReporte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	NegocioReporte negReporte = new NegocioReporteImpl();
	NegocioEspecialidad negEsp = new NegocioEspecialidadImpl();
	NegocioMedico negMedico = new NegocioMedicoImpl();
	
	
    public ServletReporte() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {

			ArrayList<Especialidad> especialidades = negEsp.cargarEspecialidades();
			ArrayList<Medico> medicos = negMedico.obtenerMedicos();			
			
			request.setAttribute("especialidades", especialidades);
			request.setAttribute("medicos", medicos);
			int estado1 = 0;
			int estado2 = 0;
			String btnObtenerReporte = request.getParameter("btnObtenerReporte");
			String btnReporte2 = request.getParameter("btnReporte2");
			
			
			if (btnObtenerReporte != null) {
				System.out.println("PRIMER BOTON");
				// REPORTE 1
				String especialidad = request.getParameter("especialidad");
				String fechaInicio = request.getParameter("fechaInicio");
				String fechaFin = request.getParameter("fechaFin");
				int cantPacientes = negReporte.obtenerCantPacientesXEspecialidad(especialidad, fechaInicio, fechaFin);
				if (cantPacientes > 0) {
					estado1 = 1;
					estado2 = 0;
					request.setAttribute("estado1", estado1);
					request.setAttribute("estado2", estado2);
					request.setAttribute("especialidad", especialidad);
					request.setAttribute("fechaInicio", fechaInicio);
					request.setAttribute("fechaFin", fechaFin);
					request.setAttribute("cantPacientes", cantPacientes);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/Reporte.jsp");
					dispatcher.forward(request, response);
				} else {
					estado1 = 2;
					estado2 = 0;
					request.setAttribute("estado1", estado1);
					request.setAttribute("estado2", estado2);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/Reporte.jsp");
					dispatcher.forward(request, response);
				}

			} else if (btnReporte2 != null) {
				estado1 = 0;
				boolean reporte2 = false;
				request.setAttribute("estado1", estado1);
				System.out.println("BOTOOON 2 ");
				System.out.println(request.getParameter("slcmedico"));
				System.out.println(request.getParameter("mes"));
				System.out.println(request.getParameter("anio"));

				int idMedico = Integer.parseInt(request.getParameter("slcmedico"));
				String mes = request.getParameter("mes");
				String anio = request.getParameter("anio");
				int idEstado = Integer.parseInt(request.getParameter("idEstado"));

				int cantTurnos = negReporte.obtenerTurnosXMedicoXfecha(idMedico, mes, anio, idEstado);
				if (cantTurnos > 0) {
					estado2 = 1;

					String nombreMedico = "";
					String apellidoMedico = "";

					for (Medico medico : medicos) {
						if (medico.getIdMedico() == idMedico) {
							nombreMedico = medico.getNombre();
							apellidoMedico = medico.getApellido();
						}
					}
					request.setAttribute("estado2", estado2);
					request.setAttribute("estado1", estado1);
					request.setAttribute("nombreMedico", nombreMedico);
					request.setAttribute("apellidoMedico", apellidoMedico);
					request.setAttribute("mes", mes);
					request.setAttribute("anio", anio);
					request.setAttribute("idEstado", idEstado);
					request.setAttribute("cantTurnos", cantTurnos);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/Reporte.jsp");
					dispatcher.forward(request, response);
				} else {
					estado2 = 2;
					estado1 = 0;
					request.setAttribute("estado2", estado2);
					request.setAttribute("estado1", estado1);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/Reporte.jsp");
					dispatcher.forward(request, response);

				}
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/Reporte.jsp");
				dispatcher.forward(request, response);
			}			
		}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
	}
}





