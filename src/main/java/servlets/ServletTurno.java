package servlets;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.Especialidad;
import dominio.Medico;
import dominio.Paciente;
import dominio.Turno;
import dominio.Usuario;
import exceptions.PacienteNotFoundException;
import negocio.NegocioEspecialidad;
import negocio.NegocioMedico;
import negocio.NegocioMedicoxdia;
import negocio.NegocioPaciente;
import negocio.NegocioTurno;
import negocioImpl.NegocioEspecialidadImpl;
import negocioImpl.NegocioMedicoImpl;
import negocioImpl.NegocioMedicoxdiaImpl;
import negocioImpl.NegocioPacienteImpl;
import negocioImpl.NegocioTurnoImpl;



@WebServlet("/ServletTurno")
public class ServletTurno extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletTurno() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		NegocioMedico negocioMedico = new NegocioMedicoImpl();
		NegocioTurno negocioTurno = new NegocioTurnoImpl();
		NegocioEspecialidad negocioEspecialidad = new NegocioEspecialidadImpl();	

		List<Especialidad> listaEspecialidad = (ArrayList<Especialidad>) negocioEspecialidad.readAll();
		List<Medico> listaMedico = (ArrayList<Medico>) negocioMedico.readAll();
		List<Turno> listaTurno = (ArrayList<Turno>) negocioTurno.readAll();

		RequestDispatcher dispatcher;

		if (request.getParameter("Param") != null) {
			
			List<Turno> listaTurno2 = (ArrayList<Turno>) negocioTurno.readAll();
			
			//request.setAttribute("listaTurnos", listaTurno);
			request.setAttribute("listaEspecialidad", listaEspecialidad);		
			request.setAttribute("listaMedico", listaMedico);
			request.getSession().setAttribute("listaTurnos", listaTurno2);
			
			request.setAttribute("exito", false);	
			
			//rd = request.getRequestDispatcher("/ListaTurnos.jsp");
			dispatcher = request.getRequestDispatcher("/AdminInicio.jsp");
			dispatcher.forward(request, response);
		}
		
		/**
		 * Abre ventana para Asignacion de un Turno.
		 */
		if(request.getParameter("AsignarTurno") != null) {
			
			Turno turno = new Turno();
			int id = Integer.parseInt(request.getParameter("AsignarTurno"));
			turno = negocioTurno.devuelveTurno(id);
			request.getSession().setAttribute("Turno", turno);
			request.setAttribute("exito", true);	
			dispatcher = request.getRequestDispatcher("/AsignacionTurnos.jsp");
			dispatcher.forward(request, response);
		}
		
		/**
		 * Modal para Detalle de Turno
		 */
		if(request.getParameter("VerDetalleTurno") != null && request.getParameter("Paciente") != null) {
						
			Turno turno = new Turno();
			Paciente paciente = new Paciente();
			NegocioPaciente negocioPaciente = new NegocioPacienteImpl();
			
			int idTurno;
			idTurno = Integer.parseInt(request.getParameter("VerDetalleTurno"));
			
			String dni;
			dni = request.getParameter("Paciente");
			
			turno = negocioTurno.devuelveTurno(idTurno);
			request.setAttribute("Turno", turno);
			
			paciente = negocioPaciente.mostrarPaciente(dni);
			
			request.setAttribute("Paciente", paciente);
			request.setAttribute("ModalEdit", true);
			
	//		request.getRequestDispatcher("/ListaTurnos.jsp").forward(request, response);
			request.getRequestDispatcher("/AdminInicio.jsp").forward(request, response);
		
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Medico med = new Medico();

		NegocioMedicoxdia dxmNeg = new NegocioMedicoxdiaImpl();
		NegocioTurno tneg = new NegocioTurnoImpl();
		Usuario usu = new Usuario();
		List<Turno> listaAgenda = new ArrayList<Turno>();
		NegocioPaciente pNeg = new NegocioPacienteImpl();
		boolean existe = false;
		Turno turno = new Turno();
		NegocioMedico meNeg = new NegocioMedicoImpl();
		
		NegocioEspecialidad esNeg = new NegocioEspecialidadImpl();
		
		List<Especialidad> listaEspecialidad = (ArrayList<Especialidad>) esNeg.readAll();
		List<Medico> listaMedico = (ArrayList<Medico>) meNeg.readAll();

		/**
		 * Reservar un turno
		 */
		if(request.getParameter("reservar") != null) {
			try {
			pNeg.existePaciente2(request.getParameter("txtDni"));
			turno = (Turno)request.getSession().getAttribute("Turno");
			tneg.agendarTurno(request.getParameter("txtDni"), turno);
			request.setAttribute("exito", true);
			List<Turno> listaTurno2 = (ArrayList<Turno>) tneg.readAll();
			request.getSession().setAttribute("listaTurnos", listaTurno2);
		}
		catch (PacienteNotFoundException e) {
			request.setAttribute("mensaje", e.getMessage());
			request.getRequestDispatcher("AsignacionTurnos.jsp").forward(request, response);
			return;
		}
	}				
		
		/**
		 * Liberacion de un turno ocupado.
		 */
		if(request.getParameter("LiberarTurno") != null) {
			int id = Integer.parseInt(request.getParameter("turnoId"));
			if (tneg.liberarTurno(id)) {
				request.setAttribute("exito3", true);
				List<Turno> listaTurno = (ArrayList<Turno>) tneg.readAll();
				request.getSession().setAttribute("listaTurnos", listaTurno);
			}
		}
		
		/**
		 * Filtrado
		 */
		if (request.getParameter("btnFiltrar") != null) {

			int idEstado = Integer.parseInt(request.getParameter("slcEstado"));

			if (request.getParameter("fechaFiltro") != "") {

				SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
				Date dateFormateado = new Date();
				try {
					dateFormateado = formato.parse(request.getParameter("fechaFiltro"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				java.sql.Date fechaFiltro = new java.sql.Date(dateFormateado.getTime());

				if (idEstado != 0) {

					
					List<Turno> listaTurnoFiltros =tneg.filtroFechaEstado(idEstado, fechaFiltro);
					request.getSession().setAttribute("listaTurnos", listaTurnoFiltros);
					// CONSULTA BUSQUEDA X DOS FILTROS
				} else {									
					List<Turno> listaTurnoFecha =	tneg.filtroFecha(fechaFiltro);
					request.getSession().setAttribute("listaTurnos", listaTurnoFecha);
					// CONSULTA BUSQUEDA SOLO POR FECHA
				}

			} else {

				if (idEstado != 0) {

				List<Turno> listaTurnoEstado =	tneg.filtroEstado(idEstado);
				request.getSession().setAttribute("listaTurnos", listaTurnoEstado);
				} else {

					List<Turno> listaTurno = (ArrayList<Turno>) tneg.readAll();
					request.getSession().setAttribute("listaTurnos", listaTurno);	
					// LISTA SIN FILTRO
				}

			}

		}
				
		request.getRequestDispatcher("/AdminInicio.jsp").forward(request, response);	
			
	}	
}
