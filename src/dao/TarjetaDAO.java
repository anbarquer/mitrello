package dao;

import java.sql.Connection;
import java.util.List;

import model.Tarjeta;

public interface TarjetaDAO {
	public void setConexion(Connection conexion);
	
	public List<Tarjeta> getTarjetasLista(long listaid);

	public boolean checkTarjeta(String nombre, long usuarioid);

	public long checkListaTablon(long usuarioid, long listaid);

	public long checkTarjetaTablon(long usuarioid, long tarjetaid);

	public boolean Guardar(String nombre, long listaid, long usuarioid);

	public boolean Modificar(String nombre, long tarjetaid, long usuarioid);

	public void Borrar(long tarjetaid);

	public String getNombreTarjeta(long tarjetaid);

	public List<Tarjeta> getTarjetasUsuario(long usuarioid);

	public boolean moverTarjeta(long tarjetaid, long idlista);
	
	public void limpiarTarjetas(long listaid);
	
	public List<Tarjeta> getTarjetas();
	
	public Tarjeta getTarjeta(long tarjetaid);
	
	public long guardar(Tarjeta tarjeta);

}
