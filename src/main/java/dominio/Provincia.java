package dominio;

public class Provincia {
	
	private int IdProvincia;
	private String Descripcion;
	
	public Provincia() {}		
	
	/**
	 * @param idProvincia
	 * @param descripcion
	 */
	public Provincia(int idProvincia, String descripcion) {
		super();
		IdProvincia = idProvincia;
		Descripcion = descripcion;
	}



	public int getIdProvincia() {
		return IdProvincia;
	}
	public void setIdProvincia(int idProvincia) {
		IdProvincia = idProvincia;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}		
	
	@Override
	public String toString() {
		return IdProvincia +Descripcion;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
