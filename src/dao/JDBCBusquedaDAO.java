package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Tablon;

public class JDBCBusquedaDAO implements BusquedaDAO {
	private Connection conexion;

	@Override
	public void setConexion(Connection conexion) {
		this.conexion = conexion;

	}

	@Override
	public List<Tablon> getTablonesBusqueda(String cadena, long usuarioid) {

		ArrayList<Tablon> listaTablones = null;

		if (conexion != null) {
			listaTablones = new ArrayList<Tablon>();

			try {

				Statement stmt = conexion.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT BOARD.ID AS ID, BOARD.NAME AS NAME, BOARD.OWNER AS OWNER FROM USER JOIN BOARD ON (USER.ID = BOARD.OWNER) WHERE USER.ID ="
								+ usuarioid
								+ "AND BOARD.NAME LIKE '%"
								+ cadena.toUpperCase()
								+ "%'");
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

}
