package dominio;

public class Localidad {
	
	private int IdLocalidad;
	private Provincia provincia;
	private String 	Descripcion;
	
	public Localidad(){}
	
	public Localidad(int idLocalidad) {
		this.IdLocalidad = idLocalidad;
	}
	

	public Localidad(int idLocalidad, Provincia provincia, String descripcion) {
		super();
		IdLocalidad = idLocalidad;
		this.provincia = provincia;
		Descripcion = descripcion;
	}

	public int getIdLocalidad() {
		return IdLocalidad;
	}
	public void setIdLocalidad(int idLocalidad) {
		IdLocalidad = idLocalidad;
	}
	public Provincia getProvincia() {
		return provincia;
	}
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	
	
	@Override
	public String toString() {
		return "Localidad [IdLocalidad=" + IdLocalidad + ", provincia=" + provincia + ", Descripcion=" + Descripcion
				+ "]";
	}
	
	
	
	
	
	
	
	

}
