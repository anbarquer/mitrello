package resources;
import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import other.BCrypt;
import model.Usuario;
import dao.JDBCUsuarioDAO;
import dao.UsuarioDAO;

@Path("/usuarios")
public class UsuarioResource {
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	@Context
	HttpServletRequest request;
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String loginJSON(
			@QueryParam("usuario") String usuario, 
			@QueryParam("password")String password) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UsuarioDAO usuarioDao = new JDBCUsuarioDAO();
		usuarioDao.setConexion(conn);
		
		String correct = "{\"ok\":\"false\"}";
		Usuario user = null;
		
		switch(usuarioDao.checkUsuario(usuario,password))
		{
			// El usuario no se encuentra registrado
			case 0:	correct = "{\"ok\":\"noregistrado\"}";	break;
				
			// La contraseña es incorrecta
			case 1:	correct = "{\"ok\":\"incorrect\"}";		break;
				
			case 2:		
				user = new Usuario();
				request.getSession().invalidate();
				user.setId(usuarioDao.getUsuario(usuario).getId());
				request.getSession().setAttribute("usuario", user);
				correct = "{\"ok\":\"true\"}";	
			break;
		}
		

		return correct;
	}
	
	@GET
	@Path("/{usuario_id:[0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario getUsuarioJSON(@PathParam("usuario_id") long usuario_id) {  
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UsuarioDAO usuarioDao = new JDBCUsuarioDAO();
		usuarioDao.setConexion(conn);
		
		Usuario user = usuarioDao.getUsuario(usuario_id);
				
	    return user; 
	  }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String registroUsuarioJSON(
			@QueryParam("nombre") String nombre,
			@QueryParam("usuario") String usuario,
			@QueryParam("password") String password,
			@QueryParam("email") String email){
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UsuarioDAO usuarioDao = new JDBCUsuarioDAO();
		usuarioDao.setConexion(conn);
		String correct = "{\"ok\":\"false\"}";
		
		if(!usuarioDao.Buscar(usuario))
		{
			String hash = BCrypt.hashpw(password,BCrypt.gensalt());
			usuarioDao.Guardar(nombre, usuario, email, hash);
			correct = "{\"ok\":\"true\"}";
		}
		return correct;
	}
}
