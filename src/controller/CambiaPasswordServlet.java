package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Usuario;
import dao.UsuarioDAO;
import other.Mensaje;
import other.ServerGestion;
import other.BCrypt;

public class CambiaPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();
	// Mensajes al cliente
	private static Mensaje mensaje = new Mensaje();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Si está en la sesión se redirige a la zona de usuario
		if (serverGestion.haySesion(request)) {

			RequestDispatcher view = request
					.getRequestDispatcher("WEB-INF/modifica_password.jsp");
			view.forward(request, response);

		} else {
			// Si no se manda de nuevo a la zona de login
			response.sendRedirect("login");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (serverGestion.haySesion(request)) {
			UsuarioDAO usuarioDao = serverGestion
					.getUsuarioDAO((Connection) getServletContext()
							.getAttribute("dbConn"));

			Usuario usuario = usuarioDao.getUsuario(serverGestion.getUsuario(
					request).getId());

			// Comprobación de la contraseña
			if (BCrypt.checkpw(request.getParameter("password_antiguo"),
					usuario.getPassword())) {
				String hashed = BCrypt.hashpw(
						request.getParameter("password_nuevo"),
						BCrypt.gensalt()); // Hashing del nuevo password
				usuario.setPassword(hashed);
				usuarioDao.Actualizar(usuario);
				request.setAttribute("notificacion", mensaje
						.Crear("La contraseña se ha modificado correctamente."));
				doGet(request, response);

			} else {
				request.setAttribute("error", mensaje
						.Crear("ERROR. La contraseña antigua es errónea."));
				doGet(request, response);
			}

		} else {
			response.sendRedirect("login");
		}

	}

}
