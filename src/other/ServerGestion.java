package other;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.JDBCListaDAO;
import dao.JDBCTablonDAO;
import dao.JDBCTarjetaDAO;
import dao.JDBCUsuarioDAO;
import dao.ListaDAO;
import dao.TablonDAO;
import dao.TarjetaDAO;
import dao.UsuarioDAO;
import model.Tablon;
import model.Tarjeta;
import model.Usuario;

import java.util.Iterator;
import java.util.List;

import model.Lista;

public class ServerGestion {

	private boolean propietarioLista(Connection conexion, long idusuario,
			long idatributo) {

		boolean propietario = false;

		ListaDAO listaDao = getListaDAO(conexion);
		List<Lista> l = listaDao.getListasUsuario(idusuario);
		Iterator<Lista> iter = l.listIterator();

		while (iter.hasNext() && !propietario) {

			Lista lista = (Lista) iter.next();
			if (lista.getId() == idatributo) {
				propietario = true;
			}

		}

		return propietario;

	}

	private boolean propietarioTarjeta(Connection conexion, long idusuario,
			long idatributo) {

		boolean propietario = false;

		TarjetaDAO tarjetaDao = getTarjetaDAO(conexion);
		List<Tarjeta> l = tarjetaDao.getTarjetasUsuario(idusuario);
		Iterator<Tarjeta> iter = l.listIterator();

		while (iter.hasNext() && !propietario) {

			Tarjeta tarjeta = (Tarjeta) iter.next();
			if (tarjeta.getId() == idatributo) {
				propietario = true;
			}

		}

		return propietario;

	}

	private boolean propietarioTablon(Connection conexion, long idusuario,
			long idatributo) {

		boolean propietario = false;

		TablonDAO tablonDao = getTablonDAO(conexion);
		List<Tablon> l = tablonDao.getTablonesUsuario(idusuario);
		Iterator<Tablon> iter = l.listIterator();

		while (iter.hasNext() && !propietario) {

			Tablon tablon = (Tablon) iter.next();
			if (tablon.getId() == idatributo) {
				propietario = true;
			}
		}

		return propietario;
	}

	public ServerGestion() {
		super();
	}

	public Usuario getUsuario(HttpServletRequest request) {

		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		return usuario;
	}

	public TablonDAO getTablonDAO(Connection conexion) {
		TablonDAO tablonDao = new JDBCTablonDAO();
		tablonDao.setConexion(conexion);

		return tablonDao;
	}

	public ListaDAO getListaDAO(Connection conexion) {
		ListaDAO listaDao = new JDBCListaDAO();
		listaDao.setConexion(conexion);

		return listaDao;
	}

	public UsuarioDAO getUsuarioDAO(Connection conexion) {
		UsuarioDAO usuarioDao = new JDBCUsuarioDAO();
		usuarioDao.setConexion(conexion);

		return usuarioDao;
	}

	public TarjetaDAO getTarjetaDAO(Connection conexion) {
		TarjetaDAO tarjetaDao = new JDBCTarjetaDAO();
		tarjetaDao.setConexion(conexion);

		return tarjetaDao;
	}

	public boolean haySesion(String atributo, HttpServletRequest request,
			Connection conexion, long idatributo) {

		boolean haySesion = false;
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		if (usuario != null) {
			long idusuario = usuario.getId();
			if (atributo == "lista") {
				haySesion = propietarioLista(conexion, idusuario, idatributo);
			} else {
				if (atributo == "tarjeta") {
					haySesion = propietarioTarjeta(conexion, idusuario,
							idatributo);
				} else {
					if (atributo == "tablon") {
						haySesion = propietarioTablon(conexion, idusuario,
								idatributo);
					}
				}
			}
		}

		return haySesion;
	}

	public boolean haySesion(HttpServletRequest request) {
		boolean haySesion = false;
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		if (usuario != null)
			haySesion = true;

		return haySesion;
	}
}
