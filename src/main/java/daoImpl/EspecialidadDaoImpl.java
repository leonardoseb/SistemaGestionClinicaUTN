package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.CallableStatement;
import java.sql.Connection;
import dao.EspecialidadDao;
import dominio.Especialidad;

public class EspecialidadDaoImpl implements EspecialidadDao {

	private static final String readall = "select * from Especialidad";
	
	private Conexion conexion; 
	private Conexion conex; 

	public EspecialidadDaoImpl() {

	}

	@Override
	public ArrayList<Especialidad> cargarEspecialidades() {
		conex = new Conexion();
		conex.Open();
		ArrayList<Especialidad> list = new ArrayList<Especialidad>();
		try {
			ResultSet rs = conex.query("select * from Especialidad");
			while (rs.next()) {
				Especialidad esp = new Especialidad();
				esp.setIdEspecialidad(rs.getInt(1));
				esp.setDescripcion(rs.getString("Descripcion"));
				list.add(esp);
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			conex.cerrarConexion();
		}

		return list;

	}

	@Override
	public int obtenerIdespecialidad(String descripcion) {

		int idEsp = 0 ;
		Conexion cn = new Conexion();
		cn.Open();
		try {
			//ResultSet rs = cn.query("select * from especialidad where descripcion ='"+descripcion+"'");

			cn.Open();
			ResultSet rs = cn.query("SELECT * FROM Especialidad WHERE UPPER(Descripcion) = UPPER('"+descripcion+"')");
			while(rs.next()) {
				idEsp = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return idEsp;
	}
	
	@Override
	public List<Especialidad> readAll() {
		
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<Especialidad> especialidadList = new ArrayList<Especialidad>();
			
		Connection connection = null;
		
		conexion = new Conexion();			
		conexion.Open();			
		connection = conexion.getSQLConexion();
		
		try 
		{
			
			statement = connection.prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				especialidadList.add(getEspecialidad(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return especialidadList;
	
	}
	
	private Especialidad getEspecialidad(ResultSet resultSet) throws SQLException
	{
		
		Especialidad es = new Especialidad();
		
		es.setIdEspecialidad(resultSet.getInt("idEspecialidad"));
		es.setDescripcion(resultSet.getString("Descripcion"));
		
		
		return es;
	}

}
