package servlets;

import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dominio.Localidad;
import dominio.Nacionalidad;
import dominio.Paciente;
import negocio.NegocioPaciente;
import negocioImpl.NegocioPacienteImpl;

@WebServlet("/ServletAltaPaciente")
public class ServletAltaPaciente extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	NegocioPaciente negocioPaciente = new NegocioPacienteImpl();
	
	public ServletAltaPaciente() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{			
		
		HttpSession session = request.getSession(true);	    
        session.setAttribute("txtNombre",request.getParameter("var1")); 
        response.sendRedirect("AltaPaciente.jsp"); //logged-in page   
		
		
		if(request.getParameter("btnAgregarPaciente")!=null) {
			
			Paciente paciente = new Paciente();
			
			paciente.setDni(request.getParameter("txtDni"));
			paciente.setNombre(request.getParameter("txtNombre"));
			paciente.setApellido(request.getParameter("txtApellido"));
			paciente.setSexo(request.getParameter("sexo").charAt(0));
			paciente.setNacionalidad(new Nacionalidad(Integer.parseInt(request.getParameter("comboNacionalidad"))));
			
			String fechaStr = request.getParameter("dFecha");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date fechaNacimiento = sdf.parse(fechaStr);
				paciente.setFechaNacimiento(fechaNacimiento);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			paciente.setDireccion(request.getParameter("txtDireccion"));
			paciente.setLocalidad(new Localidad(Integer.parseInt(request.getParameter("comboLocalidad"))));
			paciente.setTelefono(request.getParameter("telefono"));
			paciente.setEmail(request.getParameter("txtCorreo"));
			
			boolean estado = true;
			estado = negocioPaciente.insertar(paciente);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/GestionPacientes.jsp?Param=list");
			dispatcher.forward(request, response);
		}
	}
	
}
