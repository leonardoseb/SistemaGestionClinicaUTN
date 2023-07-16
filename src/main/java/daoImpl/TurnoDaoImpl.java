package daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import daoImpl.Conexion;
import dominio.Usuario;
import dominio.Estado;
import negocio.NegocioPaciente;
import negocioImpl.NegocioPacienteImpl;
import dao.TurnoDao;
import dominio.Especialidad;
import dominio.Medico;
import dominio.Paciente;
import dominio.Turno;

public class TurnoDaoImpl implements TurnoDao {

	private Conexion conexion; 
	public Paciente paciente;
	
	private static final String READ_ALL =   "SELECT T.idTurno, T.idMedico, T.Fecha, T.idPaciente, (SELECT PE.Nombre FROM Paciente PA " 
		    + " INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteNombre, "
		    + " (SELECT PE.Apellido FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteApellido, " 
		    + " (SELECT PE.DNI FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteDNI, "
		    + " T.idEstado, ES.Descripcion, T.Hora, T.Observacion, "
		    + " (SELECT P.Apellido FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoApellido, " 
		    + " (SELECT P.Nombre FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoNombre, "
		    + " (SELECT E.idEspecialidad FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idEspe, " 
		    + " (SELECT E.Descripcion FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idDesEspe "
		    + " FROM Turnos T LEFT JOIN Paciente PA ON T.idPaciente = PA.idPaciente "
		    + " LEFT JOIN Persona P ON P.DNI = PA.DNI "
		    + " LEFT JOIN Estados_Turno ES ON T.idEstado = ES.idEstado "
		    + " WHERE ((select concat(T.Fecha, ' ', T.Hora) AS FechaHora) >= NOW()) AND (SELECT M.Estado FROM Medico AS M WHERE M.idMedico = T.idMedico) = 1 ORDER BY T.Fecha ASC";
		   // + " WHERE (SELECT M.Estado FROM Medico AS M WHERE M.idMedico = T.idMedico) = 1 ORDER BY T.Fecha ASC ";
	/*
	 * +
	 * " WHERE ((select concat(T.fecha, ' ', T.hora) AS FechaHora) >= NOW()) AND (SELECT M.Estado FROM Medico AS M WHERE M.idMedico = T.idMedico) = 1 ORDER BY T.Fecha ASC "
	 * ;
	 */
	private static final String READ_TURNO = "SELECT Es.Descripcion, T.Observacion,T.idTurno,E.idEspecialidad, (SELECT P.Apellido FROM Medico m INNER JOIN Persona AS P ON P.DNI = m.DNI WHERE m.idMedico = T.idMedico) AS ApellidoMedico, (SELECT P.Nombre FROM Medico m INNER JOIN Persona AS P ON P.DNI = m.DNI WHERE m.idMedico = T.idMedico) AS NombreMedico, T.Hora,T.Fecha, E.Descripcion FROM Turnos T INNER JOIN Medico AS M ON M.idMedico = T.idMedico INNER JOIN Persona P ON  P.DNI = M.DNI INNER JOIN Especialidad AS E ON  E.idEspecialidad = M.idEspecialidad INNER JOIN Estados_Turno AS Es ON  Es.idEstado = T.idEstado WHERE T.idTurno = ?";
	private static final String UPDATE2 = "UPDATE Turnos SET Observacion = ?, idEstado = ? WHERE idTurno = ?";
	private static final String UPDATE = "UPDATE Turnos SET idEstado = 2, idPaciente = ? WHERE idTurno = ?";
	private static final String EXISTE_FECHA = "SELECT CASE WHEN EXISTS ( SELECT * FROM Turnos WHERE idMedico = ? AND Fecha = ?) THEN 'TRUE' ELSE 'FALSE' END";
	private static final String READ_BY_MEDICO = "SELECT T.idTurno, T.idMedico, T.Fecha, T.idPaciente, (SELECT PE.Nombre FROM Paciente PA "
	     + " INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteNombre, "
	     + " (SELECT PE.Apellido FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteApellido, "
	     + " (SELECT PE.DNI FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteDNI, "
	     + " T.idEstado, ES.Descripcion, T.Hora, T.Observacion, "
	     + " (SELECT P.Apellido FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoApellido, "
	     + " (SELECT P.Nombre FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoNombre, " 
	     + " (SELECT E.idEspecialidad FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idEspe, "
	     + " (SELECT E.Descripcion FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idDesEspe "
	     + " FROM Turnos T LEFT JOIN Paciente PA ON T.idPaciente = PA.idPaciente "
	     + " LEFT JOIN Persona P ON P.DNI = PA.DNI "
	     + " LEFT JOIN Estados_Turno ES ON T.idEstado = ES.idEstado "
	    // + " WHERE T.idMedico = ? AND T.idEstado != 1 AND (SELECT M.Estado FROM Medico AS M WHERE M.idMedico = T.idMedico) = 1 ORDER BY T.Fecha ASC ";
		 + " WHERE ((SELECT concat(T.fecha, ' ', T.hora) AS FechaHora) >= NOW()) AND T.idMedico = ? AND T.idEstado != 1 AND (SELECT M.Estado FROM Medico AS M WHERE M.idMedico = T.idMedico) = 1 ORDER BY T.Fecha ASC ";
	private static final String LIBERAR_TURNO = "UPDATE Turnos SET idEstado = 1, idPaciente = null WHERE idTurno = ?";
	private static final String TOTAL_AUSENTES = "SELECT COUNT(idPaciente) AS Total FROM Turnos WHERE idEstado=3 AND Fecha between ? AND ? ;";
	private static final String TOTAL_ATENDIDOS = "SELECT Count(idTurno) AS Total FROM Turnos WHERE idEstado=4 AND MONTH(Fecha) = ? AND YEAR(Fecha)= ?";
	private static final String TOTAL_PACIENTES = "SELECT SUM(idpaciente) AS Total FROM Turnos WHERE YEAR(Fecha)= ?";
	private static final String TOTAL_PRESENTES = "SELECT SUM(idpaciente) AS Total FROM Turnos WHERE idTurno = 4 AND YEAR(Fecha)= ?";

	private static final String FILTRA_FECHA =  "SELECT T.idTurno, T.idMedico, T.Fecha, T.idPaciente, (SELECT PE.Nombre FROM Paciente PA"
	    + " INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteNombre,"
	    + " (SELECT PE.Apellido FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteApellido,"
	    + " (SELECT PE.DNI FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteDNI,"
	    + " T.idEstado, ES.Descripcion, T.Hora, T.Observacion,"
	    + " (SELECT P.Apellido FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoApellido,"
	    + " (SELECT P.Nombre FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoNombre, "
	    + " (SELECT E.idEspecialidad FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idEspe,"
	    + " (SELECT E.Descripcion FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idDesEspe"
	    + " FROM Turnos T LEFT JOIN Paciente PA ON T.idPaciente = PA.idPaciente "
	    + " LEFT JOIN Persona P ON P.DNI = PA.DNI "
	    + " LEFT JOIN Estados_Turno ES ON T.idEstado = ES.idEstado "
	    + " WHERE T.Fecha = ? AND (SELECT M.Estado FROM Medico AS M WHERE M.idMedico = T.idMedico) = 1 ORDER BY T.Fecha ASC";
	private static final String FILTRA_ESTADO = "SELECT T.idTurno, T.idMedico, T.Fecha, T.idPaciente, (SELECT PE.Nombre FROM Paciente PA"
	    + " INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteNombre,"
	    + " (SELECT PE.Apellido FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteApellido,"
	    + " (SELECT PE.DNI FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteDNI,"
	    + " T.idEstado, ES.Descripcion, T.Hora, T.Observacion,"
	    + " (SELECT P.Apellido FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoApellido,"
	    + " (SELECT P.Nombre FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoNombre, "
	    + " (SELECT E.idEspecialidad FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idEspe,"
	    + " (SELECT E.Descripcion FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idDesEspe"
	    + " FROM Turnos T LEFT JOIN Paciente PA ON T.idPaciente = PA.idPaciente "
	    + " LEFT JOIN Persona P ON P.DNI = PA.DNI "
	    + " LEFT JOIN Estados_Turno ES ON T.idEstado = ES.idEstado "
	    + " WHERE T.idEstado = ? AND (SELECT M.Estado FROM Medico AS M WHERE M.idMedico = T.idMedico) = 1 ORDER BY T.Fecha ASC";
	private static final String FILTRA_FECHA_ESTADO = "SELECT T.idTurno, T.idMedico, T.Fecha, T.idPaciente, (SELECT PE.Nombre FROM Paciente PA"
	    + " INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteNombre,"
	    + " (SELECT PE.Apellido FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteApellido,"
	    + " (SELECT PE.DNI FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteDNI,"
	    + " T.idEstado, ES.Descripcion, T.Hora, T.Observacion,"
	    + " (SELECT P.Apellido FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoApellido,"
	    + " (SELECT P.Nombre FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoNombre, "
	    + " (SELECT E.idEspecialidad FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idEspe,"
	    + " (SELECT E.Descripcion FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idDesEspe"
	    + " FROM Turnos T LEFT JOIN Paciente PA ON T.idPaciente = PA.idPaciente "
	    + " LEFT JOIN Persona P ON P.DNI = PA.DNI "
	    + " LEFT JOIN Estados_Turno ES ON T.idEstado = ES.idEstado "
	    + " WHERE T.idEstado = ? AND T.Fecha = ? AND (SELECT M.Estado FROM Medico AS M WHERE M.idMedico = T.idMedico) = 1 ORDER BY T.Fecha ASC";

	private static final String FILTRA_FECHA_ESTADO_BY_MEDICO = "SELECT T.idTurno, T.idMedico, T.Fecha, T.idPaciente, (SELECT PE.Nombre FROM Paciente PA"
	    + " INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteNombre,"
	    + " (SELECT PE.Apellido FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteApellido,"
	    + " (SELECT PE.DNI FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteDNI,"
	    + " T.idEstado, ES.Descripcion, T.Hora, T.Observacion,"
	    + " (SELECT P.Apellido FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoApellido,"
	    + " (SELECT P.Nombre FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoNombre, "
	    + " (SELECT E.idEspecialidad FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idEspe,"
	    + " (SELECT E.Descripcion FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idDesEspe"
	    + " FROM Turnos T LEFT JOIN Paciente PA ON T.idPaciente = PA.idPaciente "
	    + " LEFT JOIN Persona P ON P.DNI = PA.DNI "
	    + " LEFT JOIN Estados_Turno ES ON T.idEstado = ES.idEstado "
	    + " WHERE T.idEstado = ? AND T.Fecha = ?  AND T.idMedico = ?  AND (SELECT M.Estado FROM Medico AS M WHERE M.idMedico = T.idMedico) = 1 ORDER BY T.Fecha ASC";

	private static final String FILTRA_FECHA_BY_MEDICO = "SELECT T.idTurno, T.idMedico, T.Fecha, T.idPaciente, (SELECT PE.Nombre FROM Paciente PA"
	    + " INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteNombre,"
	    + " (SELECT PE.Apellido FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteApellido,"
	    + " (SELECT PE.DNI FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteDNI,"
	    + " T.idEstado, ES.Descripcion, T.Hora, T.Observacion,"
	    + " (SELECT P.Apellido FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoApellido,"
	    + " (SELECT P.Nombre FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoNombre, "
	    + " (SELECT E.idEspecialidad FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idEspe,"
	    + " (SELECT E.Descripcion FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idDesEspe"
	    + " FROM Turnos T LEFT JOIN Paciente PA ON T.idPaciente = PA.idPaciente "
	    + " LEFT JOIN Persona P ON P.DNI = PA.DNI "
	    + " LEFT JOIN Estados_Turno ES ON T.idEstado = ES.idEstado "
	    + " WHERE T.Fecha = ? AND T.idEstado != 1  AND T.idMedico = ?  AND (SELECT M.Estado FROM Medico AS M  WHERE M.idMedico = T.idMedico) = 1 ORDER BY T.Fecha ASC";

	private static final String FILTRA_ESTADO_BY_MEDICO = "SELECT T.idTurno, T.idMedico, T.Fecha, T.idPaciente, (SELECT PE.Nombre FROM Paciente PA"
	    + " INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteNombre,"
	    + " (SELECT PE.Apellido FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteApellido,"
	    + " (SELECT PE.DNI FROM Paciente PA INNER JOIN Persona PE ON PE.DNI = PA.DNI WHERE PA.idPaciente = T.idPaciente) AS PacienteDNI,"
	    + " T.idEstado, ES.Descripcion, T.Hora, T.Observacion,"
	    + " (SELECT P.Apellido FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoApellido,"
	    + " (SELECT P.Nombre FROM Medico AS M INNER JOIN Persona P ON P.DNI =M.DNI WHERE M.idMedico = T.idMedico) AS MedicoNombre, "
	    + " (SELECT E.idEspecialidad FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idEspe,"
	    + " (SELECT E.Descripcion FROM Especialidad E INNER JOIN Medico M ON M.idEspecialidad = E.idEspecialidad WHERE M.idMedico = T.idMedico) AS idDesEspe"
	    + " FROM Turnos T LEFT JOIN Paciente PA ON T.idPaciente = PA.idPaciente "
	    + " LEFT JOIN Persona P ON P.DNI = PA.DNI "
	    + " LEFT JOIN Estados_Turno ES ON T.idEstado = ES.idEstado "
	    + " WHERE T.idEstado = ?  AND T.idMedico = ?  AND (SELECT M.Estado FROM Medico AS M  WHERE M.idMedico = T.idMedico) = 1 ORDER BY T.Fecha ASC";
	private static final String INSERT = "INSERT INTO Turnos(idMedico, Fecha, idEstado, Hora) VALUES(?, ?, 1, ?)";
	
	private static final String READ_ALL_OBSERVACIONES_BY_IDPACIENTE = "SELECT pa.idPaciente, GROUP_CONCAT(t.Observacion) AS Observaciones FROM Paciente pa INNER JOIN Turnos t ON t.idPaciente = pa.idPaciente WHERE pa.idPaciente = ? GROUP BY pa.idPaciente";
		
	@Override
	public List<Turno> obtenerTurnos() {
		conexion = new Conexion();
		conexion.Open();
		List <Turno> list = new ArrayList<Turno>();
		try 
		{
			ResultSet rs = conexion.query("SELECT distinct tr.idTurno, tr.idEstado, tr.fecha, tr.hora, tr.idpaciente, per.dni, per.nombre, per.Apellido, m.idMedico medico, tr.Observacion, m.idEspecialidad\r\n" + 
					"					FROM Turnos tr, Paciente p, Persona per, Medico m, Especialidad es\r\n" + 
					"					WHERE p.idPaciente = tr.idPaciente\r\n" + 
					"					AND per.dni = p.DNI \r\n" + 
					"					AND m.idMedico = tr.idMedico");

			while (rs.next())
			{
				Turno turno = new Turno();
				turno.setIdTurno(rs.getInt("idTurno"));
				turno.setObservacion(rs.getString("Observacion"));
				Estado estado = new Estado();
				estado.setIdEstado(rs.getInt("idEstado"));
				estado.setDescripcion(rs.getString("Descripcion"));
				turno.setEstado(estado);
				turno.setFecha(rs.getDate("fecha"));
				turno.setHora(rs.getTime("hora"));
				
				Paciente paciente = new Paciente();
				paciente.setIdPaciente(rs.getInt("idpaciente"));
				paciente.setApellido(rs.getString("Apellido"));
				paciente.setNombre(rs.getString("nombre"));
				
				turno.setPaciente(paciente);

				Especialidad especialidad = new Especialidad();
				especialidad.setIdEspecialidad(rs.getInt("idEspecialidad"));

				Medico medico = new Medico();
				medico.setIdMedico(rs.getInt("medico"));
				medico.setIdEspecialidad(especialidad);

				turno.setMedico(medico);

				list.add(turno);
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
	public boolean insertarTurno(Turno turno) {
		boolean wasInserted = true;
		
		try{
		conexion = new Conexion();
		conexion.Open();				
		
		String query = "INSERT INTO Turnos(idMedico, idPaciente, Fecha, idEstado, Hora, Observacion) VALUES('\"+turno.getMedico().getIdMedico()+\"','\"+turno.getPaciente().getIdPaciente()+\"','\"+turno.getFecha()+\"','\"+turno.getIdEstado()+\"','\"+turno.getHora()+\"','\"+turno.getObservacion()+\"')\";";
		
		wasInserted=conexion.execute(query);
		
		 }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			conexion.cerrarConexion();
		}
		return wasInserted;
	}

	@Override
	public boolean borrarTurno(int id) {
		boolean estado=true;
		conexion = new Conexion();
		conexion.Open();		 
		String query = "UPDATE paciente SET estado=0 WHERE idPaciente="+id;
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
	public List<Turno> obtenerTurnosMedicoFecha(int idMedico, String fecha) {
		
		conexion = new Conexion();
		conexion.Open();
		List <Turno> list = new ArrayList<Turno>();
		try 
		{
			ResultSet rs = conexion.query("SELECT distinct tr.idTurno, tr.idEstado, tr.fecha, tr.hora, tr.idpaciente, per.dni, per.nombre, per.Apellido, m.idMedico medico, tr.Observacion, m.idEspecialidad\r\n" + 
					"					FROM Turnos tr, Paciente p, Persona per, Medico m, Especialidad es \r\n" + 
					"					WHERE p.idPaciente = tr.idPaciente\r\n" + 
					"					AND per.dni = p.DNI\r\n" + 
					"					AND m.idMedico = tr.idMedico\r\n" + 
					"                   AND tr.fecha = '"+fecha+"'\r\n" + 
					"                   AND m.idMedico = "+ idMedico);

			while (rs.next())
			{
				Turno turno = new Turno();

				turno.setIdTurno(rs.getInt("idTurno"));
				turno.setObservacion(rs.getString("Observacion"));
				Estado estado = new Estado();
				estado.setIdEstado(rs.getInt("idEstado"));
				estado.setDescripcion(rs.getString("Descripcion"));
				turno.setEstado(estado);
				turno.setFecha(rs.getDate("fecha"));
				turno.setHora(rs.getTime("hora"));

				Paciente paciente = new Paciente();
				paciente.setIdPaciente(rs.getInt("idpaciente"));
				paciente.setApellido(rs.getString("Apellido"));
				paciente.setNombre(rs.getString("nombre"));

				turno.setPaciente(paciente);

				Especialidad especialidad = new Especialidad();
				especialidad.setIdEspecialidad(rs.getInt("idEspecialidad"));

				Medico medico = new Medico();
				medico.setIdMedico(rs.getInt("medico"));
				medico.setIdEspecialidad(especialidad);

				turno.setMedico(medico);


				list.add(turno);
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
	public List<Turno> readAll() {
		
		ArrayList<Turno> listaTurno = new ArrayList();
		PreparedStatement statement;
		Connection connection = null;
		ResultSet resultSet;
		
		try{
			
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(READ_ALL);
			System.out.println("READ_ALL");
			System.out.println(READ_ALL);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()){
				
				Turno turno = new Turno();
				Medico medico = new Medico();
				Usuario usuario = new Usuario();
				Paciente paciente = new Paciente();
				Estado estado = new Estado();
				Especialidad especialidad = new Especialidad();
				
				turno.setIdTurno(resultSet.getInt("idTurno"));
				usuario.setIdUsuario(resultSet.getInt("idMedico"));
				especialidad.setDescripcion(resultSet.getString("idDesEspe"));
				especialidad.setIdEspecialidad(resultSet.getInt("idEspe"));
				medico.setIdMedico(usuario.getIdUsuario());
				medico.setIdEspecialidad(especialidad);
				medico.setApellido(resultSet.getString("MedicoNombre"));
				medico.setNombre(resultSet.getString("MedicoApellido"));
				turno.setMedico(medico);
				paciente.setIdPaciente(resultSet.getInt("idPaciente"));
				paciente.setDni(resultSet.getString("PacienteDNI"));
				paciente.setApellido(resultSet.getString("PacienteApellido"));
				paciente.setNombre(resultSet.getString("PacienteNombre"));
				turno.setPaciente(paciente);
				turno.setFecha(resultSet.getDate("Fecha"));
				estado.setIdEstado(resultSet.getInt("idEstado"));
				estado.setDescripcion(resultSet.getString("Descripcion"));
				turno.setEstado(estado);
				turno.setHora(resultSet.getTime("Hora"));
				turno.setObservacion(resultSet.getString("Observacion"));
				
				listaTurno.add(turno);
			}
			
			
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return listaTurno;
	}

	@Override
	public Turno devuelveTurno(int id) {

		Turno turno = new Turno();
		Medico medico = new Medico();
		Especialidad especialidad = new Especialidad();
		Estado estado = new Estado();
		PreparedStatement statement;
		ResultSet resultSet; 
		
		Connection connection = null;
		
		try
		 {
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(READ_TURNO);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			resultSet.next();
			turno.setIdTurno(id);
			turno.setFecha(resultSet.getDate("Fecha"));
			turno.setHora(resultSet.getTime("Hora"));
			turno.setObservacion(resultSet.getString("Observacion"));
			medico.setApellido(resultSet.getString("ApellidoMedico"));
			medico.setNombre(resultSet.getString("NombreMedico"));
			especialidad.setIdEspecialidad(resultSet.getInt("idEspecialidad"));
			especialidad.setDescripcion(resultSet.getString("Descripcion"));
			estado.setDescripcion(resultSet.getString("Descripcion"));
			turno.setEstado(estado);
			medico.setIdEspecialidad(especialidad);
			turno.setMedico(medico);
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return turno;
	}

	@Override
	public boolean agendarTurno(String dni, Turno turno) {
		boolean isUpdateExitoso = false;		
		PreparedStatement statement;
		Paciente paciente = new Paciente();
		NegocioPaciente negocioPaciente = new NegocioPacienteImpl();
		Connection connection = null;
		try 
		{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(UPDATE);
			paciente = negocioPaciente.mostrarPaciente(dni);
			statement.setInt(1, paciente.getIdPaciente());
			statement.setInt(2, turno.getIdTurno());

			if(statement.executeUpdate() > 0)
			{
				connection.setAutoCommit(false);
				connection.commit();
				isUpdateExitoso = true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdateExitoso;
	}

	@Override
	public boolean existeFechaTurno(int idMedico, Date fecha) {
		PreparedStatement statement;
		Connection connection = null;
		boolean existe = false;
		ResultSet resultSet;
		try {
			
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(EXISTE_FECHA);
			statement.setInt(1, idMedico);
			statement.setDate(2, fecha);
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
	public List<Turno> readPorMedico(int idMedico) {
		List<Turno> listaTurno = new ArrayList<Turno>();
		PreparedStatement statement;
		Connection connection = null;
		ResultSet resultSet;
		
		try{
			
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(READ_BY_MEDICO);
			statement.setInt(1, idMedico);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()){
				
				Turno turno = new Turno();
				Medico medico = new Medico();
				Usuario usuario = new Usuario();
				Paciente paciente = new Paciente();
				Estado estado = new Estado();
				Especialidad espe = new Especialidad();
				
				turno.setIdTurno(resultSet.getInt("idTurno"));
				usuario.setIdUsuario(resultSet.getInt("idMedico"));
				espe.setDescripcion(resultSet.getString("idDesEspe"));
				espe.setIdEspecialidad(resultSet.getInt("idEspe"));
				medico.setIdMedico(usuario.getIdUsuario());
				medico.setIdEspecialidad(espe);
				medico.setApellido(resultSet.getString("MedicoNombre"));
				medico.setNombre(resultSet.getString("MedicoApellido"));
				turno.setMedico(medico);
				paciente.setIdPaciente(resultSet.getInt("idPaciente"));
				paciente.setDni(resultSet.getString("PacienteDNI"));
				paciente.setApellido(resultSet.getString("PacienteApellido"));
				paciente.setNombre(resultSet.getString("PacienteNombre"));
				turno.setPaciente(paciente);
				turno.setFecha(resultSet.getDate("Fecha"));
				estado.setIdEstado(resultSet.getInt("idEstado"));
				estado.setDescripcion(resultSet.getString("Descripcion"));
				turno.setEstado(estado);
				turno.setHora(resultSet.getTime("Hora"));
				turno.setObservacion(resultSet.getString("Observacion"));
				
				listaTurno.add(turno);
			}						
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return listaTurno;
	}

	@Override
	public boolean update2(int idTurno, int estado, String observacion) {
		boolean isUpdateExitoso = false;		
		PreparedStatement statement;
		Connection connection = null;
		try 
		{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(UPDATE2);
			statement.setString(1, observacion);
			statement.setInt(2, estado);
			statement.setInt(3, idTurno);

			if(statement.executeUpdate() > 0)
			{
				connection.setAutoCommit(false);
				connection.commit();
				isUpdateExitoso = true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdateExitoso;
	}

	@Override
	public boolean liberarTurno(int idTurno) {
		boolean isUpdateExitoso = false;		
		PreparedStatement statement;
		Connection connection = null;
		try 
		{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			statement = connection.prepareStatement(LIBERAR_TURNO);
			statement.setInt(1, idTurno);

			if(statement.executeUpdate() > 0)
			{
				connection.setAutoCommit(false);
				connection.commit();
				isUpdateExitoso = true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdateExitoso;
	}

	@Override
	public int totalAusentes(Date fecha1, Date fecha2) {
		Connection connection = null;
		int total = 0;
		PreparedStatement statement;
		ResultSet resultSet; 
		try
		 {
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(TOTAL_AUSENTES);
			statement.setDate(1, fecha1);
			statement.setDate(2, fecha2);
			resultSet = statement.executeQuery();
			resultSet.next();
			total = resultSet.getInt("Total");		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return total;	
	}

	@Override
	public int totalAtendidosPorMes(int mes, int anio) {
		Connection connection = null;
		int total = 0;
		PreparedStatement statement;
		ResultSet resultSet; 
		try
		 {
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(TOTAL_ATENDIDOS);
			statement.setInt(1, mes);
			statement.setInt(2, anio);
			resultSet = statement.executeQuery();
			resultSet.next();
			total = resultSet.getInt("Total");		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return total;	
	}

	@Override
	public int total(int anio) {
		Connection connection = null;
		int tot = 0;
		PreparedStatement statement;
		ResultSet resultSet; 
		try
		 {
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(TOTAL_PACIENTES);
			statement.setInt(1, anio);

			resultSet = statement.executeQuery();
			resultSet.next();
			tot = resultSet.getInt("Total");		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return tot;
	}

	@Override
	public int totalPresentes(int anio) {
		Connection connection = null;
		int tot = 0;
		PreparedStatement statement;
		ResultSet resultSet; 
		try
		 {
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(TOTAL_PRESENTES);
			statement.setInt(1, anio);

			resultSet = statement.executeQuery();
			resultSet.next();
			tot = resultSet.getInt("Total");		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return tot;
	}

	@Override
	public List<Turno> filtroFechaEstado(int idEstado, Date fecha) {
		List<Turno> listaTurno = new ArrayList<Turno>();
		PreparedStatement statement;
		Connection connection = null;
		ResultSet resultSet;
		
		try{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(FILTRA_FECHA_ESTADO);
			statement.setInt(1, idEstado);
			statement.setDate(2, fecha);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()){
				
				Turno turno = new Turno();
				Medico medico = new Medico();
				Usuario usuario = new Usuario();
				Paciente paciente = new Paciente();
				Estado estado = new Estado();
				Especialidad especialidad = new Especialidad();
				
				turno.setIdTurno(resultSet.getInt("idTurno"));
				usuario.setIdUsuario(resultSet.getInt("idMedico"));
				especialidad.setDescripcion(resultSet.getString("idDesEspe"));
				especialidad.setIdEspecialidad(resultSet.getInt("idEspe"));
				medico.setIdMedico(usuario.getIdUsuario());
				medico.setIdEspecialidad(especialidad);
				medico.setApellido(resultSet.getString("MedicoNombre"));
				medico.setNombre(resultSet.getString("MedicoApellido"));
				turno.setMedico(medico);
				paciente.setIdPaciente(resultSet.getInt("idPaciente"));
				paciente.setDni(resultSet.getString("PacienteDNI"));
				paciente.setApellido(resultSet.getString("PacienteApellido"));
				paciente.setNombre(resultSet.getString("PacienteNombre"));
				turno.setPaciente(paciente);
				turno.setFecha(resultSet.getDate("Fecha"));
				estado.setIdEstado(resultSet.getInt("idEstado"));
				estado.setDescripcion(resultSet.getString("Descripcion"));
				turno.setEstado(estado);
				turno.setHora(resultSet.getTime("Hora"));
				turno.setObservacion(resultSet.getString("Observacion"));
				
				listaTurno.add(turno);
			}
			
			
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return listaTurno;
	}

	@Override
	public List<Turno> filtroFecha(Date fecha) {
		List<Turno> listaTurno = new ArrayList<Turno>();
		PreparedStatement statement;
		Connection connection = null;
		ResultSet resultSet;
		
		try{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(FILTRA_FECHA);
			statement.setDate(1, fecha);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()){
				
				Turno turno = new Turno();
				Medico medico = new Medico();
				Usuario usuario = new Usuario();
				Paciente paciente = new Paciente();
				Estado estado = new Estado();
				Especialidad especialidad = new Especialidad();
				
				turno.setIdTurno(resultSet.getInt("idTurno"));
				usuario.setIdUsuario(resultSet.getInt("idMedico"));
				especialidad.setDescripcion(resultSet.getString("idDesEspe"));
				especialidad.setIdEspecialidad(resultSet.getInt("idEspe"));
				medico.setIdMedico(usuario.getIdUsuario());
				medico.setIdEspecialidad(especialidad);
				medico.setApellido(resultSet.getString("MedicoNombre"));
				medico.setNombre(resultSet.getString("MedicoApellido"));
				turno.setMedico(medico);
				paciente.setIdPaciente(resultSet.getInt("idPaciente"));
				paciente.setDni(resultSet.getString("PacienteDNI"));
				paciente.setApellido(resultSet.getString("PacienteApellido"));
				paciente.setNombre(resultSet.getString("PacienteNombre"));
				turno.setPaciente(paciente);
				turno.setFecha(resultSet.getDate("Fecha"));
				estado.setIdEstado(resultSet.getInt("idEstado"));
				estado.setDescripcion(resultSet.getString("Descripcion"));
				turno.setEstado(estado);
				turno.setHora(resultSet.getTime("Hora"));
				turno.setObservacion(resultSet.getString("Observacion"));
				
				listaTurno.add(turno);
			}
			
			
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return listaTurno;
	}

	@Override
	public List<Turno> filtroEstado(int idEstado) {
		List<Turno> listaTurno = new ArrayList<Turno>();
		PreparedStatement statement;
		Connection connection = null;
		ResultSet resultSet;
		
		try{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(FILTRA_ESTADO);
			statement.setInt(1, idEstado);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()){
				
				Turno turno = new Turno();
				Medico medico = new Medico();
				Usuario usuario = new Usuario();
				Paciente paciente = new Paciente();
				Estado estado = new Estado();
				Especialidad especialidad = new Especialidad();
				
				turno.setIdTurno(resultSet.getInt("idTurno"));
				usuario.setIdUsuario(resultSet.getInt("idMedico"));
				especialidad.setDescripcion(resultSet.getString("idDesEspe"));
				especialidad.setIdEspecialidad(resultSet.getInt("idEspe"));
				medico.setIdMedico(usuario.getIdUsuario());
				medico.setIdEspecialidad(especialidad);
				medico.setApellido(resultSet.getString("MedicoNombre"));
				medico.setNombre(resultSet.getString("MedicoApellido"));
				turno.setMedico(medico);
				paciente.setIdPaciente(resultSet.getInt("idPaciente"));
				paciente.setDni(resultSet.getString("PacienteDNI"));
				paciente.setApellido(resultSet.getString("PacienteApellido"));
				paciente.setNombre(resultSet.getString("PacienteNombre"));
				turno.setPaciente(paciente);
				turno.setFecha(resultSet.getDate("Fecha"));
				estado.setIdEstado(resultSet.getInt("idEstado"));
				estado.setDescripcion(resultSet.getString("Descripcion"));
				turno.setEstado(estado);
				turno.setHora(resultSet.getTime("Hora"));
				turno.setObservacion(resultSet.getString("Observacion"));
				
				listaTurno.add(turno);
			}
			
			
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return listaTurno;
	}

	@Override
	public List<Turno> filtroFechaEstado(int idEstado, Date fecha, int idMedico) {
		List<Turno> listaTurno = new ArrayList<Turno>();
		PreparedStatement statement;
		Connection connection = null;
		ResultSet resultSet;
		
		try{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(FILTRA_FECHA_ESTADO_BY_MEDICO);
			statement.setInt(1, idEstado);
			statement.setDate(2, fecha);
			statement.setInt(3, idMedico);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()){
				
				Turno turno = new Turno();
				Medico medico = new Medico();
				Usuario usuario = new Usuario();
				Paciente paciente = new Paciente();
				Estado estado = new Estado();
				Especialidad especialidad = new Especialidad();
				
				turno.setIdTurno(resultSet.getInt("idTurno"));
				usuario.setIdUsuario(resultSet.getInt("idMedico"));
				especialidad.setDescripcion(resultSet.getString("idDesEspe"));
				especialidad.setIdEspecialidad(resultSet.getInt("idEspe"));
				medico.setIdMedico(usuario.getIdUsuario());
				medico.setIdEspecialidad(especialidad);
				medico.setApellido(resultSet.getString("MedicoNombre"));
				medico.setNombre(resultSet.getString("MedicoApellido"));
				turno.setMedico(medico);
				paciente.setIdPaciente(resultSet.getInt("idPaciente"));
				paciente.setDni(resultSet.getString("PacienteDNI"));
				paciente.setApellido(resultSet.getString("PacienteApellido"));
				paciente.setNombre(resultSet.getString("PacienteNombre"));
				turno.setPaciente(paciente);
				turno.setFecha(resultSet.getDate("Fecha"));
				estado.setIdEstado(resultSet.getInt("idEstado"));
				estado.setDescripcion(resultSet.getString("Descripcion"));
				turno.setEstado(estado);
				turno.setHora(resultSet.getTime("Hora"));
				turno.setObservacion(resultSet.getString("Observacion"));
				
				listaTurno.add(turno);
			}
			
			
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return listaTurno;
	}

	@Override
	public List<Turno> filtroFecha(Date fecha, int idMedico) {
		List<Turno> listaTurno = new ArrayList<Turno>();
		PreparedStatement statement;
		Connection connection = null;
		ResultSet resultSet;
		
		try{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(FILTRA_FECHA_BY_MEDICO);
			statement.setDate(1, fecha);
			statement.setInt(2, idMedico);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()){
				
				Turno turno = new Turno();
				Medico medico = new Medico();
				Usuario usuario = new Usuario();
				Paciente paciente = new Paciente();
				Estado estado = new Estado();
				Especialidad especialidad = new Especialidad();
				
				turno.setIdTurno(resultSet.getInt("idTurno"));
				usuario.setIdUsuario(resultSet.getInt("idMedico"));
				especialidad.setDescripcion(resultSet.getString("idDesEspe"));
				especialidad.setIdEspecialidad(resultSet.getInt("idEspe"));
				medico.setIdMedico(usuario.getIdUsuario());
				medico.setIdEspecialidad(especialidad);
				medico.setApellido(resultSet.getString("MedicoNombre"));
				medico.setNombre(resultSet.getString("MedicoApellido"));
				turno.setMedico(medico);
				paciente.setIdPaciente(resultSet.getInt("idPaciente"));
				paciente.setDni(resultSet.getString("PacienteDNI"));
				paciente.setApellido(resultSet.getString("PacienteApellido"));
				paciente.setNombre(resultSet.getString("PacienteNombre"));
				turno.setPaciente(paciente);
				turno.setFecha(resultSet.getDate("Fecha"));
				estado.setIdEstado(resultSet.getInt("idEstado"));
				estado.setDescripcion(resultSet.getString("Descripcion"));
				turno.setEstado(estado);
				turno.setHora(resultSet.getTime("Hora"));
				turno.setObservacion(resultSet.getString("Observacion"));
				
				listaTurno.add(turno);
			}
			
			
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return listaTurno;
	}

	@Override
	public List<Turno> filtroEstado(int idEstado, int idMedico) {
		List<Turno> listaTurno = new ArrayList<Turno>();
		PreparedStatement statement;
		Connection connection = null;
		ResultSet resultSet;
		
		try{
			
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(FILTRA_ESTADO_BY_MEDICO);
			statement.setInt(1, idEstado);
			statement.setInt(2, idMedico);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()){
				
				Turno turno = new Turno();
				Medico medico = new Medico();
				Usuario usuario = new Usuario();
				Paciente paciente = new Paciente();
				Estado estado = new Estado();
				Especialidad especialidad = new Especialidad();
				
				turno.setIdTurno(resultSet.getInt("idTurno"));
				usuario.setIdUsuario(resultSet.getInt("idMedico"));
				especialidad.setDescripcion(resultSet.getString("idDesEspe"));
				especialidad.setIdEspecialidad(resultSet.getInt("idEspe"));
				medico.setIdMedico(usuario.getIdUsuario());
				medico.setIdEspecialidad(especialidad);
				medico.setApellido(resultSet.getString("MedicoNombre"));
				medico.setNombre(resultSet.getString("MedicoApellido"));
				turno.setMedico(medico);
				paciente.setIdPaciente(resultSet.getInt("idPaciente"));
				paciente.setDni(resultSet.getString("PacienteDNI"));
				paciente.setApellido(resultSet.getString("PacienteApellido"));
				paciente.setNombre(resultSet.getString("PacienteNombre"));
				turno.setPaciente(paciente);
				turno.setFecha(resultSet.getDate("Fecha"));
				estado.setIdEstado(resultSet.getInt("idEstado"));
				estado.setDescripcion(resultSet.getString("Descripcion"));
				turno.setEstado(estado);
				turno.setHora(resultSet.getTime("Hora"));
				turno.setObservacion(resultSet.getString("Observacion"));
				
				listaTurno.add(turno);
			}
			
			
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return listaTurno;
	}

	@Override
	public boolean insert(List<Turno> listaTurnos) {
		
		PreparedStatement statement;
		Connection connection = null;
		boolean isInsertExitoso = false;
		
		try
		{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(INSERT);
			
			for (Turno turno : listaTurnos) {
				statement.setInt(1,turno.getMedico().getIdMedico());
				statement.setDate(2, (Date) turno.getFecha());
				statement.setTime(3, turno.getHora());
				if(statement.executeUpdate() > 0)
				{
					connection.setAutoCommit(false);
					connection.commit();
					isInsertExitoso = true;
				}
				
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return isInsertExitoso;
	}
	
	@Override
	public String readAllObservacionesByIdPaciente(int idPaciente) {
		
		PreparedStatement statement;
		Connection connection = null;
		ResultSet resultSet;
		String observaciones = new String();
		
		try{
			
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(READ_ALL_OBSERVACIONES_BY_IDPACIENTE);			
			statement.setInt(1, idPaciente);
			System.out.println(READ_ALL_OBSERVACIONES_BY_IDPACIENTE);
			resultSet = statement.executeQuery();			
			
			while (resultSet.next()){
								
				observaciones = resultSet.getString("Observaciones");				
			}
			
			
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		return observaciones;
	}
}
