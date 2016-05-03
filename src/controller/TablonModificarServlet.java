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

public class TablonModificarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();
	// Mensajes al cliente
	private static Mensaje mensaje = new Mensaje();
	// ID del tablón
	private long tablon_id = -1;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Se captura el identificador del tablón
		if (request.getParameter("tablon_id") != null) {
			tablon_id = Long.parseLong(request.getParameter("tablon_id"));
			if (serverGestion.haySesion("tablon", request,
					(Connection) getServletContext().getAttribute("dbConn"),
					tablon_id)) {
				RequestDispatcher view = request
						.getRequestDispatcher("WEB-INF/modifica_tablon.jsp");
				view.forward(request, response);
			} else {
				response.sendRedirect("login");
			}
		} else {
			// Si no hay sesión de usuario se redirige a login.
			response.sendRedirect("login");
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Si el usuario está en sesión
		if (serverGestion.haySesion("tablon", request,
				(Connection) getServletContext().getAttribute("dbConn"),
				tablon_id)) {

			TablonDAO tablonDAO = serverGestion
					.getTablonDAO((Connection) getServletContext()
							.getAttribute("dbConn"));
			// Si no existe un tablón con el mismo nombre se guarda en el
			// sistema

			if (request.getParameter("cambiar") != null) {
				if (tablonDAO.Modificar(request.getParameter("nombre_tablon"),
						tablon_id, serverGestion.getUsuario(request).getId())) {
					response.sendRedirect("tablon");
				} else {
					request.setAttribute(
							"error",
							mensaje.Crear("ERROR. Ya existe un tablón con ese nombre."));
					RequestDispatcher view = request
							.getRequestDispatcher("WEB-INF/modifica_tablon.jsp");
					view.forward(request, response);
				}
			}

		} else {
			response.sendRedirect("login");
		}

	}

}
