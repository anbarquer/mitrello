package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import other.ServerGestion;

public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Conexiones al dao y a la sesión
	private static ServerGestion serverGestion = new ServerGestion();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Si el usuario está en sesión
		if (serverGestion.haySesion(request)) {
			RequestDispatcher view = request
					.getRequestDispatcher("WEB-INF/dashboard.jsp");
			view.forward(request, response);
		} else {

			// Si no hay sesión de usuario se redirige a login.
			response.sendRedirect("login");
		}
	}
}
