package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.UserNotFoundException;
import negocio.NegocioUsuario;
import negocioImpl.NegocioUsuarioImpl;
import dominio.Usuario;

@WebServlet("/login")
public class ServletLogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public ServletLogIn() {
        super();
    }
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {			
			String strUsuario = request.getParameter("txtUsuario");
			String clave = request.getParameter("txtPassword");
			NegocioUsuario negocioUsuario = new NegocioUsuarioImpl();
			Usuario usuario = negocioUsuario.initUser(strUsuario, clave); // Get usuario y clave.
				//GUARDO LA SESSION
				HttpSession session = request.getSession();
				session.setAttribute("idUsuario", usuario.getIdUsuario());
				session.setAttribute("username", usuario.getNombreUsuario());
				session.setAttribute("password", usuario.getClave());
				session.setAttribute("tipo", usuario.getTipo());
				session.setAttribute("estado", usuario.getEstado());
				
				
				if(usuario.getTipo().equals("Admin")) {					
					response.sendRedirect("AdminInicio?Param=list");
				}
				else {					
					response.sendRedirect("ServletMedicoInicio?list=1");
				}
		}
		catch (UserNotFoundException e){
			request.setAttribute("mensaje", e.getMessage());
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
		
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
