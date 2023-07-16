package daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.PacienteNotFoundException;
import dao.PacienteDao;
import dominio.Localidad;
import dominio.Nacionalidad;
import dominio.Paciente;
import dominio.Provincia;

public class PacienteDaoImpl implements PacienteDao{
	
	private static final String EXISTE_PACIENTE = "SELECT CASE WHEN EXISTS ( SELECT * FROM Persona WHERE DNI = ?) THEN 'TRUE' ELSE 'FALSE' END";
	private static final String DELETE = "UPDATE Paciente SET Estado = 0 WHERE idPaciente = ?";
	private static final String SELECT_IDPACIENTE_BY_DNI = "SELECT p.idPaciente FROM Paciente p WHERE p.DNI = ?";
	private static final String READ_PACIENTE = "SELECT Pa.Estado, Pa.idPaciente, Pe.DNI, Pe.Nombre, Pe.Apellido, Pe.Sexo, Pe.idNacionalidad, N.Descripcion as DescripcionNac, Pe.FechaNacimiento, Pe.Direccion, Pe.idLocalidad, Lo.Descripcion as DescripcionLocalidad, Pe.Email, Pe.Telefono, Lo.idProvincia, Pro.Descripcion as DescripcionProvincia FROM Paciente as Pa INNER JOIN Persona AS Pe ON Pe.Dni = Pa.DNI INNER JOIN Nacionalidad as N on Pe.idNacionalidad = N.idNacionalidad INNER JOIN Localidad as Lo ON Lo.idLocalidad = Pe.idLocalidad INNER JOIN Provincia as Pro ON Pro.idProvincia = Lo.idProvincia WHERE Pe.DNI = ?";
	private static final String UPDATE = "UPDATE Persona SET Nombre = ?, Apellido = ?, Sexo = ?, idNacionalidad = ?, FechaNacimiento = ?, Direccion = ?, idLocalidad = ?, Email = ?, Telefono = ? WHERE DNI = ?";
	
	//
	private static final String DELETE_PACIENTE_BY_DNI = "UPDATE Paciente set Estado = 0 WHERE DNI like ?";
	private static final String READ_ALL = "SELECT * FROM Paciente AS pa INNER JOIN Persona AS pe ON pe.DNI LIKE pa.DNI INNER JOIN Nacionalidad AS N ON pe.idNacionalidad = N.idNacionalidad WHERE pa.Estado = 1"; 	
	private static final String EXISTS = "SELECT CASE WHEN exists ( SELECT * FROM Persona WHERE DNI = ?) THEN 'TRUE' ELSE 'FALSE' END";
	
	
	private Conexion conexion; 
	
	public PacienteDaoImpl() {}

	@Override
	public List<Paciente> obtenerPacientes() {
		
		conexion = new Conexion();
		conexion.Open();
		List <Paciente> list = new ArrayList<Paciente>();
		try 
		{
			ResultSet rs = conexion.query("SELECT * FROM Paciente paciente "
					+ "INNER JOIN Persona persona ON persona.DNI = paciente.DNI "
					+ "INNER JOIN Nacionalidad ON persona.idNacionalidad = Nacionalidad.idNacionalidad "
					+ "INNER JOIN Localidad ON persona.idLocalidad = Localidad.idLocalidad "
					+ "INNER JOIN Provincia ON Localidad.idProvincia = Provincia.idProvincia "
					+ " WHERE paciente.Estado = 1");

			while (rs.next())
			{
				Paciente paciente = new Paciente();
				
				paciente.setIdPaciente(rs.getInt("paciente.idPaciente"));
				paciente.setDni(rs.getString("persona.DNI"));
				paciente.setEstado(rs.getBoolean("paciente.Estado"));
				
				paciente.setNombre(rs.getString("persona.Nombre"));
				paciente.setApellido(rs.getString("persona.Apellido"));				 
				paciente.setSexo(rs.getString("persona.Sexo").charAt(0));			
				 
				Nacionalidad nacionalidad = new Nacionalidad();
				nacionalidad.setIdNacionalidad(rs.getInt("Nacionalidad.idNacionalidad"));				 
				nacionalidad.setDescripcion(rs.getString("Nacionalidad.Descripcion"));
				paciente.setNacionalidad(nacionalidad);	
				
				paciente.setFechaNacimiento(rs.getDate("persona.FechaNacimiento"));
				paciente.setDireccion(rs.getString("persona.Direccion"));
				
				Localidad localidad = new Localidad();
				Provincia provincia = new Provincia(1, "Buenos Aires");
				localidad.setProvincia(provincia);
				localidad.setIdLocalidad(rs.getInt("Localidad.idLocalidad"));
				localidad.setDescripcion(rs.getString("Localidad.Descripcion"));
				localidad.getProvincia().setIdProvincia(rs.getInt("Provincia.idProvincia"));
				localidad.getProvincia().setDescripcion(rs.getString("Provincia.Descripcion"));
				paciente.setLocalidad(localidad);
				
				paciente.setEmail(rs.getString("persona.Email"));
				paciente.setTelefono(rs.getString("persona.Telefono"));			
				
				list.add(paciente);
			}
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			conexion.cerrarConexion();
		}
		
		return list;
	}

	@Override
	public boolean insertarPaciente(Paciente paciente) {
		boolean wasInserted = true;
		
		Connection connection = null;
		
		try{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			CallableStatement cst = connection.prepareCall("call registrarPaciente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			cst.setString(1, paciente.getDni());
			cst.setString(2, paciente.getNombre());
			cst.setString(3, paciente.getApellido());
			cst.setString(4, String.valueOf(paciente.getSexo()));
			cst.setInt(5, paciente.getNacionalidad().getIdNacionalidad());
			cst.setDate(6, (Date)paciente.getFechaNacimiento());
			cst.setString(7, paciente.getDireccion());
			cst.setInt(8, paciente.getLocalidad().getIdLocalidad());
			cst.setString(9, paciente.getEmail());
			cst.setInt(10, 1);
			cst.setString(11, paciente.getTelefono());		
			
			if (cst.executeUpdate()>0) {
				connection.setAutoCommit(false);
				connection.commit();
				wasInserted = true;
			}
			
		 }
		catch(Exception e)
		{
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally
		{
			conexion.cerrarConexion();
		}
		return wasInserted;
	}

	@Override
	public boolean editarPaciente(Paciente paciente) {
		boolean estado = true;

		conexion = new Conexion();
		conexion.Open();	

		String query = "UPDATE paciente SET idPaciente='"+paciente.getIdPaciente()+"', DNI='"+paciente.getDni()+"', Estado='"+paciente.getEstado()+"' WHERE idPaciente='"+paciente.getIdPaciente()+"'";
		try
		 {
			estado=conexion.execute(query);
		 }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			conexion.cerrarConexion();
		}
		return estado;
	}

	@Override
	public boolean borrarPaciente(String dni) {
		
		int idPaciente = getIdPacienteByDNI(dni);
		
		boolean estado = true;
		PreparedStatement statement;

		Connection connection = null;
		
		System.out.println("idPaciente a borrar: " + idPaciente);
		
		try 
		{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(DELETE);
			statement.setInt(1, idPaciente);
			
			if(statement.executeUpdate() > 0)
			{
				connection.setAutoCommit(false);
				connection.commit();
				estado = true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conexion.cerrarConexion();
		}
		return estado;
	}
	
	@Override
	public Paciente obtenerPacientePorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existePaciente(String dni) {
		
		conexion = new Conexion();
		conexion.Open();
		
		Connection connection = conexion.getSQLConexion();
		
		PreparedStatement statement;
		boolean existePaciente = false;
		ResultSet resultSet;
		
		try {
			statement = connection.prepareStatement(EXISTE_PACIENTE);
			statement.setString(1, dni);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				existePaciente = Boolean.valueOf(resultSet.getString(1));	
			}					
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			conexion.cerrarConexion();
		}
		
		return existePaciente;	
	}

	@Override
	public int getIdPacienteByDNI(String dni) {
		
		Connection connection = null;
	
		PreparedStatement statement;
		ResultSet resultSet = null;		
				
		int idPaciente = -1;
		
		try 
		{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(SELECT_IDPACIENTE_BY_DNI);
			statement.setString(1, dni);
			
			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				String strIdPaciente = resultSet.getString(1);
				if (!resultSet.wasNull()) {
					idPaciente = Integer.parseInt(strIdPaciente);
					return idPaciente;
				}
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conexion.cerrarConexion();
		}
		return idPaciente;
	}

	@Override
	public Paciente mostrarPaciente(String dni) {
		Connection connection = null;
		
		Paciente paciente = new Paciente();
		PreparedStatement statement;
		ResultSet resultSet; 
		
		try
		 {
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(READ_PACIENTE);
			statement.setString(1, dni);
			resultSet = statement.executeQuery();
			resultSet.next();
			paciente = getPaciente2(resultSet);			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}finally {
			conexion.cerrarConexion();
		}
		return paciente;			
	}
	
	private Paciente getPaciente2(ResultSet resultSet) throws SQLException {
		Paciente paciente = new Paciente();
		Nacionalidad nacionalidad = new Nacionalidad();
		Localidad localidad = new Localidad();
		Provincia provincia = new Provincia();
		
		paciente.setDni(resultSet.getString("DNI"));
		paciente.setIdPaciente(resultSet.getInt("idPaciente"));

		paciente.setNombre(resultSet.getString("Nombre"));
		paciente.setApellido(resultSet.getString("Apellido"));
		paciente.setSexo(resultSet.getString("Sexo").charAt(0));
		//
		nacionalidad.setIdNacionalidad( resultSet.getInt("idNacionalidad"));	
		nacionalidad.setDescripcion( resultSet.getString("DescripcionNac"));
		paciente.setNacionalidad(nacionalidad);
		//
		paciente.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
		paciente.setDireccion(resultSet.getString("Direccion"));
		//
		provincia.setIdProvincia(resultSet.getInt("idProvincia"));
		provincia.setDescripcion(resultSet.getString("DescripcionProvincia"));
		localidad.setProvincia(provincia);
		//
		localidad.setIdLocalidad(resultSet.getInt("idLocalidad"));
		localidad.setDescripcion(resultSet.getString("DescripcionLocalidad"));
		paciente.setLocalidad(localidad);
		//
		paciente.setEmail(resultSet.getString("Email"));
		paciente.setEstado(resultSet.getBoolean("Estado"));
		//
		paciente.setTelefono(resultSet.getString("Telefono"));
		//
		return paciente;
	}

	@Override
	public boolean modificar(Paciente paciente) {
		
		boolean esModificacionExitosa = false;		
		PreparedStatement statement;
		Connection connection = null;
		
		try 
		{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(UPDATE);
			statement.setString(1, paciente.getNombre());
			statement.setString(2, paciente.getApellido());
			statement.setString(3, String.valueOf(paciente.getSexo()));
			statement.setInt(4, paciente.getNacionalidad().getIdNacionalidad());
			statement.setDate(5, (Date) paciente.getFechaNacimiento());
			statement.setString(6, paciente.getDireccion());
			statement.setInt(7, paciente.getLocalidad().getIdLocalidad());
			statement.setString(8, paciente.getEmail());
			statement.setString(9, paciente.getTelefono());			
			statement.setString(10, paciente.getDni());
			if(statement.executeUpdate() > 0)
			{
				connection.setAutoCommit(false);
				connection.commit();
				esModificacionExitosa = true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conexion.cerrarConexion();
		}		
		return esModificacionExitosa;
	}
	
	//	

	@Override
	public boolean delete(String dni) {
		boolean isUpdateExitoso = false;		
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		try 
		{
			statement = conexion.prepareStatement(DELETE_PACIENTE_BY_DNI);
			statement.setString(1, dni);
			
			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
				isUpdateExitoso = true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdateExitoso;
	}

	@Override
	public List<Paciente> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<Paciente> pacienteList = new ArrayList<Paciente>();
		
	
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				pacienteList.add(getPaciente(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return pacienteList;
	}

	private Paciente getPaciente(ResultSet resultSet) throws SQLException {
		Paciente pa = new Paciente();
		Nacionalidad na = new Nacionalidad();
		Localidad lo = new Localidad();
		
		pa.setDni(resultSet.getString("DNI"));
		pa.setIdPaciente(resultSet.getInt("idPaciente"));

		pa.setNombre(resultSet.getString("Nombre"));
		pa.setApellido(resultSet.getString("Apellido"));
		pa.setSexo(resultSet.getString("Sexo").charAt(0));
		//
		na.setIdNacionalidad( resultSet.getInt("idNacionalidad"));	
		na.setDescripcion( resultSet.getString("Descripcion"));
		pa.setNacionalidad(na);
		//
		pa.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
		pa.setDireccion(resultSet.getString("Direccion"));
		//
		lo.setIdLocalidad(resultSet.getInt("idLocalidad"));
		pa.setLocalidad(lo);
		//
		pa.setEmail(resultSet.getString("Email"));
		pa.setEstado(resultSet.getBoolean("Estado"));
		//
		pa.setTelefono(resultSet.getString("Telefono"));		
		//
		
		return pa;
	}
	
	
	
	public List<Paciente> readAllBuscar(String nombre){
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<Paciente> listaPaciente = new ArrayList<Paciente>();
		
	
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement("SELECT * FROM Paciente AS pa INNER JOIN Persona AS pe ON pa.DNI = pe.DNI INNER JOIN Nacionalidad as N on pe.idNacionalidad = N.idNacionalidad WHERE  pe.Nombre LIKE "+"'%"+nombre+"%'");
			
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				listaPaciente.add(getPaciente(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return listaPaciente;
	}

	public List<Paciente> paginar() {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<Paciente> pacienteList = new ArrayList<Paciente>();
		
	
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				pacienteList.add(getPaciente(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return pacienteList;
	}	
	
	@Override
	public boolean existePaciente2(String dni) throws PacienteNotFoundException{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean existe = false;
		ResultSet resultSet;
		try {
			statement = conexion.prepareStatement(EXISTE_PACIENTE);
			statement.setString(1, dni);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				existe = Boolean.valueOf(resultSet.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (existe == false) {
			throw new PacienteNotFoundException();
		}
		
		return existe;
	}
}

