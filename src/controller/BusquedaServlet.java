package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Tablon;
import other.Mensaje;
import other.ServerGestion;
import dao.BusquedaDAO;
import dao.JDBCBusquedaDAO;

public class BusquedaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();
	// Mensajes al cliente
	private static Mensaje mensaje = new Mensaje();

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if (serverGestion.haySesion(request)) {

			if (request.getParameter("busqueda") != null) {
				Connection conexion = (Connection) getServletContext()
						.getAttribute("dbConn");
				BusquedaDAO busquedaDao = new JDBCBusquedaDAO();
				busquedaDao.setConexion(conexion);

				List<Tablon> tablones = busquedaDao.getTablonesBusqueda(request
						.getParameter("busqueda"),
						serverGestion.getUsuario(request).getId());
				request.setAttribute("tablones", tablones);
				request.setAttribute("busqueda",
						request.getParameter("busqueda"));

				if (tablones.isEmpty())
					request.setAttribute(
							"error",
							mensaje.Crear("ERROR. No se ha encontrado ninguna coincidencia"));
			}

			RequestDispatcher view = request
					.getRequestDispatcher("WEB-INF/buscar.jsp");
			view.forward(request, response);
		} else {
			response.sendRedirect("login");
		}

	}

}
