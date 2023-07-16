package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daoImpl.Conexion;
import dominio.Dia;
import dominio.Medico_x_dia;
import dao.MedicoxdiaDao;
import dominio.Medico;

public class MedicoxdiaDaoImpl implements MedicoxdiaDao {
	
	private Conexion conexion;
	
	private static final String READ_ALL = "SELECT mxd.idDia AS idD, mxd.idMedico, mxd.HoraIngreso, mxd.HoraEgreso, d.Descripcion FROM Medico_x_Dia AS mxd INNER JOIN Dia AS d ON mxd.idDia = d.idDia WHERE mxd.idMedico = ? and mxd.Estado = 1";
	private static final String INSERT = "INSERT INTO Medico_x_Dia (idDia, idMedico, HoraIngreso, HoraEgreso, Estado) VALUES (?,?,?,?,?)";
	private static final String READ_DIAS = "SELECT * FROM Medico_x_Dia WHERE idMedico = ? AND Estado=1";
	private static final String EXISTE_HORARIO = "SELECT CASE WHEN EXISTS ( SELECT * FROM Medico_x_Dia WHERE idMedico = ? AND idDia = ? AND Estado = 1) THEN 'TRUE' ELSE 'FALSE' END";
	private static final String MEDICO_HORARIO_NO_DISPONIBLE_BAJA = "SELECT CASE WHEN exists ( SELECT * FROM Medico_x_Dia WHERE idMedico = ? AND idDia = ? AND Estado = 0) THEN 'TRUE' ELSE 'FALSE' END";
	private static final String DELETE = "UPDATE Medico_x_Dia SET Estado = 0 WHERE idMedico = ? AND idDia = ?";
	private static final String DAR_DE_ALTA = "UPDATE Medico_x_Dia SET Estado = 1, HoraIngreso = ?, HoraEgreso = ? WHERE idMedico = ? AND idDia = ?";
	

	@Override
	public Medico_x_dia obtenerHorarioMedico(int idMedico, int weekday) {
		
		Medico_x_dia horario = new Medico_x_dia();
		
		Conexion cn = new Conexion();
		try {
			cn.Open();
			ResultSet rs = cn.query("select * from medico_x_dia\r\n" + 
					"where idMedico ="+ idMedico +" \r\n" + 
					"and idDia =" + weekday );
			while(rs.next()) {
				Medico med = new Medico();
				med.setIdMedico(idMedico);
				horario.setIdMedico(med);
				Dia dia = new Dia();
				dia.setIdDia(rs.getInt("idDia"));	
				dia.setDescripcion(rs.getString("Descripcion"));
				horario.setDia(dia);
				horario.setHoraEgreso(rs.getTime("HoraEgreso"));
				horario.setHoraIngreso(rs.getTime("HoraIngreso"));
				/*
				 * horario.setHoraEgreso(rs.getTime("HoraEgreso").toLocalTime());
				 * horario.setHoraIngreso(rs.getTime("HoraIngreso").toLocalTime());
				 */
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return horario;
	}
	
	@Override
	public List<Medico_x_dia> readall(int idMedico) {
		
		PreparedStatement statement;
		ResultSet resultSet; 
		
		ArrayList<Medico_x_dia> horarioList = new ArrayList<Medico_x_dia>();
	
		Connection connection = null;
		try 
		{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(READ_ALL);
			statement.setInt(1, idMedico);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				Medico_x_dia hora = new Medico_x_dia();
				Dia dia = new Dia();
				dia.setIdDia(resultSet.getInt("idDia"));
				dia.setDescripcion(resultSet.getString("Descripcion"));
				hora.setDia(dia);
				hora.setHoraIngreso(resultSet.getTime("HoraIngreso"));		
				hora.setHoraEgreso(resultSet.getTime("HoraEgreso"));				
				horarioList.add(hora);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return horarioList;		
	}

	@Override
	public boolean Insert(Medico_x_dia MedicoxDia) {
	

		PreparedStatement statement;
		Connection connection = null;
		boolean isInsertExitoso = false;
		try
		{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(INSERT);
			statement.setInt(1, MedicoxDia.getDia().getIdDia());
			statement.setInt(2, MedicoxDia.getIdMedico().getIdMedico());
			statement.setTime(3, MedicoxDia.getHoraIngreso());
			statement.setTime(4, MedicoxDia.getHoraEgreso());
			statement.setBoolean(5, true);
			
			
			if(statement.executeUpdate() > 0)
			{
				connection.setAutoCommit(false);
				connection.commit();
				isInsertExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return isInsertExitoso;
		
		
	}

	@Override
	public boolean delete(Medico_x_dia MedicoxDia) {
		
		PreparedStatement statement ;
		Connection connection = null;
		boolean isInsertExitoso = false;
		try
		{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(DELETE);
			statement.setInt(1, MedicoxDia.getIdMedico().getIdMedico());
			statement.setInt(2, MedicoxDia.getDia().getIdDia());
			
			
			if(statement.executeUpdate() > 0)
			{
				connection.setAutoCommit(false);
				connection.commit();
				isInsertExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return isInsertExitoso;
	}

	@Override
	public ArrayList<Medico_x_dia> readDias(int idMedico) {
		PreparedStatement statement;
		ResultSet resultSet; 
		
		ArrayList<Medico_x_dia> dias = new ArrayList<Medico_x_dia>();
	
		Connection connection = null;
		try 
		{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(READ_DIAS);
			statement.setInt(1, idMedico);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				Medico_x_dia mxd = new Medico_x_dia();
				Dia dia = new Dia();
				dia.setIdDia(resultSet.getInt("idDia"));	
				dia.setDescripcion(resultSet.getString("Descripcion"));
//				dxm.setDia(dia);				
				mxd.setDia(dia);
				mxd.setHoraIngreso(resultSet.getTime("HoraIngreso"));
				mxd.setHoraEgreso(resultSet.getTime("HoraEgreso"));
				
				dias.add(mxd);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return dias;		
	}

	@Override
	public boolean diaTrabajoMedico(int idMedico, int idHorario) {
		PreparedStatement statement;
		Connection connection = null;
		boolean existe = false;
		ResultSet resultSet;
		try {
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(EXISTE_HORARIO);
			statement.setInt(1, idMedico);
			statement.setInt(2, idHorario);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				existe = Boolean.valueOf(resultSet.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}

	@Override
	public boolean darAlta(Medico_x_dia medicoxDia) {
		PreparedStatement statement;
		Connection connection = null;
		boolean isInsertExitoso = false;
		try
		{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			statement = connection.prepareStatement(DAR_DE_ALTA);
		
			statement.setTime(1, medicoxDia.getHoraIngreso());
			statement.setTime(2, medicoxDia.getHoraEgreso());
			statement.setInt(3, medicoxDia.getIdMedico().getIdMedico());
			statement.setInt(4, medicoxDia.getDia().getIdDia());
			
			
			if(statement.executeUpdate() > 0)
			{
				connection.setAutoCommit(false);
				connection.commit();
				isInsertExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return isInsertExitoso;
	}

	@Override
	public boolean estadoBaja(Medico_x_dia medicoxDia) {
		PreparedStatement statement;
		Connection connection = null;
		boolean existe = false;
		ResultSet resultSet;
		try {
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(MEDICO_HORARIO_NO_DISPONIBLE_BAJA);
			statement.setInt(1, medicoxDia.getIdMedico().getIdMedico());
			statement.setInt(2, medicoxDia.getDia().getIdDia());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				existe = Boolean.valueOf(resultSet.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}
}