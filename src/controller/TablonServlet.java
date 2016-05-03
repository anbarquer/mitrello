package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Usuario;
import dao.TablonDAO;
import dao.UsuarioDAO;
import other.ServerGestion;

public class TablonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Si el usuario está en sesión
		if (serverGestion.haySesion(request)) {

			// Obtiene la lista de tablones para mostrarla al cliente
			TablonDAO tablonDAO = serverGestion
					.getTablonDAO((Connection) getServletContext()
							.getAttribute("dbConn"));

			Usuario usuario = serverGestion.getUsuario(request);

			UsuarioDAO usuarioDao = serverGestion
					.getUsuarioDAO((Connection) getServletContext()
							.getAttribute("dbConn"));

			request.setAttribute("nombre_usuario",
					usuarioDao.getUsuario(usuario.getId()).getName());

			// Se introducen la lista de tablones en la petición
			request.setAttribute(
					"tablones",
					tablonDAO.getTablonesUsuario(serverGestion.getUsuario(
							request).getId()));
			RequestDispatcher view = request
					.getRequestDispatcher("WEB-INF/tablon.jsp");
			view.forward(request, response);

		} else {

			// Si no hay sesión de usuario se redirige a login.
			response.sendRedirect("login");
		}
	}
}
