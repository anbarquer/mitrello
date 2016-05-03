package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import other.Mensaje;
import other.ServerGestion;
import model.Usuario;
import dao.UsuarioDAO;

public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();
	// Mensajes al cliente
	private static Mensaje mensaje = new Mensaje();

	private void cambiarDatos(HttpServletRequest request) {
		// Se obtiene el DAO del usuario
		UsuarioDAO usuarioDao = serverGestion
				.getUsuarioDAO((Connection) getServletContext().getAttribute(
						"dbConn"));

		Usuario usuario = usuarioDao.getUsuario(serverGestion.getUsuario(
				request).getId());

		// Captura los parámetros de la petición
		if ((request.getParameter("login") != "")
				&& (!request.getParameter("login")
						.equals(usuario.getUsername()))) {

			if (usuarioDao.Buscar(request.getParameter("login"))) {
				request.setAttribute("error", mensaje
						.Crear("ERROR. El nombre de usuario ya está en uso."));

			} else {
				usuario.setUsername(request.getParameter("login"));
			}

		}
		if (request.getParameter("nombre") != "")
			usuario.setName(request.getParameter("nombre"));
		if (request.getParameter("email") != "")
			usuario.setEmail(request.getParameter("email"));

		// Se guardan los nuevos datos de usuario
		usuarioDao.Actualizar(usuario);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Si el usuario está en sesión
		if (serverGestion.haySesion(request)) {
			UsuarioDAO usuarioDao = serverGestion
					.getUsuarioDAO((Connection) getServletContext()
							.getAttribute("dbConn"));

			Usuario usuario = usuarioDao.getUsuario(serverGestion.getUsuario(
					request).getId());
			request.setAttribute("usuario_mostrar", usuario);

			RequestDispatcher view = request
					.getRequestDispatcher("WEB-INF/usuario.jsp");
			view.forward(request, response);
		} else {

			// Si no hay sesión de usuario se redirige a login.
			response.sendRedirect("login");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (serverGestion.haySesion(request)) {
			if (request.getParameter("eliminar") != null) {

				// Si elige eliminar se manda a la página de confirmación
				response.sendRedirect("confirma_eliminar_usuario");
			} else {
				// Si elige cambiar se actualizan los datos del usuario en la
				// tabla
				if (request.getParameter("cambiar") != null) {

					cambiarDatos(request);
					doGet(request, response);
				} else {
					response.sendRedirect("cambiar_password");
				}
			}
		} else {
			// Si no hay sesión de usuario se redirige a login.
			response.sendRedirect("login");
		}
	}
}
