package other;

public class Mensaje {
	private String mensaje;

	public Mensaje() {
		super();
		this.mensaje = "";
	}

	public String Crear(String contenido) {
		this.mensaje = "";
		this.mensaje = contenido;
		return mensaje;
	}

}
