package Constantes;

public class QueriesPersonas {
	
	public static String OBTENER_PERSONAS = "SELECT persona.DNI,persona.Nombre,persona.Apellido,persona.Sexo,Nacionalidad.Descripcion,persona.FechaNacimiento,persona.Direccion,Localidad.Descripcion,persona.Email,persona.Telefono FROM Persona INNER JOIN Nacionalidad ON Persona.idNacionalidad = Nacionalidad.idNacionalidad INNER JOIN Localidad ON Persona.idLocalidad = Localidad.idLocalidad";

}
