package dao;

import java.sql.Connection;
import java.util.List;

import model.Tablon;

public interface TablonDAO {
	public void setConexion(Connection conexion);

	public List<Tablon> getTablonesUsuario(long usuarioid);

	public boolean checkTablon(String nombre, long usuarioid);

	public boolean Guardar(String nombre, long usuarioid);

	public boolean Modificar(String nombre, long tablonid, long usuarioid);

	public void Borrar(long boardid);

	public String getNombreTablon(long tablonid);
	
	public List<Tablon> getTablones();
	
	public Tablon getTablon(long tablonid);
	
	public long guardar(Tablon tablon);

}
