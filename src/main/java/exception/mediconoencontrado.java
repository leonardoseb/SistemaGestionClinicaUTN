package exception;

public class mediconoencontrado extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public mediconoencontrado() {
		
	}
	public String getMessage()  {
		return "Medico no encontrado";
	}
}
