package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Tarjeta;

public class JDBCTarjetaDAO implements TarjetaDAO {
	private Connection conexion;

	@Override
	public void setConexion(Connection conexion) {
		this.conexion = conexion;

	}
	
	@Override
	public List<Tarjeta> getTarjetasLista(long listaid) {
		ArrayList<Tarjeta> listaTarjetas = null;

		if (conexion != null) {
			listaTarjetas = new ArrayList<Tarjeta>();

			try {

				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM Public.Card WHERE LIST="
								+ listaid);
				while (rs.next()) {
					Tarjeta tarjeta = new Tarjeta();
					tarjeta.setId(rs.getLong("ID"));
					tarjeta.setName(rs.getString("NAME"));
					tarjeta.setList(rs.getLong("LIST"));
					listaTarjetas.add(tarjeta);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaTarjetas;
	}

	@Override
	public boolean checkTarjeta(String nombre, long tablonid) {
		boolean encontrado = false;

		if (conexion != null) {
			try {
				Statement stmt = conexion.createStatement();
				ResultSet consulta = stmt
						.executeQuery("SELECT DISTINCT CARD.NAME AS NAME FROM BOARD JOIN LIST ON (BOARD.ID = LIST.BOARD) JOIN CARD ON (LIST.ID = CARD.LIST) WHERE BOARD.ID="
								+ tablonid);

				while (consulta.next() && !encontrado) {
					if (consulta.getString("NAME").equals(nombre)) {
						encontrado = true; // La tarjeta se ha encontrado
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

				if(consulta.next())
					idtablon = consulta.getLong("ID");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return idtablon;
	}

	@Override
	public long checkTarjetaTablon(long usuarioid, long tarjetaid) {
		long idtablon = 0;

		if (conexion != null) {
			try {
				Statement stmt = conexion.createStatement();
				ResultSet consulta = stmt
						.executeQuery("SELECT DISTINCT BOARD.ID AS ID FROM USER JOIN BOARD ON (USER.ID = BOARD.OWNER) JOIN LIST ON (BOARD.ID = LIST.BOARD) JOIN CARD ON (CARD.LIST = LIST.ID) WHERE CARD.ID = "
								+ tarjetaid + " AND USER.ID =" + usuarioid);

				consulta.next();
				idtablon = consulta.getLong("ID");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return idtablon;
	}

	@Override
	public boolean Guardar(String nombre, long listaid, long usuarioid) {
		boolean guardado = false;
		long idtablon = 0;
		if (conexion != null) {

			idtablon = checkListaTablon(usuarioid, listaid);

			if (!checkTarjeta(nombre, idtablon)) {
				try {
					Statement stmt = conexion.createStatement();
					stmt.executeUpdate("INSERT INTO Card (name,list) VALUES('"
							+ nombre + "'," + listaid + ")");
					guardado = true;

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return guardado;
	}

	@Override
	public boolean Modificar(String nombre, long tarjetaid, long usuarioid) {
		boolean modificado = false;
		long idtablon = 0;
		if (conexion != null) {

			idtablon = checkTarjetaTablon(usuarioid, tarjetaid);

			if (!checkTarjeta(nombre, idtablon)) {
				try {
					Statement stmt = conexion.createStatement();
					stmt.executeUpdate("UPDATE Card SET name='" + nombre
							+ "' WHERE id=" + tarjetaid);
					modificado = true;

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return modificado;
	}

	@Override
	public void Borrar(long tarjetaid) {
		if (conexion != null) {
			try {
				Statement stmt = conexion.createStatement();
				stmt.executeUpdate("DELETE FROM Card WHERE id =" + tarjetaid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public String getNombreTarjeta(long tarjetaid) {
		String nombre = null;

		if (conexion != null) {
			try {
				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT NAME FROM CARD WHERE ID="
								+ tarjetaid);
				rs.next();
				nombre = rs.getString("NAME");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return nombre;
	}

	@Override
	public List<Tarjeta> getTarjetasUsuario(long usuarioid) {

		ArrayList<Tarjeta> listaTarjetas = null;

		if (conexion != null) {
			listaTarjetas = new ArrayList<Tarjeta>();

			try {

				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT CARD.ID AS ID, CARD.NAME AS NAME, CARD.LIST AS LIST FROM CARD JOIN LIST ON (CARD.LIST = LIST.ID) JOIN BOARD ON (LIST.BOARD = BOARD.ID) JOIN USER ON (BOARD.OWNER = USER.ID) WHERE USER.ID ="
								+ usuarioid);
				while (rs.next()) {
					Tarjeta tarjeta = new Tarjeta();
					tarjeta.setId(rs.getLong("ID"));
					tarjeta.setName(rs.getString("NAME"));
					tarjeta.setList(rs.getLong("LIST"));
					listaTarjetas.add(tarjeta);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaTarjetas;

	}

	@Override
	public boolean moverTarjeta(long tarjetaid, long idlista) {
		boolean modificado = false;
		if (conexion != null) {

			try {
				Statement stmt = conexion.createStatement();
				stmt.executeUpdate("UPDATE Card SET LIST=" + idlista
						+ " WHERE id=" + tarjetaid);
				modificado = true;

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return modificado;

	}
	
	public void limpiarTarjetas(long listaid){
		if (conexion != null) {
			try {
				Statement stmt = conexion.createStatement();
				stmt.executeUpdate("DELETE FROM CARD WHERE CARD.LIST=" + listaid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public List<Tarjeta> getTarjetas(){
		
		ArrayList<Tarjeta> listaTarjetas = null;

		if (conexion != null) {
			listaTarjetas = new ArrayList<Tarjeta>();

			try {

				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM Public.Card");
				while (rs.next()) {
					Tarjeta tarjeta = new Tarjeta();
					tarjeta.setId(rs.getLong("ID"));
					tarjeta.setName(rs.getString("NAME"));
					tarjeta.setList(rs.getLong("LIST"));
					listaTarjetas.add(tarjeta);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return listaTarjetas;
	}
	
	public Tarjeta getTarjeta(long tarjetaid)
	{
		Tarjeta tarjeta = null;
		if (conexion != null) {
			
			try {

				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM CARD WHERE ID="+tarjetaid);
				
				rs.next();
				tarjeta = new Tarjeta();
				tarjeta.setId(rs.getLong("ID"));
				tarjeta.setName(rs.getString("NAME"));
				tarjeta.setList(rs.getLong("LIST"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return tarjeta;
		
	}
	
	public long guardar(Tarjeta tarjeta){
		
		long id = -1;
		
		if (conexion != null) {
			try {
				
				Statement stmt = conexion.createStatement();
				stmt.executeUpdate("INSERT INTO LIST (name,board) VALUES('"
						+tarjeta.getName()+"','"
						+tarjeta.getList()+"')",Statement.RETURN_GENERATED_KEYS);
				
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
