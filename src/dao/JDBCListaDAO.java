package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Lista;

public class JDBCListaDAO implements ListaDAO {
	private Connection conexion;

	@Override
	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}
	
	@Override
	public List<Lista> getListasUsuario(long usuarioid){
		ArrayList<Lista> listaTablones = null;

		if (conexion != null) {
			listaTablones = new ArrayList<Lista>();

			try {

				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT LIST.ID AS ID, LIST.NAME AS NAME, LIST.BOARD AS BOARD FROM USER JOIN BOARD ON (USER.ID=BOARD.OWNER) JOIN LIST ON (BOARD.ID=LIST.BOARD) WHERE OWNER="
								+ usuarioid);
				while (rs.next()) {
					Lista lista = new Lista();
					lista.setId(rs.getLong("ID"));
					lista.setName(rs.getString("NAME"));
					lista.setBoard(rs.getLong("BOARD"));
					listaTablones.add(lista);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaTablones;
	}

	@Override
	public List<Lista> getListasTablon(long tablonid) {

		ArrayList<Lista> listaListas = null;

		if (conexion != null) {
			listaListas = new ArrayList<Lista>();

			try {

				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM Public.List WHERE BOARD="
								+ tablonid);
				while (rs.next()) {
					Lista lista = new Lista();
					lista.setId(rs.getLong("ID"));
					lista.setName(rs.getString("NAME"));
					lista.setBoard(rs.getLong("BOARD"));
					listaListas.add(lista);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaListas;
	}

	@Override
	public boolean checkLista(String nombre, long usuarioid, long tablonid) {
		boolean encontrado = false;

		if (conexion != null) {
			try {
				Statement stmt = conexion.createStatement();
				ResultSet consulta = stmt
						.executeQuery("SELECT DISTINCT LIST.NAME AS NAME FROM LIST JOIN BOARD ON(LIST.BOARD = BOARD.ID) WHERE BOARD.ID ="
								+ tablonid);

				while (consulta.next() && !encontrado) {
					if (consulta.getString("NAME").equals(nombre)) {
						encontrado = true; // La lista se ha encontrado
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return encontrado;
	}

	@Override
	public long checkListaTablon(long usuarioid, long listaid) {
		long idtablon = 0;

		if (conexion != null) {
			try {
				Statement stmt = conexion.createStatement();
				ResultSet consulta = stmt
						.executeQuery("SELECT DISTINCT BOARD.ID AS ID FROM USER JOIN BOARD ON (USER.ID = BOARD.OWNER) JOIN LIST ON (BOARD.ID = LIST.BOARD) WHERE USER.ID ="
								+ usuarioid + "AND LIST.ID =" + listaid);

				consulta.next();
				idtablon = consulta.getLong("ID");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return idtablon;
	}

	@Override
	public boolean Guardar(String nombre, long tablonid, long usuarioid) {

		boolean guardado = false;
		if (conexion != null) {
			if (!checkLista(nombre, usuarioid, tablonid)) {
				try {
					Statement stmt = conexion.createStatement();
					stmt.executeUpdate("INSERT INTO List (name,board) VALUES('"
							+ nombre + "'," + tablonid + ")");
					guardado = true;

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return guardado;
	}

	@Override
	public boolean Modificar(String nombre, long listaid, long usuarioid) {

		boolean modificado = false;
		long idtablon = 0;
		if (conexion != null) {

			// Obtiene el identificador del tablón que contiene la lista que se
			// desea modificar
			idtablon = checkListaTablon(usuarioid, listaid);

			// A continuación se busca en la lista si hay alguna lista con ese
			// nombre
			if (!checkLista(nombre, usuarioid, idtablon)) {
				try {
					Statement stmt = conexion.createStatement();
					stmt.executeUpdate("UPDATE List SET name='" + nombre
							+ "' WHERE id=" + listaid);
					modificado = true;

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return modificado;
	}

	@Override
	public void Borrar(long listaid) {
		if (conexion != null) {
			try {
				Statement stmt = conexion.createStatement();
				stmt.executeUpdate("DELETE FROM List WHERE id =" + listaid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public String getNombreLista(long listaid) {

		String nombre = null;

		if (conexion != null) {
			try {
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT NAME FROM LIST WHERE ID="
								+ listaid);
				rs.next();
				nombre = rs.getString("NAME");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return nombre;
	}
	
	
	@Override
	public List<Lista> getListas(){
		
		ArrayList<Lista> listaTablones = null;

		if (conexion != null) {
			listaTablones = new ArrayList<Lista>();

			try {

				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM LIST");
								
				while (rs.next()) {
					Lista lista = new Lista();
					lista.setId(rs.getLong("ID"));
					lista.setName(rs.getString("NAME"));
					lista.setBoard(rs.getLong("BOARD"));
					listaTablones.add(lista);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaTablones;
	}
	
	public Lista getLista(long listaid){
		
		Lista lista = null;

		if (conexion != null) {
	
			try {

				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM LIST WHERE ID="+listaid);
				
				rs.next();
				lista = new Lista();
				lista.setId(rs.getLong("ID"));
				lista.setName(rs.getString("NAME"));
				lista.setBoard(rs.getLong("BOARD"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return lista;
	}
	
	public long guardar(Lista lista){
		
		long id = -1;
		
		if (conexion != null) {
			try {
				Statement stmt = conexion.createStatement();
				stmt.executeUpdate("INSERT INTO LIST (name,board) VALUES('"
						+lista.getName()+"','"
						+lista.getBoard()+"')",Statement.RETURN_GENERATED_KEYS);
				
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
