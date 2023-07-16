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

import dao.MedicoDao;
import daoImpl.MedicoDaoImpl;
import dominio.Especialidad;
import dominio.Localidad;
import dominio.Medico;
import dominio.Nacionalidad;
import dominio.Paciente;
import dominio.Provincia;
import negocio.NegocioEspecialidad;
import negocio.NegocioLocalidad;
import negocio.NegocioMedico;
import negocio.NegocioNacionalidad;
import negocio.NegocioProvincia;
import negocioImpl.NegocioEspecialidadImpl;
import negocioImpl.NegocioLocalidadesImpl;
import negocioImpl.NegocioMedicoImpl;
import negocioImpl.NegocioNacionalidadImpl;
import negocioImpl.NegocioProvinciaImpl;

/**
 * Servlet implementation class servletModificarMedico
 */
@WebServlet("/ModificarMedico")
public class servletModificarMedico extends HttpServlet {
	private static final long serialVersionUID = 1L;
	NegocioMedico negMed = new NegocioMedicoImpl();
	NegocioEspecialidad negEsp = new NegocioEspecialidadImpl();
	NegocioNacionalidad negocioNacionalidad = new NegocioNacionalidadImpl();
	NegocioLocalidad negocioLocalidad = new NegocioLocalidadesImpl();
	MedicoDao medicoDao = new MedicoDaoImpl();
	NegocioProvincia negocioProvincia = new NegocioProvinciaImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public servletModificarMedico() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub}
		boolean blTabla = false;
		System.out.println(request.getParameter(("textDni")));
	    String enlace = request.getParameter("enlace"); // Obtiene el valor del parámetro "enlace"
	    System.out.println(enlace);
		if (request.getParameter("btnBuscar") != null) {
			request.setAttribute("listaNacionalidad", negocioNacionalidad.listarNacionalidades()); // Cargo Nacionalidade																			// combobox
			request.setAttribute("listaLocalidad", negocioLocalidad.listarLocalidades()); // Cargo Localidades combobox
			List<Provincia> listaProvincia = negocioProvincia.listarProvincias();
			request.setAttribute("listaProvincia", listaProvincia); // Cargo Provincias combobox
			ArrayList<Especialidad> especialidades = negEsp.cargarEspecialidades();
			request.setAttribute("especialidades", especialidades);
			if (request.getParameter("textDni") != "" && !request.getParameter("textDni").isEmpty()) {
				int dni = Integer.parseInt(request.getParameter("textDni"));
				request.getSession().setAttribute("dniBusqueda", dni); 
				List<Medico> medico = negMed.obtenerMedicoxDni(dni);
				blTabla = true;
				request.getSession().setAttribute("medico", medico);
				request.setAttribute("ListMedicos", medico);
				request.setAttribute("blTabla", blTabla);
			} else {
				blTabla = false;
				request.setAttribute("blTabla", blTabla);
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarMedico.jsp");
			dispatcher.forward(request, response);
		}else if (request.getParameter("btnModificar") != null) {
			// obtener todos los valores y pasarlo al update
			Medico medico = new Medico();
			boolean estado = false;
			medico.setDni(request.getParameter("txtDniMedico"));
			medico.setNombre(request.getParameter("txtNombreMedico"));
			medico.setApellido(request.getParameter("txtApellidoMedico"));
			medico.setDireccion(request.getParameter("txtDireccionMedico"));
			// obtener id de nacionalidad
			// request.getParameter("comboNacionalidad")
			medico.setNacionalidad(new Nacionalidad(Integer.parseInt(request.getParameter("comboNacionalidad"))));
			// obtener id de localidad
			medico.setLocalidad(new Localidad(Integer.parseInt(request.getParameter("comboLocalidad"))));
			medico.setSexo(request.getParameter("sexo").charAt(0));
			medico.setTelefono(request.getParameter("txtTelefonoMedico"));
			medico.setIdEspecialidad(
					new Especialidad(Integer.parseInt(request.getParameter("comboEspecialidad")), null));
			// obtener el date
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			Date dateFormateado = new Date();
			try {
				dateFormateado = formato.parse(request.getParameter("txtFechaNacimiento"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			java.sql.Date date1 = new java.sql.Date(dateFormateado.getTime());
			medico.setFechaNacimiento(date1);
			medico.setEmail(request.getParameter("txtEmail"));
	        int dniBusqueda = (int) request.getSession().getAttribute("dniBusqueda");
	        if(dniBusqueda >0) {
	        	estado = negMed.modificarMedico(medico, dniBusqueda);	        	
				if (estado) {
					
					System.out.println("PASO BIEN "); 
					RequestDispatcher dispatcher = request.getRequestDispatcher("/CargarMedico?Param=list");
					dispatcher.forward(request, response);
				} else {
					System.out.println("PASO MAL ");
					RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarMedico.jsp");
					dispatcher.forward(request, response);
				}
	        }
		}
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
