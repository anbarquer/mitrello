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
import dao.TablonDAO;

public class TablonCrearServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();
	// Mensajes al cliente
	private static Mensaje mensaje = new Mensaje();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Si el usuario está en sesión
		if (serverGestion.haySesion(request)) {
			RequestDispatcher view = request
					.getRequestDispatcher("WEB-INF/crea_tablon.jsp");
			view.forward(request, response);
		} else {

			// Si no hay sesión de usuario se redirige a login.
			response.sendRedirect("login");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Si el usuario está en sesión
		if (serverGestion.haySesion(request)) {

			TablonDAO tablonDAO = serverGestion
					.getTablonDAO((Connection) getServletContext()
							.getAttribute("dbConn"));

			// Si no existe un tablón con el mismo nombre se guarda en el
			// sistema
			if (tablonDAO.Guardar(request.getParameter("nombre_tablon"),
					serverGestion.getUsuario(request).getId())) {
				response.sendRedirect("tablon");
			} else {
				// En otro caso se informa al cliente del error y se recarga la
				// página
				request.setAttribute("error", mensaje
						.Crear("ERROR. Ya existe un tablón con ese nombre."));
				doGet(request, response);
			}

		} else {

			// Si no hay sesión de usuario se redirige a login.
			response.sendRedirect("login");
		}
	}

}
