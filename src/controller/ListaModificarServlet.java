package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ListaDAO;
import other.Mensaje;
import other.ServerGestion;

public class ListaModificarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();
	// Mensajes al cliente
	private static Mensaje mensaje = new Mensaje();
	// ID de la lista
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
						.getRequestDispatcher("WEB-INF/modifica_lista.jsp");
				view.forward(request, response);

			} else {
				// Si el parámetro es nulo o si no está en sesión se redirige a
				// login
				response.sendRedirect("login");
			}

		} else {
			// Si el parámetro es nulo o si no está en sesión se redirige a
			// login
			response.sendRedirect("login");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Si el usuario está en sesión
		if (serverGestion.haySesion("lista", request,
				(Connection) getServletContext().getAttribute("dbConn"),
				lista_id)) {

			ListaDAO listaDAO = serverGestion
					.getListaDAO((Connection) getServletContext().getAttribute(
							"dbConn"));
			// Si no existe una lista con el mismo nombre se guarda en el
			// sistema

			if (listaDAO.Modificar(request.getParameter("nombre_lista"),
					lista_id, serverGestion.getUsuario(request).getId())) {
				response.sendRedirect("lista?tablon_id=" + tablon_id);

			} else {
				// En otro caso se informa al cliente del error y se recarga la
				// página
				request.setAttribute("error", mensaje
						.Crear("ERROR. Ya existe una lista con ese nombre."));
				RequestDispatcher view = request
						.getRequestDispatcher("WEB-INF/modifica_lista.jsp");
				view.forward(request, response);
			}

		} else {

			// Si no hay sesión de usuario se redirige a login.
			response.sendRedirect("login");
		}
	}

}
