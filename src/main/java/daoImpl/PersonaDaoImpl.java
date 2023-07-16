package daoImpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import Constantes.Queries;
import Constantes.QueriesPacientes;
import dao.PersonaDao;
import dominio.Localidad;
import dominio.Nacionalidad;
import dominio.Persona;

public class PersonaDaoImpl implements PersonaDao{
	
	private Conexion cn;
	
	public PersonaDaoImpl(){}
	
	@Override
	public List<Persona> obtenerPersonas(){
		
		cn = new Conexion();
		cn.Open();
		
		 List<Persona> list = new ArrayList<Persona	>();
		 try
		 {
			 ResultSet rs= cn.query("SELECT persona.DNI,persona.Nombre,persona.Apellido,persona.Sexo,Nacionalidad.Descripcion,persona.FechaNacimiento,persona.Direccion,Localidad.Descripcion,persona.Email,persona.Telefono FROM Persona INNER JOIN Nacionalidad ON Persona.idNacionalidad = Nacionalidad.idNacionalidad INNER JOIN Localidad ON Persona.idLocalidad = Localidad.idLocalidad");
//			 ResultSet rs= cn.query(QueriesPacientes.OBTENER_PERSONAS);
			 while(rs.next())
			 {
				 Persona persona = new Persona();
				 persona.setDni(rs.getString("persona.DNI"));				
				 persona.setNombre(rs.getString("persona.Nombre"));
				 persona.setApellido(rs.getString("persona.Apellido"));				 
				 persona.setSexo(rs.getString("persona.Sexo").charAt(0));			
				 
				 Nacionalidad nacionalidad = new Nacionalidad();
				 nacionalidad.setIdNacionalidad(rs.getInt("Nacionalidad.idNacionalidad"));				 
				 nacionalidad.setDescripcion(rs.getString("Nacionalidad.Descripcion"));
				 persona.setNacionalidad(nacionalidad);	
				 
				 persona.setFechaNacimiento(rs.getDate("FechaNacimiento"));
				 persona.setDireccion(rs.getString("persona.Direccion"));
				 
				 Localidad localidad = new Localidad();
				 localidad.setIdLocalidad(rs.getInt("Localidad.idLocalidad"));
				 localidad.setDescripcion(rs.getString("Localidad.Descripcion"));	
				 persona.setLocalidad(localidad);
				 
				 persona.setEmail(rs.getString("Email"));
				 persona.setTelefono(rs.getString("persona.Telefono"));

				 list.add(persona);
			 }
			 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally
		 {
			 cn.cerrarConexion();
		 }
		 return list;
		
		
		
	}
}
