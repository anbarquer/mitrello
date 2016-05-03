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

import model.Tarjeta;
import model.Usuario;
import dao.JDBCTarjetaDAO;
import dao.TarjetaDAO;

@Path("/tarjetas")
public class TarjetaResource {
	
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	@Context
	HttpServletRequest request;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tarjeta> getTarjetasJSON() {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		TarjetaDAO tarjetaDao = new JDBCTarjetaDAO();
		tarjetaDao.setConexion(conn);
		
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		List<Tarjeta> tarjetas = new ArrayList<Tarjeta>(); 
		if(usuario != null)
		{
			tarjetas = tarjetaDao.getTarjetasUsuario(usuario.getId());
		}
		
	    return tarjetas;
	}
	
	@PUT
	@Path("/{lista_id: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String mueveTarjetaJSON(
		@PathParam("lista_id") long lista_id,	
		@QueryParam("tarjeta_id") long tarjeta_id
		) {
		String correct = "{\"ok\":\"false\"}";
		Connection conn = (Connection) sc.getAttribute("dbConn");
		TarjetaDAO tarjetaDao = new JDBCTarjetaDAO();
		tarjetaDao.setConexion(conn);
		
		tarjetaDao.moverTarjeta(tarjeta_id, lista_id);
	
		return correct;
	}
	@GET
	@Path("/{tarjeta_id: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String actualizaTarjetaJSON(
		@PathParam("tarjeta_id") long tarjeta_id,	
		@QueryParam("nombre") String nombre
		) {
		String correct = "{\"ok\":\"false\"}";
		Connection conn = (Connection) sc.getAttribute("dbConn");
		TarjetaDAO tarjetaDao = new JDBCTarjetaDAO();
		tarjetaDao.setConexion(conn);
		
		List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		boolean propietario = false;
		
		if(usuario != null)
		{
			tarjetas = tarjetaDao.getTarjetasUsuario(usuario.getId());
			Iterator<Tarjeta> iter = tarjetas.listIterator();
			
			while(iter.hasNext() && !propietario)
			{
				Tarjeta tarjeta = (Tarjeta) iter.next();
				if(tarjeta.getId() == tarjeta_id)
				{
					propietario = true;
				}
			}
		}
		
		if(propietario)
		{
			if(tarjetaDao.Modificar(nombre, tarjeta_id, usuario.getId()))
			{
				correct = "{\"ok\":\"true\"}";
			}
			else
				correct = "{\"ok\":\"duplicado\"}";
		}
	
		return correct;
	}
	
	@POST
	@Path("/{lista_id: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String guardarTarjetaJSON(
			@PathParam("lista_id") long lista_id,
			@QueryParam("nombre") String nombre
		){
		String correct = "{\"ok\":\"false\"}";
		Connection conn = (Connection) sc.getAttribute("dbConn");
		TarjetaDAO tarjetaDao = new JDBCTarjetaDAO();
		tarjetaDao.setConexion(conn);
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
			
		if(usuario != null)
		{
			if(tarjetaDao.Guardar(nombre, lista_id, usuario.getId()))
			{
				correct = "{\"ok\":\"true\"}";
			}
			else
			{
				correct = "{\"ok\":\"duplicado\"}";
			}
		}
		
		return correct;
	}
	
	@DELETE
	@Path("/{tarjeta_id:[0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String borrarListaJSON(@PathParam("tarjeta_id") long tarjeta_id) {
		String correct = "{\"ok\":\"false\"}";
		Connection conn = (Connection) sc.getAttribute("dbConn");
		TarjetaDAO tarjetaDao = new JDBCTarjetaDAO();
		tarjetaDao.setConexion(conn);
		
		List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		boolean propietario = false;
		
		if(usuario != null)
		{
			tarjetas = tarjetaDao.getTarjetasUsuario(usuario.getId());
			Iterator<Tarjeta> iter = tarjetas.listIterator();
			
			while(iter.hasNext() && !propietario)
			{
				Tarjeta tarjeta = (Tarjeta) iter.next();
				if(tarjeta.getId() == tarjeta_id)
				{
					propietario = true;
				}
			}
		}
		
		if(propietario)
		{
			tarjetaDao.Borrar(tarjeta_id);
			correct = "{\"ok\":\"true\"}";
		}
		
		return correct;
    }

}
