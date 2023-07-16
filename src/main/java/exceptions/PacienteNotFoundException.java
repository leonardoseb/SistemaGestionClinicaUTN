package exceptions;

public class PacienteNotFoundException extends RuntimeException {
	
	public PacienteNotFoundException() {
		
	}
	
	@Override
	public String getMessage() {
		return "Paciente inexistente.";
	}

}
