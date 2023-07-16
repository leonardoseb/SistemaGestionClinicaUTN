package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import negocio.NegocioEspecialidad;
import negocio.NegocioMedico;
import negocio.NegocioTurno;
import negocioImpl.NegocioEspecialidadImpl;
import negocioImpl.NegocioMedicoImpl;
import negocioImpl.NegocioTurnoImpl;

/**
 * Servlet implementation class ServletGestionTurnos
 */
@WebServlet("/AdminInicio")
public class ServletGestionTurnos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	NegocioTurno negocioTurno = new NegocioTurnoImpl();
	NegocioMedico negocioMedico = new NegocioMedicoImpl();
	NegocioEspecialidad negocioEspecialidad = new NegocioEspecialidadImpl();
	/// PONER NEGOCIO PACIENTE MEDICO ESPECIALIDAD ??????ESTADo???????

	public ServletGestionTurnos(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{		
		
		if(request.getParameter("Param")!=null) {
			
			String opcion = request.getParameter("Param").toString();
			switch (opcion) {
				case "list":{
					request.setAttribute("listaTurnos", negocioTurno.listarTurnos());
					request.setAttribute("listaMedicos", negocioMedico.listarMedicosConTurno());
					request.setAttribute("listaEspecialidades", negocioEspecialidad.cargarEspecialidades());
					RequestDispatcher dispatcher = request.getRequestDispatcher("/ServletTurno?Param=list");
					dispatcher.forward(request, response);
					break;
				}
				
				//case "insert":{
					//request.setAttribute("listaNacionalidad", negocioNacionalidad.listarNacionalidades()); //Cargo Nacionalidades combobox
					//request.setAttribute("listaLocalidad", negocioLocalidad.listarLocalidades()); //Cargo Localidades combobox
					//RequestDispatcher dispatcher = request.getRequestDispatcher("/AltaPaciente.jsp");
					//dispatcher.forward(request, response);
					//break;
				//}
				
				case "delete":{
					break;
				}
				
				default:
					break;
			}
		}	  
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
	}

}

