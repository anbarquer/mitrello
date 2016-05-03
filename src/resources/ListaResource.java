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

import model.Lista;
import model.Usuario;
import dao.JDBCListaDAO;
import dao.ListaDAO;
import exceptions.CustomNotFoundException;

@Path("/listas")
public class ListaResource {
	
	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	@Context
	HttpServletRequest request;
	
	@GET
	@Path("/{tablon_id: [0-9]+}")	  
	@Produces(MediaType.APPLICATION_JSON)
	public List<Lista> getListaJSON(@PathParam("tablon_id") long tablon_id) {  
		Connection conn = (Connection) sc.getAttribute("dbConn");
		ListaDAO listaDao = new JDBCListaDAO();
		listaDao.setConexion(conn);
		
		List<Lista> listas = listaDao.getListasTablon(tablon_id);
		
		if (listas == null) {
		    throw new CustomNotFoundException("Listas de, " + tablon_id + ", no se ha encontrado");
		  }
		
	    return listas; 
	  }
	
	@PUT
	@Path("/{lista_id:[0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String modificarListaJSON(
			@PathParam("lista_id") long lista_id, 
			@QueryParam("nombre") String nombre
			){
		
		String correct = "{\"ok\":\"false\"}";
		Connection conn = (Connection) sc.getAttribute("dbConn");
		ListaDAO listaDao = new JDBCListaDAO();
		listaDao.setConexion(conn);
		List<Lista> listas = new ArrayList<Lista>();
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		boolean propietario = false;
		
		if(usuario != null)
		{
			listas = listaDao.getListasUsuario(usuario.getId());
			Iterator<Lista> iter = listas.listIterator();
			
			while(iter.hasNext() && !propietario)
			{
				Lista lista = (Lista) iter.next();
				
				if(lista.getId() == lista_id)
				{
					propietario = true;
				}
			}
		}
		
		if(propietario)
		{
			if(listaDao.Modificar(nombre, lista_id, usuario.getId()))
			{
				correct = "{\"ok\":\"true\"}";
			}
			else
				correct = "{\"ok\":\"duplicado\"}";
		}
	
		return correct;
	}
	
	@POST
	@Path("/{tablon_id: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String guardarTablonJSON(
			@PathParam("tablon_id") long tablon_id,
			@QueryParam("nombre") String nombre
		){
		String correct = "{\"ok\":\"false\"}";
		Connection conn = (Connection) sc.getAttribute("dbConn");
		ListaDAO listaDao = new JDBCListaDAO();
		listaDao.setConexion(conn);
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		if(usuario != null)
		{
			if(listaDao.Guardar(nombre, tablon_id,usuario.getId()))
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
	@Path("/{lista_id:[0-9]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String borrarListaJSON(@PathParam("lista_id") int lista_id) {
		String correct = "{\"ok\":\"false\"}";
		Connection conn = (Connection) sc.getAttribute("dbConn");
		ListaDAO listaDao = new JDBCListaDAO();
		listaDao.setConexion(conn);
		
		List<Lista> listas = new ArrayList<Lista>();
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		
		boolean propietario = false;
		
		if(usuario != null)
		{
			listas = listaDao.getListasUsuario(usuario.getId());
			Iterator<Lista> iter = listas.listIterator();
			
			while(iter.hasNext() && !propietario)
			{
				Lista lista = (Lista) iter.next();
				
				if(lista.getId() == lista_id)
				{
					propietario = true;
				}
			}
		}
		
		if(propietario)
		{
			listaDao.Borrar(lista_id);
			correct = "{\"ok\":\"true\"}";
		}

		return correct;
    }
}
