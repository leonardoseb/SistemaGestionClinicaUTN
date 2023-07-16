package dominio;

public class Estado {

	private int idEstado;
	private String descripcion;
	
	public Estado() {}
	
	public Estado(int idEstado, String descripcion) {
		super();
		this.idEstado = idEstado;
		this.descripcion = descripcion;
	}
	//Getterts & Setters
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	//Metodo ToString
	@Override
	public String toString() {
		return "Estado [idEstado=" + idEstado + ", descripcion=" + descripcion + "]";
	}
	
}
