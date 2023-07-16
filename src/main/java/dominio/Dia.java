package dominio;

public class Dia {
	private int idDia;
	private String Descripcion;

	public Dia() {

	}

	public int getIdDia() {
		return idDia;
	}

	public void setIdDia(int idDia) {
		this.idDia = idDia;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public Dia(int idDia, String descripcion) {
		super();
		this.idDia = idDia;
		Descripcion = descripcion;
	}

}
