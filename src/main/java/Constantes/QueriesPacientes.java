package Constantes;

public class QueriesPacientes {
	
	public static String OBTENER_TODOS = "SELECT * FROM Paciente paciente "
			+ "INNER JOIN Persona persona ON persona.DNI = paciente.DNI";

}
