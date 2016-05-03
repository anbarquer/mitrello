package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import other.ServerGestion;
import dao.TablonDAO;

public class TablonEliminarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();
	// ID del tablón que se va a eliminar
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
						.getRequestDispatcher("WEB-INF/confirma_eliminar_tablon.jsp");
				view.forward(request, response);
			} else {
				response.sendRedirect("login");
			}
		} else {
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
			if (request.getParameter("si") != null) {
				// Si se confirma la eliminación se borra la tabla del sistema
				tablonDAO.Borrar(tablon_id);
				response.sendRedirect("tablon");
			} else {
				if (request.getParameter("no") != null) {
					response.sendRedirect("tablon");
				}
			}

		} else {
			response.sendRedirect("login");
		}
	}

}
