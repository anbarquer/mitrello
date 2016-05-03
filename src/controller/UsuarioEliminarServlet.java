package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import other.ServerGestion;
import dao.UsuarioDAO;
import model.Usuario;

public class UsuarioEliminarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Si está en la sesión se redirige a la zona de usuario
		if (serverGestion.haySesion(request)) {

			RequestDispatcher view = request
					.getRequestDispatcher("WEB-INF/confirma_eliminar_usuario.jsp");
			view.forward(request, response);

		} else {
			// Si no se manda de nuevo a la zona de login
			response.sendRedirect("login");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Si el cliente tiene una sesión abierta
		if (serverGestion.haySesion(request)) {
			if (request.getParameter("si") != null) {

				// Si confirma se borra el usuario
				UsuarioDAO usuarioDao = serverGestion
						.getUsuarioDAO((Connection) getServletContext()
								.getAttribute("dbConn"));
				Usuario usuario = serverGestion.getUsuario(request);
				usuarioDao.Borrar(usuario.getId());
				response.sendRedirect("home");

			} else {
				// Si no se recarga la página
				if (request.getParameter("no") != null) {
					response.sendRedirect("usuario");
				}
			}

		} else {
			response.sendRedirect("login");
		}
	}

}
