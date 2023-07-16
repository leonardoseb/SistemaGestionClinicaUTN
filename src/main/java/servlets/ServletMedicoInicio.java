package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.Paciente;
import dominio.Turno;
import negocio.NegocioPaciente;
import negocio.NegocioTurno;
import negocioImpl.NegocioPacienteImpl;
import negocioImpl.NegocioTurnoImpl;


@WebServlet("/ServletMedicoInicio")
public class ServletMedicoInicio extends HttpServlet{
	private static final long serialVersionUID = 1L;

    public ServletMedicoInicio() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		NegocioTurno negocioTurno = new NegocioTurnoImpl();
		NegocioPaciente negocioPaciente = new NegocioPacienteImpl();
		Paciente paciente = new Paciente();
		int idMedico = 0;
		if(request.getSession().getAttribute("idUsuario") != null) {			
			idMedico = (int)request.getSession().getAttribute("idUsuario");
		}
		ArrayList<Turno> listaMisTurnos = (ArrayList<Turno>) negocioTurno.readPorMedico(idMedico);
		request.setAttribute("listaMisTurnos", listaMisTurnos);
		RequestDispatcher rd;
		
		
		
		if(request.getParameter("list") != null) {
		
			request.setAttribute("listaMisTurnos", listaMisTurnos);
			rd = request.getRequestDispatcher("/MedicoInicio.jsp");
			rd.forward(request, response);
			
		}
		
		if(request.getParameter("Modificar") != null && request.getParameter("Pax") != null) {
			Turno tu = new Turno();
			Paciente pax = new Paciente();
			int idTurno;
			idTurno = Integer.parseInt(request.getParameter("Modificar"));
			String dni;
			dni = request.getParameter("Pax");
			tu = negocioTurno.devuelveTurno(idTurno);
			request.setAttribute("Turno", tu);
			pax = negocioPaciente.mostrarPaciente(dni);
			request.setAttribute("Paciente", pax);
			request.setAttribute("ModalEdit", true);
			request.getRequestDispatcher("/MedicoInicio.jsp").forward(request, response);
			
		}
		
		if(request.getParameter("Detalle") != null) {
			
			
			String dni = request.getParameter("Detalle");
			paciente = negocioPaciente.mostrarPaciente(dni);
			String observaciones = new String(); 
			
			observaciones = negocioTurno.readAllObservacionesByIdPaciente(paciente.getIdPaciente());
			
			request.setAttribute("Paciente", paciente);
			request.setAttribute("detallePaciente", true);
			request.setAttribute("observacionesPaciente", observaciones);
			rd = request.getRequestDispatcher("/MedicoInicio.jsp");
			rd.forward(request, response);
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd;
		NegocioTurno negocioTurno = new NegocioTurnoImpl();
		int idMedico = 0;
		if(request.getSession().getAttribute("idUsuario") != null) {			
			idMedico = (int)request.getSession().getAttribute("idUsuario");
		}
		
		if(request.getParameter("actualizarTurno") != null) {
			int idTurno, estado;
			String observacion;
			idTurno = Integer.parseInt(request.getParameter("lblidTurno"));
			estado = Integer.parseInt(request.getParameter("lblEstado"));
			observacion = request.getParameter("txtObservacion");
			if(negocioTurno.update2(idTurno, estado, observacion) == true ) {
				request.setAttribute("exito", true);
			}
			NegocioTurno tNeg = new NegocioTurnoImpl();
			
			ArrayList<Turno> listaMisTurnos = (ArrayList<Turno>) tNeg.readPorMedico(idMedico);
			request.setAttribute("listaMisTurnos", listaMisTurnos);
			
		}
		
		
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

					
					List<Turno> listaTurnoFiltros = negocioTurno.filtroFechaEstado(idEstado, fechaFiltro, idMedico);
					request.setAttribute("listaMisTurnos", listaTurnoFiltros);			
				} else {

					
					
					List<Turno> listaTurnoFecha =	negocioTurno.filtroFecha(fechaFiltro, idMedico);
					request.setAttribute("listaMisTurnos", listaTurnoFecha);

				}

			} else {

				if (idEstado != 0) {

				ArrayList<Turno> listaTurnoEstado =	(ArrayList<Turno>) negocioTurno.filtroEstado(idEstado, idMedico);
				request.setAttribute("listaMisTurnos", listaTurnoEstado);
				} else {

					ArrayList<Turno> listaMisTurnos = (ArrayList<Turno>) negocioTurno.readPorMedico(idMedico);
					request.setAttribute("listaMisTurnos", listaMisTurnos);
					// LISTA SIN FILTRO
				}

			}

		}
		
		request.getRequestDispatcher("/MedicoInicio.jsp").forward(request, response);	
		
		
	}

}
