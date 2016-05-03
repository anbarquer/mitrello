package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TarjetaDAO;
import other.Mensaje;
import other.ServerGestion;

public class TarjetaModificarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();
	// Mensajes al cliente
	private static Mensaje mensaje = new Mensaje();
	// ID de la tarjeta
	private long tarjeta_id = -1;
	// ID del tablón
	private long tablon_id = -1;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Se captura el identificador del tablón
		if (request.getParameter("tarjeta_id") != null
				&& request.getParameter("tablon_id") != null) {
			tarjeta_id = Long.parseLong(request.getParameter("tarjeta_id"));
			tablon_id = Long.parseLong(request.getParameter("tablon_id"));

			if (serverGestion.haySesion("tarjeta", request,
					(Connection) getServletContext().getAttribute("dbConn"),
					tarjeta_id)) {
				RequestDispatcher view = request
						.getRequestDispatcher("WEB-INF/modifica_tarjeta.jsp");
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

		// Si se ha seleccionado mover la tarjeta a otra lista se capturan los
		// identificadores
		if (request.getParameter("mover") != null) {
			tarjeta_id = Long.parseLong(request.getParameter("tarjeta_id"));
			tablon_id = Long.parseLong(request.getParameter("tablon_id"));
		}

		// Si el usuario está en sesión
		if (serverGestion.haySesion("tarjeta", request,
				(Connection) getServletContext().getAttribute("dbConn"),
				tarjeta_id)) {

			TarjetaDAO tarjetaDAO = serverGestion
					.getTarjetaDAO((Connection) getServletContext()
							.getAttribute("dbConn"));

			// Se ha seleccionado la opción de mover la lista
			if (request.getParameter("mover") != null) {

				// Se mueve la tarjeta a la lista seleccionada
				tarjetaDAO.moverTarjeta(
						Long.parseLong(request.getParameter("tarjeta_id")),
						Long.parseLong(request.getParameter("seleccion")));
				response.sendRedirect("lista?tablon_id=" + tablon_id);
			} else {

				// Si se ha seleccionado la modificación del nombre de la
				// tarjeta
				if ((request.getParameter("nombre_tarjeta") != null)
						&& (tarjetaDAO.Modificar(request
								.getParameter("nombre_tarjeta"), tarjeta_id,
								serverGestion.getUsuario(request).getId()))) {
					response.sendRedirect("lista?tablon_id=" + tablon_id);

				} else {
					// Si existe una tarjeta con el mismo nombre asociada a ese
					// usuario se informa al cliente
					request.setAttribute(
							"error",
							mensaje.Crear("ERROR. Ya existe una tarjeta con ese nombre."));
					RequestDispatcher view = request
							.getRequestDispatcher("WEB-INF/modifica_tarjeta.jsp");
					view.forward(request, response);
				}
			}

		} else {

			// Si no hay sesión de usuario se redirige a login.
			response.sendRedirect("login");
		}
	}

}
