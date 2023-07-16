package servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.Especialidad;
import dominio.Localidad;
import dominio.Medico;
import dominio.Nacionalidad;
import dominio.Paciente;
import negocio.NegocioEspecialidad;
import negocio.NegocioLocalidad;
import negocio.NegocioMedico;
import negocio.NegocioNacionalidad;
import negocio.NegocioUsuario;
import negocioImpl.NegocioEspecialidadImpl;
import negocioImpl.NegocioLocalidadesImpl;
import negocioImpl.NegocioMedicoImpl;
import negocioImpl.NegocioNacionalidadImpl;
import negocioImpl.NegocioUsuarioImpl;

/**
 * Servlet implementation class ServletAltaMedico
 */
@WebServlet("/AltaMedico")
public class ServletAltaMedico extends HttpServlet {
	private static final long serialVersionUID = 1L;
	NegocioMedico negMed = new NegocioMedicoImpl();
	NegocioEspecialidad negEsp = new NegocioEspecialidadImpl();
	NegocioNacionalidad negocioNacionalidad = new NegocioNacionalidadImpl();
	NegocioLocalidad negocioLocalidad = new NegocioLocalidadesImpl();
	NegocioUsuario negocioUsuario = new NegocioUsuarioImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAltaMedico() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)	
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		  //RequestDispatcher dispatcher = request.getRequestDispatcher("ServletTurno?Param=list");
		  //request.getRequestDispatcher("AdminInicio?Param=list");
		  //dispatcher.forward(request, response);
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("btnAgregarMed") != null) {
			Paciente paciente = new Paciente();
			Medico medico = new Medico();
			int filas = 0;
			String[] dias = request.getParameterValues("dias");
			for (String dia : dias) {
				System.out.println("DIAS: " + dia);
			}
			String horario = request.getParameter("horarios");
			paciente.setDni(request.getParameter("txtDniMedico"));
			paciente.setNombre(request.getParameter("txtNombreMedico"));
			paciente.setApellido(request.getParameter("txtApellidoMedico"));
			paciente.setSexo(request.getParameter("sexo").charAt(0));
			paciente.setNacionalidad(new Nacionalidad(Integer.parseInt(request.getParameter("slcNacionalidad"))));
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			Date dateFormateado = new Date();
			try {
				dateFormateado = formato.parse(request.getParameter("txtFechaNacimiento"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			java.sql.Date date1 = new java.sql.Date(dateFormateado.getTime());
			paciente.setFechaNacimiento(date1);
			paciente.setDireccion(request.getParameter("txtDireccionMedico"));
			paciente.setLocalidad(new Localidad(Integer.parseInt(request.getParameter("slcLocalidad"))));
			paciente.setTelefono(request.getParameter("txtTelefonoMedico"));
			paciente.setEmail(request.getParameter("txtEmail"));
// 			medico.setIdMedico(negocioUsuario.obtenerUltimoUsuario());
			System.out.println("EMAIL: " + paciente.getEmail());
			// ultimo id de usuario

			medico.setIdMedico(negocioUsuario.getUltimoIdUsuario()+1);
			System.out.println(medico.getIdMedico());
			medico.setDni(paciente.getDni());
			String especialidad = request.getParameter("especialidad").trim();
			int idEspecialidad = Integer.parseInt(especialidad);
			Especialidad especialidadObj = new Especialidad(idEspecialidad, null);
			medico.setIdEspecialidad(especialidadObj);

			filas = negMed.agregarMedico(medico, paciente, dias, horario);
			if (filas == 1) {								
				/*
				 * RequestDispatcher dispatcher =
				 * request.getRequestDispatcher("ServletTurno?Param=list");
				 * System.out.println("filas == 1: " + "ServletTurno?Param=list");
				 * dispatcher.forward(request, response);
				 */
				
				response.sendRedirect("AdminInicio?Param=list");
			}
			else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("CargarMedico?Param=cargar");				
				dispatcher.forward(request, response);
			}

		}

	}

}
