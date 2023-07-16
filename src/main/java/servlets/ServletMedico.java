package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daoImpl.EspecialidadDaoImpl;
import dominio.Especialidad;
import dominio.Localidad;
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

@WebServlet("/CargarMedico")
public class ServletMedico extends HttpServlet {
	private static final long serialVersionUID = 1L;

	NegocioMedico negMed = new NegocioMedicoImpl();
	NegocioEspecialidad negEsp = new NegocioEspecialidadImpl();
	NegocioNacionalidad negocioNacionalidad = new NegocioNacionalidadImpl();
	NegocioLocalidad negocioLocalidad = new NegocioLocalidadesImpl();
	NegocioProvincia negocioProvincia = new NegocioProvinciaImpl();
	
	public ServletMedico() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("Param") != null) {
			String opcion = request.getParameter("Param").toString();
			switch (opcion) {
			case "cargar": {
				request.setAttribute("listaNacionalidad", negocioNacionalidad.listarNacionalidades()); // Cargo Nacionalidades combobox
				request.setAttribute("listaLocalidad", negocioLocalidad.listarLocalidades()); // Cargo Localidades combobox
				
			
				List<Provincia> listaProvincia = negocioProvincia.listarProvincias();
				request.setAttribute("listaProvincia", negocioProvincia.listarProvincias()); //Cargo Provincias combobox

				ArrayList<Especialidad> especialidades = negEsp.cargarEspecialidades();
				request.setAttribute("especialidades", especialidades);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/CargarMedico.jsp");
				dispatcher.forward(request, response);
				break;
			}

			case "insert": {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/CargarMedico.jsp");
				dispatcher.forward(request, response);
				break;
			}
			case "modificar":{
				RequestDispatcher dispatcher = request.getRequestDispatcher("/ModificarMedico.jsp");
				dispatcher.forward(request, response);
				break;
			}

			case "delete": {
				break;
			}
			case "list": {
				request.setAttribute("listaMedicos", negMed.obtenerMedicos());
				request.setAttribute("exito", true);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/GestionMedico.jsp");
				dispatcher.forward(request, response);
				break;
			}
			case "error":{
				boolean blDni = true;
				request.setAttribute("blDni", blDni);
				request.setAttribute("listaNacionalidad", negocioNacionalidad.listarNacionalidades()); // Cargo Nacionalidades combobox
				request.setAttribute("listaLocalidad", negocioLocalidad.listarLocalidades()); // Cargo Localidades combobox

				ArrayList<Especialidad> especialidades = negEsp.cargarEspecialidades();
				request.setAttribute("especialidades", especialidades);

				RequestDispatcher dispatcher = request.getRequestDispatcher("CargarMedico.jsp");
				dispatcher.forward(request, response);				
				break;
			}
			default:
				break;
			}
		}


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("delete") != null) {
			System.out.println("LLEGO A ELIMINAR");
			// obtiene el dni
			System.out.println(request.getParameter("medId"));
			String Dni = request.getParameter("medId");
			if (negMed.borrarMedico(Dni) == true) {
				
				System.out.println("LLEGO DESPUES DE IF");
				request.setAttribute("delete", true);
				request.setAttribute("mensaje", "");

				request.setAttribute("listaMedicos", negMed.obtenerMedicos());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/GestionMedico.jsp");
				dispatcher.forward(request, response);
			}
		}

	}

}
