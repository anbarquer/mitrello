package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import other.Mensaje;
import other.ServerGestion;
import dao.UsuarioDAO;
import model.Usuario;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();
	// Mensajes al cliente
	private static Mensaje mensaje = new Mensaje();

	// Si se produce un error al introducir los credenciales se informa al
	// usuario y se recarga la página
	private void errorLogin(String contenido, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("error", mensaje.Crear(contenido));
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Si el usuario tiene una sesión se elimina
		if (serverGestion.haySesion(request)) {
			HttpSession session = request.getSession();
			session.invalidate();
		}

		RequestDispatcher view = request
				.getRequestDispatcher("WEB-INF/login.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		UsuarioDAO usuarioDAO = serverGestion
				.getUsuarioDAO((Connection) getServletContext().getAttribute(
						"dbConn"));

		switch (usuarioDAO.checkUsuario(request.getParameter("login"),
				request.getParameter("password"))) {

		// El usuario no se encuentra registrado
		case 0:
			errorLogin("ERROR. El usuario no se encuentra registrado.",
					request, response);
			break;
		// El usuario ha introducido una contraseña incorrecta
		case 1:
			errorLogin("ERROR. Contraseña incorrecta.", request, response);
			break;
		case 2:

			// El nombre de usuario y la contraseña coinciden con los datos del
			// sistema
			Usuario usuario = new Usuario();
			usuario.setId(usuarioDAO.getUsuario(request.getParameter("login"))
					.getId());
			HttpSession session = request.getSession();
			session.setAttribute("usuario", usuario);
			response.sendRedirect("dashboard");
			break;

		}
	}

}
