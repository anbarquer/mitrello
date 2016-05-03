package dao;

import java.sql.Connection;
import java.util.List;

import model.Usuario;

public interface UsuarioDAO {
	public void setConexion(Connection conexion);

	public int checkUsuario(String username, String password);

	public Usuario getUsuario(String username);

	public boolean Buscar(String username);

	public Usuario getUsuario(long id);

	public void Guardar(String name, String username, String email,
			String password);

	public void Actualizar(Usuario User);

	public void Borrar(long id);
	
	public List<Usuario> getUsuarios();
	
	public long guardar(Usuario usuario);
	
}
