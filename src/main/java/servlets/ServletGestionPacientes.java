package servlets;

import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.Localidad;
import dominio.Nacionalidad;
import dominio.Paciente;
import dominio.Provincia;
import negocio.NegocioLocalidad;
import negocio.NegocioNacionalidad;
import negocio.NegocioPaciente;
import negocio.NegocioProvincia;
import negocioImpl.NegocioLocalidadesImpl;
import negocioImpl.NegocioNacionalidadImpl;
import negocioImpl.NegocioPacienteImpl;
import negocioImpl.NegocioProvinciaImpl;

@WebServlet("/ServletGestionPacientes")
public class ServletGestionPacientes extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	NegocioPaciente negocioPaciente = new NegocioPacienteImpl();
	NegocioNacionalidad negocioNacionalidad = new NegocioNacionalidadImpl();
	NegocioProvincia negocioProvincia = new NegocioProvinciaImpl();
	NegocioLocalidad negocioLocalidad = new NegocioLocalidadesImpl();	

	public ServletGestionPacientes(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	
		RequestDispatcher dispatcher;
		Paciente paciente = new Paciente();
		
		
		if(request.getParameter("Param")!=null) {
			
			String opcion = request.getParameter("Param").toString();
			
			switch (opcion) {
				case "list":{
					request.setAttribute("listaPacientes", negocioPaciente.listarPacientes());
					request.setAttribute("exito", true);
					dispatcher = request.getRequestDispatcher("/GestionPacientes.jsp");
					dispatcher.forward(request, response);
					break;
				}
				
				case "insert":{
					request.setAttribute("exito", false);
					
					request.setAttribute("listaNacionalidad", negocioNacionalidad.listarNacionalidades()); //Cargo Nacionalidades combobox
					request.setAttribute("listaProvincia", negocioProvincia.listarProvincias()); //Cargo Provincias combobox
					request.setAttribute("listaLocalidad", negocioLocalidad.listarLocalidades()); //Cargo Localidades combobox								
					dispatcher = request.getRequestDispatcher("/AltaPaciente.jsp");
					dispatcher.forward(request, response);
					break;
				}														
				
				default:					
					break;
			}									
		}	 
		
		if(request.getParameter("Modificar")!=null)
		{
			
			String dni = request.getParameter("Modificar").toString();				
			paciente = negocioPaciente.mostrarPaciente(dni);            
			request.getSession().setAttribute("paciente", paciente);
			request.setAttribute("listaProvincia", negocioProvincia.listarProvincias()); 
			List<Localidad> listaLocalidad2 = (ArrayList<Localidad>) negocioLocalidad.listarLocalidadesByProvincia(paciente.getLocalidad().getProvincia().getIdProvincia());
			request.setAttribute("listaLocalidad2", listaLocalidad2);
			dispatcher = request.getRequestDispatcher("/ModificarPaciente.jsp");   
			dispatcher.forward(request, response);
	        
			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	
		RequestDispatcher dispatcher;
		Paciente paciente = new Paciente();
		
		List<Nacionalidad> listaNacionalidad = new ArrayList<>();
		listaNacionalidad = negocioNacionalidad.listarNacionalidades();
		
		List<Provincia> listaProvincia = negocioProvincia.listarProvincias();
		
		List<Localidad> listaLocalidad = new ArrayList<>();
		listaLocalidad = negocioLocalidad.listarLocalidades();
		//request.setAttribute("listaLocalidad", listaLocalidad);
		
		if (request.getParameter("insert") != null) {
					
			if (negocioPaciente.existePaciente(request.getParameter("txtDni"))) {
		
				request.setAttribute("mensaje", "Este Documento ya esta registrado");
				request.setAttribute("listaLocalidad", listaLocalidad);
				request.setAttribute("listaNacionalidad", listaNacionalidad);
				request.setAttribute("listaProvincia", listaProvincia);
		
				request.getRequestDispatcher("AltaPaciente.jsp").forward(request, response);
		
			} else {
		
				paciente.setDni(request.getParameter("txtDni").toString());
				paciente.setNombre(request.getParameter("txtNombre").toString());
				paciente.setApellido(request.getParameter("txtApellido").toString());
				SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
				Date dateFormateado = new Date();
				try {
					dateFormateado = formato.parse(request.getParameter("txtFechaNac"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				java.sql.Date date1 = new java.sql.Date(dateFormateado.getTime());
				paciente.setFechaNacimiento(date1);
				paciente.setSexo(request.getParameter("slcSexo").charAt(0));
				Nacionalidad nacionalidad = new Nacionalidad();
				nacionalidad.setIdNacionalidad(Integer.parseInt(request.getParameter("slcNacionalidad")));
				paciente.setNacionalidad(nacionalidad);
				Localidad localidad = new Localidad();
				localidad.setIdLocalidad(Integer.parseInt(request.getParameter("slcLocalidad")));
				paciente.setLocalidad(localidad);
				paciente.setDireccion(request.getParameter("txtDireccion"));
				paciente.setEmail(request.getParameter("txtEmail"));
				paciente.setTelefono(request.getParameter("txtTelefono1"));				
		
				if (negocioPaciente.insertar(paciente) == true) {
					request.setAttribute("exito", true);
					request.setAttribute("txtDni", "");
					request.setAttribute("txtNombre", "");
					request.setAttribute("txtApellido", "");
					request.setAttribute("txtFechaNac", "");
					request.setAttribute("txtDireccion", "");
					request.setAttribute("txtEmail", "");
					request.setAttribute("txtTelefono1", "");	
					request.setAttribute("mensaje", "");
				}
						
				request.setAttribute("listaPacientes", negocioPaciente.listarPacientes());
				request.setAttribute("exito", true);
				dispatcher = request.getRequestDispatcher("/GestionPacientes.jsp");
				dispatcher.forward(request, response);
			}
		}
				
		  if (request.getParameter("Eliminar") != null) { 
			  String dni = request.getParameter("pacId");
		  
		  if(negocioPaciente.borrar(dni) == true) {
			  request.setAttribute("delete", true);
			  request.setAttribute("mensaje", ""); 			  			
			  
			  request.setAttribute("listaPacientes", negocioPaciente.listarPacientes());
			  
			  dispatcher = request.getRequestDispatcher("/GestionPacientes.jsp");
			  dispatcher.forward(request, response); }		  
		  }
		 
			if (request.getParameter("btnModificarPaciente") != null) {
				paciente.setNombre(request.getParameter("txtNombre").toString());
				paciente.setApellido(request.getParameter("txtApellido").toString());
				paciente.setDni(request.getParameter("txtDni").toString());
				SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
				Date dateFormateado = new Date();
				try {
					dateFormateado = formato.parse(request.getParameter("txtFechaNac"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				java.sql.Date date1 = new java.sql.Date(dateFormateado.getTime());
				paciente.setFechaNacimiento(date1);
				paciente.setSexo(request.getParameter("slcSexo").charAt(0));
				Nacionalidad nacionalidad = new Nacionalidad();
				nacionalidad.setIdNacionalidad(Integer.parseInt(request.getParameter("slcNacionalidad")));
				paciente.setNacionalidad(nacionalidad);
				Localidad localidad = new Localidad();
				localidad.setIdLocalidad(Integer.parseInt(request.getParameter("slcLocalidad")));
				paciente.setLocalidad(localidad);
				paciente.setDireccion(request.getParameter("txtDireccion"));
				paciente.setEmail(request.getParameter("txtEmail"));
				paciente.setTelefono(request.getParameter("txtTelefono1"));				

				if (negocioPaciente.modificar(paciente) == true) {
					request.setAttribute("update", true);
					request.setAttribute("mensaje", "");					
					
					request.setAttribute("listaPacientes", negocioPaciente.listarPacientes());
					
					dispatcher = request.getRequestDispatcher("/GestionPacientes.jsp");
					dispatcher.forward(request, response);
				}
			}
		
		/*
		 * if(request.getParameter("btnBuscar")!=null) {
		 * 
		 * String nombre= request.getParameter("txtBuscar").toString();
		 * 
		 * ArrayList<Paciente> listaPacienteBuscar = (ArrayList<Paciente>)
		 * paNeg.readAllBuscar(nombre); request.setAttribute("listaPaciente",
		 * listaPacienteBuscar);
		 * 
		 * 
		 * rd = request.getRequestDispatcher("/MenuPaciente.jsp"); rd.forward(request,
		 * response); }
		 */
	}
	
}
