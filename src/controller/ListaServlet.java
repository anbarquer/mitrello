package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Lista;
import model.Tarjeta;
import dao.ListaDAO;
import dao.TablonDAO;
import dao.TarjetaDAO;
import other.ServerGestion;

public class ListaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();
	// ID del tablón
	private long tablon_id = -1;
	// Nombre del tablón
	private String nombre_tablon = null;
	// Listas del tablón
	private List<Lista> listas = null;
	// Tarjetas del tablón
	private List<Tarjeta> tarjetas = null;

	private void setPagina(HttpServletRequest request) {
		// Crea la conexion con la base de datos
		ListaDAO listaDAO = serverGestion
				.getListaDAO((Connection) getServletContext().getAttribute(
						"dbConn"));
		listas = listaDAO.getListasTablon(tablon_id);
		request.setAttribute("nombre_tablon", nombre_tablon);
		request.setAttribute("tablon_id", tablon_id);
		request.setAttribute("listas", listas);

		TarjetaDAO tarjetaDAO = serverGestion
				.getTarjetaDAO((Connection) getServletContext().getAttribute(
						"dbConn"));

		tarjetas = tarjetaDAO.getTarjetasUsuario(serverGestion.getUsuario(
				request).getId());

		request.setAttribute("tarjetas", tarjetas);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Si el parámetro tiene valor, se guarda
		if (request.getParameter("tablon_id") != null) {
			tablon_id = Long.parseLong(request.getParameter("tablon_id"));

			// Si el usuario está en sesión
			if (serverGestion.haySesion("tablon", request,
					(Connection) getServletContext().getAttribute("dbConn"),
					tablon_id)) {
				TablonDAO tablonDao = serverGestion
						.getTablonDAO((Connection) getServletContext()
								.getAttribute("dbConn"));
				nombre_tablon = tablonDao.getNombreTablon(tablon_id);

				// Obtiene todos los parámetros de las listas para mostrarlos
				setPagina(request);
				RequestDispatcher view = request
						.getRequestDispatcher("WEB-INF/lista.jsp");
				view.forward(request, response);
			} else {
				// Si no hay sesión de usuario se redirige a login.
				response.sendRedirect("login");
			}

		} else {
			response.sendRedirect("login");
		}
	}

}
