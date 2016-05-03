package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TarjetaDAO;
import other.ServerGestion;

public class TarjetaEliminarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();
	// ID de la tarjeta que se va a eliminar
	private long tarjeta_id = -1;
	// ID del tablón
	private long tablon_id = -1;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("tarjeta_id") != null
				&& request.getParameter("tablon_id") != null) {
			tarjeta_id = Long.parseLong(request.getParameter("tarjeta_id"));
			tablon_id = Long.parseLong(request.getParameter("tablon_id"));

			if (serverGestion.haySesion("tarjeta", request,
					(Connection) getServletContext().getAttribute("dbConn"),
					tarjeta_id)) {
				RequestDispatcher view = request
						.getRequestDispatcher("WEB-INF/confirma_eliminar_tarjeta.jsp");
				view.forward(request, response);

			} else {
				// Si no hay sesión de usuario se redirige a login.
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
		if (serverGestion.haySesion("tarjeta", request,
				(Connection) getServletContext().getAttribute("dbConn"),
				tarjeta_id)) {

			TarjetaDAO tarjetaDAO = serverGestion
					.getTarjetaDAO((Connection) getServletContext()
							.getAttribute("dbConn"));
			if (request.getParameter("si") != null) {
				// Si se confirma la eliminación se borra la tabla del sistema
				tarjetaDAO.Borrar(tarjeta_id);
				response.sendRedirect("lista?tablon_id=" + tablon_id);
			} else {
				if (request.getParameter("no") != null)
					response.sendRedirect("lista?tablon_id=" + tablon_id);
			}
		} else {
			response.sendRedirect("login");
		}
	}

}
