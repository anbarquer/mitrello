package dao;

import java.sql.Connection;
import java.util.List;

import model.Tablon;

public interface BusquedaDAO {
	public void setConexion(Connection conexion);

	public List<Tablon> getTablonesBusqueda(String cadena, long usuarioid);
}
