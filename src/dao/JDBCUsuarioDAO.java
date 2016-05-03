package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import other.BCrypt;
import model.Usuario;

public class JDBCUsuarioDAO implements UsuarioDAO {
	private Connection conexion;

	@Override
	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public int checkUsuario(String username, String password) {

		// Retorna 3 posibles resultados cuando se busca a un usuario:
		// 0 - El usuario no está registrado
		// 1 - El usuario está registrado pero la contraseña que se ha
		// introducido no coincide
		// 2 - Todo correcto

		int estado = 1;
		boolean encontrado = false;

		if (conexion != null) {

			try {
				Statement stmt = conexion.createStatement();
				ResultSet consulta = stmt
						.executeQuery("SELECT USERNAME,PASSWORD FROM Public.User;");

				while (consulta.next() && !encontrado) {
					if (consulta.getString("USERNAME").equals(username)) {
						encontrado = true; // El usuario se ha encontrado

						// Desencripta la contraseña para comprobar si coinciden
						if (BCrypt.checkpw(password,
								consulta.getString("PASSWORD"))) {
							estado = 2;
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		// Si no se encuentra en la tabla
		if (!encontrado) {
			estado = 0;// 0 - Usuario no registrado
		}

		return estado;
	}

	public boolean Buscar(String username) {
		boolean encontrado = false;
		if (conexion != null) {

			try {
				Statement stmt = conexion.createStatement();
				ResultSet consulta = stmt
						.executeQuery("SELECT USERNAME FROM Public.User");

				while (consulta.next() && !encontrado) {
					if (consulta.getString("USERNAME").equals(username)) {
						encontrado = true; // El usuario se ha encontrado

					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return encontrado;
	}

	public Usuario getUsuario(String username) {
		Usuario usuario = null;
		boolean encontrado = false;
		if (conexion != null) {

			try {
				Statement stmt = conexion.createStatement();
				ResultSet consulta = stmt
						.executeQuery("SELECT * FROM Public.User");

				while (consulta.next() && !encontrado) {
					if (consulta.getString("USERNAME").equals(username)) {
						encontrado = true;

						usuario = new Usuario(consulta.getLong("ID"),
								consulta.getString("NAME"),
								consulta.getString("USERNAME"),
								consulta.getString("EMAIL"),
								consulta.getString("PASSWORD"));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return usuario;
	}

	@Override
	public Usuario getUsuario(long id) {

		Usuario usuario = null;
		boolean encontrado = false;

		if (conexion != null) {

			try {
				Statement stmt = conexion.createStatement();
				ResultSet consulta = stmt
						.executeQuery("SELECT * FROM Public.User");

				while (consulta.next() && !encontrado) {
					if (consulta.getLong("ID") == id) {
						encontrado = true;

						usuario = new Usuario(consulta.getLong("ID"),
								consulta.getString("NAME"),
								consulta.getString("USERNAME"),
								consulta.getString("EMAIL"),
								consulta.getString("PASSWORD"));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return usuario;
	}

	@Override
	public void Guardar(String name, String username, String email,
			String password) {

		if (conexion != null) {

			try {
				Statement stmt;
				stmt = conexion.createStatement();
				stmt.executeUpdate("INSERT INTO User (name,username,email,password) VALUES('"
						+ name
						+ "','"
						+ username
						+ "','"
						+ email
						+ "','"
						+ password + "')");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void Actualizar(Usuario usuario) {
		if (conexion != null) {

			try {
				Statement stmt;

				stmt = conexion.createStatement();
				stmt.executeUpdate("UPDATE User SET name='" + usuario.getName()
						+ "', username='" + usuario.getUsername()
						+ "', email='" + usuario.getEmail() + "', password='"
						+ usuario.getPassword() + "' WHERE id = "
						+ usuario.getId());

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void Borrar(long id) {
		if (conexion != null) {

			try {
				Statement stmt;
				stmt = conexion.createStatement();
				stmt.executeUpdate("DELETE FROM User WHERE id =" + id);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Usuario> getUsuarios(){
		
		ArrayList<Usuario> listausuarios = null;
		
		if (conexion != null) {
			listausuarios = new ArrayList<Usuario>();

			try {
				Statement stmt = conexion.createStatement();
				ResultSet consulta = stmt
						.executeQuery("SELECT * FROM Public.User;");

				while (consulta.next()) {
					
					Usuario usuario = new Usuario();
					usuario.setId(consulta.getLong("ID"));
					usuario.setName(consulta.getString("NAME"));
					usuario.setUsername(consulta.getString("USERNAME"));
					usuario.setEmail(consulta.getString("EMAIL"));
					usuario.setPassword(consulta.getString("PASSWORD"));
					listausuarios.add(usuario);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	}
		
	return listausuarios;
		
	}
	
	public long guardar(Usuario usuario){
		long id = -1;
		
		if (conexion != null) {
			try {
				
				Statement stmt = conexion.createStatement();
				stmt.executeUpdate("INSERT INTO USER (name,username,password) VALUES('"
						+usuario.getName()+"','"
						+usuario.getUsername()+"','"
						+usuario.getPassword()+"')",Statement.RETURN_GENERATED_KEYS);
				
				ResultSet genKeys = stmt.getGeneratedKeys();
				
				if (genKeys.next())
				    id = genKeys.getInt(1);
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return id;
	}

}
