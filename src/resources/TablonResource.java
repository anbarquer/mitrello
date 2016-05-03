package resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import model.Tablon;
import model.Usuario;
import dao.JDBCTablonDAO;
import dao.TablonDAO;

@Path("/tablones")
public class TablonResource {
	
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	@Context
	HttpServletRequest request;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tablon> getTablonesJSON(){
		Connection conn = (Connection) sc.getAttribute("dbConn");
		TablonDAO tablonDao = new JDBCTablonDAO();
		tablonDao.setConexion(conn);
		List<Tablon> tablones = new ArrayList<Tablon>();
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		if(usuario != null)
		{
			tablones = tablonDao.getTablonesUsuario(usuario.getId());
		}
			
	    return tablones;
	}
		
	@PUT
	@Path("/{tablon_id:[0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String modificarTablonJSON(
			@PathParam("tablon_id") long tablon_id, 
			@QueryParam("nombre") String nombre
			){
		
		String correct = "{\"ok\":\"false\"}";
		Connection conn = (Connection) sc.getAttribute("dbConn");
		TablonDAO tablonDao = new JDBCTablonDAO();
		tablonDao.setConexion(conn);
		
		List<Tablon> tablones = new ArrayList<Tablon>();
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		boolean propietario = false;
		
		if(usuario != null)
		{
			tablones = tablonDao.getTablonesUsuario(usuario.getId());
			Iterator<Tablon> iter = tablones.listIterator();
			while(iter.hasNext() && !propietario)
			{
				Tablon tablon = (Tablon) iter.next();
				if(tablon.getId() == tablon_id)
				{
					propietario = true;
				}
			}
		}
		
		if(propietario)
		{
			if(tablonDao.Modificar(nombre, tablon_id, usuario.getId()))
				correct = "{\"ok\":\"true\"}";
			else
				correct = "{\"ok\":\"duplicado\"}";
		}
		
		return correct;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String guardarTablonJSON(@QueryParam("nombre") String nombre){
		String correct = "{\"ok\":\"false\"}";
		Connection conn = (Connection) sc.getAttribute("dbConn");
		TablonDAO tablonDao = new JDBCTablonDAO();
		tablonDao.setConexion(conn);
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
				
		if(usuario != null)
		{
			if(tablonDao.Guardar(nombre, usuario.getId()))
				correct = "{\"ok\":\"true\"}";
			else
				correct = "{\"ok\":\"duplicado\"}";
		}
		
		return correct;
	}
	
	@DELETE
	@Path("/{tablon_id:[0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String borrarTablonJSON(@PathParam("tablon_id") long tablon_id) {
		String correct = "{\"ok\":\"false\"}";
		Connection conn = (Connection) sc.getAttribute("dbConn");
		TablonDAO tablonDao = new JDBCTablonDAO();
		tablonDao.setConexion(conn);
		
		List<Tablon> tablones = new ArrayList<Tablon>();
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		boolean propietario = false;
		
		if(usuario != null)
		{
			tablones = tablonDao.getTablonesUsuario(usuario.getId());
			Iterator<Tablon> iter = tablones.listIterator();
			while(iter.hasNext() && !propietario)
			{
				Tablon tablon = (Tablon) iter.next();
				if(tablon.getId() == tablon_id)
				{
					propietario = true;
				}
			}
		}

		if(propietario)
		{
			tablonDao.Borrar(tablon_id);
			correct = "{\"ok\":\"true\"}";
		}
		
		return correct;
    }
}
