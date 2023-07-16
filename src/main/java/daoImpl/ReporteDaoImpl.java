package daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.ReporteDao;

public class ReporteDaoImpl implements ReporteDao{
	
	Conexion cn = new Conexion(); 
	
	public ReporteDaoImpl(){}


	@Override
	public int obtenerCantPacientesXEspecialidad(String especialidad, String fechaInicio, String fechaFin) {
		
		
		System.out.println("llegue a reporteDaoImplementacion antes de abrir conex");
		
		int cantPaciente = 0;	
		
		System.out.println(especialidad);
		
		cn.Open();
		
		try {
			
			System.out.println("llegue a reporteDaoImpl abrio conex");
			
			String query = 	"SELECT COUNT(*) FROM Persona p " +
							"INNER JOIN Paciente pa ON p.DNI = pa.DNI " +
							"INNER JOIN Turnos t ON pa.idPaciente = t.idPaciente " +
							"INNER JOIN Medico m ON t.idMedico = m.idMedico " +
							"INNER JOIN Especialidad e ON m.idEspecialidad = e.idEspecialidad " +
							"WHERE e.descripcion = ? AND t.idEstado != 1 " +
							"AND t.Fecha BETWEEN ? AND ?";
			
			System.out.println("pase la query de ReporteDao");

			PreparedStatement stmt = cn.getSQLConexion().prepareStatement(query);
            stmt.setString(1, especialidad);
            stmt.setString(2, fechaInicio);
            stmt.setString(3, fechaFin);
            
            System.out.println("pase los sets de ReporteDao");

            ResultSet rs = stmt.executeQuery();
			
            if (rs.next()) {
                cantPaciente = rs.getInt(1);
            }

		}
		
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.cerrarConexion();
		}
		
		System.out.println("pase la funcion contar de ReporteDao");
		
		return cantPaciente;
	}
	
	
	@Override
	public ArrayList<String> obtenerListado(int dia, int mes, int anio) {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<>();
		Conexion cn = new Conexion();
		boolean estado = true;
		try {
			cn.Open();
			String query = "SELECT t.Fecha, CONCAT(Persona_Medico.Nombre, ' ', Persona_Medico.Apellido) AS 'Nombre Medico', "
			        + "Especialidad.Descripcion, CONCAT(Persona_Paciente.Nombre, ' ', Persona_Paciente.Apellido) AS 'Nombre Paciente', "
			        + "Persona_Paciente.DNI AS DNIPaciente, Estados_Turno.Descripcion AS EstadoTurno, "
			        + "COUNT(*) AS Cantidad "
			        + "FROM Turnos t "
			        + "JOIN Medico m ON t.idMedico = m.idMedico "
			        + "JOIN Especialidad ON m.idEspecialidad = Especialidad.idEspecialidad "
			        + "JOIN Paciente ON t.idPaciente = Paciente.idPaciente "
			        + "JOIN Persona AS Persona_Medico ON m.DNI = Persona_Medico.DNI "
			        + "JOIN Persona AS Persona_Paciente ON Paciente.DNI = Persona_Paciente.DNI "
			        + "JOIN Estados_Turno ON t.idEstado = Estados_Turno.idEstado "
			        + "WHERE DAY(t.fecha) = " + dia + " AND MONTH(t.fecha) = " + mes + " AND YEAR(t.fecha) = " + anio + " "
			        + "GROUP BY t.Fecha, Persona_Medico.Nombre, Persona_Medico.Apellido, Especialidad.Descripcion, "
			        + "Persona_Paciente.Nombre, Persona_Paciente.Apellido, Persona_Paciente.DNI, Estados_Turno.Descripcion";
	        ResultSet rs = cn.query(query);
			while(rs.next()) {
				System.out.println("LLEGO DAO IMPL");
				System.out.println("1: "+ rs.getDate(1));
				System.out.println("2: "+ rs.getString(2));
				System.out.println("3: "+ rs.getString(3));
				System.out.println("4: "+ rs.getString(4));
				System.out.println("5: "+ rs.getString(5));
				System.out.println("6: "+ rs.getString(6));
				System.out.println("7: "+ rs.getString(7));
		        String item = rs.getDate(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | "
		                + rs.getString(4) + " | " + rs.getString(5) + " | " + rs.getString(6) + " | "
		                + rs.getString(7);
		        estado = true;
				list.add(item);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			cn.cerrarConexion();
		}
		if(list.isEmpty()) {
			System.out.println("RETORNA FALSE");
			return null;
		}else {
			System.out.println("RETORNA TRUE");
			return list;
		}
			
	}
		@Override
	public int obtenerTurnosXMedicoXfecha(int idMedico, String mes, String anio, int estado) {

		System.out.println("llegue a reporteDaoImplementacionReporte2 antes de abrir conex");

		int resultado = 0;

		System.out.println(idMedico);

		cn.Open();

		try {

			System.out.println("llegue a reporteDaoImplReporte2 abrio conex");

			String query = "SELECT YEAR(Fecha) AS Anio, MONTH(Fecha) AS Mes, idEstado, COUNT(*) AS CantidadTurnos "
					+ "FROM Turnos " + "WHERE idMedico = ? " + "  AND YEAR(Fecha) = ? " + "  AND MONTH(Fecha) = ? "
					+ "  AND idEstado = ? " + "GROUP BY Anio, Mes, idEstado " + "ORDER BY Anio, Mes, idEstado";

			System.out.println("pase la query de ReporteDao2");

			PreparedStatement stmt = cn.getSQLConexion().prepareStatement(query);
			stmt.setInt(1, idMedico);
			stmt.setString(2, anio);
			stmt.setString(3, mes);
			stmt.setInt(4, estado);

			System.out.println("pase los sets de ReporteDao2");

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				resultado = rs.getInt("CantidadTurnos");
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.cerrarConexion();
		}

		System.out.println("pase la funcion contar de ReporteDao2");

		return resultado;
	}
	

}
