package dominio;

public class Nacionalidad {
	
	private int IdNacionalidad;
	private String Descripcion;
	
	public Nacionalidad() {}
	
	public Nacionalidad(int idNacionalidad) {
		this.IdNacionalidad = idNacionalidad;
	}
	
	
	public int getIdNacionalidad() {
		return IdNacionalidad;
	}
	public void setIdNacionalidad(int idNacionalidad) {
		IdNacionalidad = idNacionalidad;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	
	
	@Override
	public String toString() {
		return IdNacionalidad+Descripcion;
	}
	
	

}
