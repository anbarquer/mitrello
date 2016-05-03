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
import dao.UsuarioDAO;
import other.BCrypt;

public class RegistroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();
	// Mensajes al cliente
	private static Mensaje mensaje = new Mensaje();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher view = request
				.getRequestDispatcher("WEB-INF/registro.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Se obtiene el DAO del usuario
		UsuarioDAO usuarioDao = serverGestion
				.getUsuarioDAO((Connection) getServletContext().getAttribute(
						"dbConn"));

		// Si el usuario ya se encuentra registrado en la aplicación
		if (usuarioDao.Buscar(request.getParameter("login"))) {
			request.setAttribute("error", mensaje
					.Crear("ERROR. El usuario ya se encuentra registrado."));
			doGet(request, response);
		} else {
			// Todo correcto. Se registra el usuario en la tabla y se
			// redirige a login

			// Encriptado de contraseña
			String hash = BCrypt.hashpw(request.getParameter("password"),
					BCrypt.gensalt());
			usuarioDao.Guardar(request.getParameter("nombre"),
					request.getParameter("login"),
					request.getParameter("email"), hash);
			response.sendRedirect("login");

		}
	}

}
