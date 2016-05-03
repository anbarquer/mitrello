package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Tablon;

public class JDBCTablonDAO implements TablonDAO {
	private Connection conexion = null;

	@Override
	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public List<Tablon> getTablonesUsuario(long usuarioid) {

		ArrayList<Tablon> listaTablones = null;

		if (conexion != null) {
			listaTablones = new ArrayList<Tablon>();

			try {

				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM Public.Board WHERE OWNER="
								+ usuarioid);
				while (rs.next()) {
					Tablon tablon = new Tablon();
					tablon.setId(rs.getLong("ID"));
					tablon.setName(rs.getString("NAME"));
					tablon.setOwner(rs.getLong("OWNER"));
					listaTablones.add(tablon);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaTablones;
	}

	@Override
	public boolean checkTablon(String nombre, long usuarioid) {
		boolean encontrado = false;

		if (conexion != null) {
			try {
				Statement stmt = conexion.createStatement();
				ResultSet consulta = stmt
						.executeQuery("SELECT DISTINCT BOARD.NAME AS NAME FROM BOARD WHERE OWNER="
								+ usuarioid);

				while (consulta.next() && !encontrado) {
					if (consulta.getString("NAME").equals(nombre)) {
						encontrado = true; // El tablón se ha encontrado
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return encontrado;
	}

	@Override
	public boolean Guardar(String nombre, long usuarioid) {

		boolean guardado = false;
		if (conexion != null) {

			if (!checkTablon(nombre, usuarioid)) {
				try {
					Statement stmt = conexion.createStatement();
					stmt.executeUpdate("INSERT INTO Board (name,owner) VALUES('"
							+ nombre + "'," + usuarioid + ")");
					guardado = true;

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return guardado;

	}

	@Override
	public boolean Modificar(String nombre, long tablonid, long usuarioid) {
		boolean modificado = false;
		if (conexion != null) {

			if (!checkTablon(nombre, usuarioid)) {
				try {
					Statement stmt = conexion.createStatement();
					stmt.executeUpdate("UPDATE Board SET name='" + nombre
							+ "' WHERE id=" + tablonid + "AND OWNER ="
							+ usuarioid);
					modificado = true;

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return modificado;
	}

	@Override
	public void Borrar(long tablonid) {

		if (conexion != null) {
			try {
				Statement stmt = conexion.createStatement();
				stmt.executeUpdate("DELETE FROM Board WHERE id =" + tablonid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public String getNombreTablon(long tablonid) {

		String nombre = null;

		if (conexion != null) {
			try {
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT NAME FROM BOARD WHERE ID="
								+ tablonid);
				rs.next();
				nombre = rs.getString("NAME");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return nombre;

	}
	
	public List<Tablon> getTablones(){
		
		ArrayList<Tablon> listaTablones = null;

		if (conexion != null) {
			listaTablones = new ArrayList<Tablon>();

			try {

				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM Public.Board");
				
				while (rs.next()) {
					Tablon tablon = new Tablon();
					tablon.setId(rs.getLong("ID"));
					tablon.setName(rs.getString("NAME"));
					tablon.setOwner(rs.getLong("OWNER"));
					listaTablones.add(tablon);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaTablones;
	}
	
	public Tablon getTablon(long tablonid){
		Tablon tablon = null;

		if (conexion != null) {
	
			try {

				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM BOARD WHERE ID="+tablonid);
				
				if(rs.next())
				{
					tablon = new Tablon();
					tablon.setId(rs.getLong("ID"));
					tablon.setName(rs.getString("NAME"));
					tablon.setOwner(rs.getLong("OWNER"));
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return tablon;
	}
	
	public long guardar(Tablon tablon){
		long id = -1;
			
			if (conexion != null) {
				try {
					Statement stmt = conexion.createStatement();
					stmt.executeUpdate("INSERT INTO BOARD (name,owner) VALUES('"
							+tablon.getName()+"','"
							+tablon.getOwner()+"')",Statement.RETURN_GENERATED_KEYS);
					
					ResultSet genKeys = stmt.getGeneratedKeys();
					
					if (genKeys.next())
					    id = genKeys.getInt(1);
					
		
				} catch (SQLException e) {
					e.printStackTrace();
				}
			

		}
			
		return id;
	}


}
