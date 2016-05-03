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

public class ListaLimpiarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();
	// ID de la lista que se va a eliminar
	private long lista_id = -1;
	// ID del tablón
	private long tablon_id = -1;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("lista_id") != null
				&& request.getParameter("tablon_id") != null) {
			lista_id = Long.parseLong(request.getParameter("lista_id"));

			// Si el usuario está en sesión
			if (serverGestion.haySesion("lista", request,
					(Connection) getServletContext().getAttribute("dbConn"),
					lista_id)) {

				tablon_id = Long.parseLong(request.getParameter("tablon_id"));
				RequestDispatcher view = request
						.getRequestDispatcher("WEB-INF/confirma_limpiar_lista.jsp");
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
		if (serverGestion.haySesion("lista", request,
				(Connection) getServletContext().getAttribute("dbConn"),
				lista_id)) {

			if (request.getParameter("si") != null) {
				// Se limpia la lista
				TarjetaDAO tarjetaDAO = serverGestion
						.getTarjetaDAO((Connection) getServletContext()
								.getAttribute("dbConn"));
				tarjetaDAO.limpiarTarjetas(lista_id);
				response.sendRedirect("lista?tablon_id=" + tablon_id);
			} else {
				if (request.getParameter("no") != null) {
					response.sendRedirect("lista?tablon_id=" + tablon_id);
				}

			}
		} else {
			response.sendRedirect("login");
		}
	}

}
