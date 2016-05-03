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

public class TarjetaCrearServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();
	// Mensajes al cliente
	private static Mensaje mensaje = new Mensaje();
	// ID de la lista en donde se inserta la tarjeta
	private long lista_id = -1;
	// ID del tablón
	private long tablon_id = -1;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Se captura el id de la lista para post
		if (request.getParameter("lista_id") != null
				&& request.getParameter("tablon_id") != null) {
			lista_id = Long.parseLong(request.getParameter("lista_id"));
			tablon_id = Long.parseLong(request.getParameter("tablon_id"));
			if (serverGestion.haySesion("lista", request,
					(Connection) getServletContext().getAttribute("dbConn"),
					lista_id)) {
				RequestDispatcher view = request
						.getRequestDispatcher("WEB-INF/crea_tarjeta.jsp");
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
		if (serverGestion.haySesion("lista", request,
				(Connection) getServletContext().getAttribute("dbConn"),
				lista_id)) {

			TarjetaDAO tarjetaDAO = serverGestion
					.getTarjetaDAO((Connection) getServletContext()
							.getAttribute("dbConn"));

			// Si no existe una tarjeta con el mismo nombre se guarda en el
			// sistema
			if (tarjetaDAO.Guardar(request.getParameter("nombre_tarjeta"),
					lista_id, serverGestion.getUsuario(request).getId())) {
				response.sendRedirect("lista?tablon_id=" + tablon_id);

			} else {
				// En otro caso se informa al cliente del error y se recarga la
				// página
				request.setAttribute("error", mensaje
						.Crear("ERROR. Ya existe una tarjeta con ese nombre."));
				RequestDispatcher view = request
						.getRequestDispatcher("WEB-INF/crea_tarjeta.jsp");
				view.forward(request, response);
			}

		} else {

			// Si no hay sesión de usuario se redirige a login.
			response.sendRedirect("login");
		}
	}

}
