package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daoImpl.UsuarioDaoImpl;
import dominio.Usuario;
import negocio.NegocioUsuario;
import negocioImpl.NegocioUsuarioImpl;

/**
 * Servlet implementation class ServletUsuario
 */
@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletUsuario() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("exito", false);
		request.getRequestDispatcher("AltaAdministrativo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		NegocioUsuario negocioUsuario = new NegocioUsuarioImpl();
		Usuario user = new Usuario();
		boolean exito = false;
		request.setAttribute("exito", exito);

		if (request.getParameter("btnModificarUser") != null) {
			
			int idUser = Integer.parseInt(request.getSession().getAttribute("idUsuario").toString());
			String userName = request.getSession().getAttribute("username").toString();
			String newUsername = request.getParameter("txtUserNuevo");
			String pass1 = request.getParameter("txtPassAnterior");
			String pass2 = request.getParameter("txtPassNueva");
			String pass3 = request.getParameter("txtPassNueva2");
			exito = false;
			String passSession = (request.getSession().getAttribute("password")).toString();

			if (pass2 == "" && pass3 == "" && newUsername == "") {
				request.setAttribute("mensaje", "No esta realizando ningun cambio");
				request.setAttribute("exito", exito);
				request.getRequestDispatcher("MiPerfil.jsp").forward(request, response);
			} else {
				if (!(pass1.equals(passSession))) {
					request.setAttribute("mensaje", "La claves anterior no es correcta");
					request.setAttribute("exito", exito);
					request.getRequestDispatcher("MiPerfil.jsp").forward(request, response);
				} else {

					if (!(pass2.equals(pass3))) {
						request.setAttribute("mensaje", "Las claves no coinciden");
						request.setAttribute("exito", exito);
						request.getRequestDispatcher("MiPerfil.jsp").forward(request, response);

					} 
					else {
						String us = request.getParameter("txtUserNuevo");
						if (negocioUsuario.existeUsuario(us)) {
							request.setAttribute("mensaje", "Nombre de usuario no disponible");
							request.setAttribute("exito", exito);
							request.getRequestDispatcher("MiPerfil.jsp").forward(request, response);
						}

						else {
							if (newUsername == "") {
								user.setNombreUsuario(userName);
							} else {
								user.setNombreUsuario(newUsername);
							}
							if (pass2 == "") {
								user.setClave(passSession);
							} else {
								user.setClave(request.getParameter("txtPassNueva"));
							}
							user.setIdUsuario(idUser);
	
							if (negocioUsuario.update(user) == true) {
								request.setAttribute("exito", true);
								request.setAttribute("txtUseNuevor", "");
								request.setAttribute("txtPassAnterior", "");
								request.setAttribute("txtPassNueva", "");
								request.setAttribute("txtPassNueva2", "");
								request.setAttribute("mensaje", "");
								request.getSession().setAttribute("username", user.getNombreUsuario());
								request.getSession().setAttribute("pass", user.getClave());
								request.getRequestDispatcher("MiPerfil.jsp").forward(request, response);
							}
						}

					}

				}
			}
		}

		if (request.getParameter("btnNuevoUser") != null) {

			String pass1 = request.getParameter("txtPass");
			String pass2 = request.getParameter("txtPass2");
			exito = false;
			if (!(pass1.equals(pass2))) {
				request.setAttribute("mensaje", "Las claves no coinciden");
				request.setAttribute("exito", exito);
				request.getRequestDispatcher("AltaAdministrativo.jsp").forward(request, response);

			} else {

				String us = request.getParameter("txtUser");

				if (negocioUsuario.existeUsuario(us)) {
					request.setAttribute("mensaje", "Nombre de usuario no disponible");
					request.setAttribute("exito", exito);
					request.getRequestDispatcher("AltaAdministrativo.jsp").forward(request, response);
				} else {

					user.setNombreUsuario(request.getParameter("txtUser"));
					user.setClave(request.getParameter("txtPass"));
					user.setTipo("Admin");
					user.setEstado(1);
					exito = true;
					if (negocioUsuario.insertAdmin(user) == true) {
						request.setAttribute("exito", true);
						request.setAttribute("txtUser", "");
						request.setAttribute("txtPass", "");
						request.setAttribute("mensaje", "");

						request.getRequestDispatcher("AltaAdministrativo.jsp").forward(request, response);
					}
				}

			}

		}

	}

}
