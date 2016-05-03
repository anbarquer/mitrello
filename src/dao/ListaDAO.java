package dao;

import java.sql.Connection;
import java.util.List;

import model.Lista;

public interface ListaDAO {
	public void setConexion(Connection conexion);
	
	public List<Lista> getListasUsuario(long usuarioid);

	public List<Lista> getListasTablon(long tablonid);

	public long checkListaTablon(long usuarioid, long listaid);

	public boolean checkLista(String nombre, long usuarioid, long tablonid);

	public boolean Guardar(String nombre, long tablonid, long usuarioid);

	public boolean Modificar(String nombre, long listaid, long usuarioid);

	public void Borrar(long listaid);

	public String getNombreLista(long listaid);
	
	public List<Lista> getListas();
	
	public Lista getLista(long listaid);
	
	public long guardar(Lista lista);
	
}
