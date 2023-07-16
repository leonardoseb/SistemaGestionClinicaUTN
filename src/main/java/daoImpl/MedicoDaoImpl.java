package daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import daoImpl.Conexion;
import dominio.Provincia;
import dominio.Usuario;
import dao.EspecialidadDao;
import dao.LocalidadDao;
import dao.MedicoDao;
import dao.NacionalidadesDao;
import dominio.Especialidad;
import dominio.Localidad;
import dominio.Medico;
import dominio.Medico_x_dia;
import dominio.Nacionalidad;
import dominio.Paciente;
import dominio.Persona;
import dominio.Provincia;
import exception.mediconoencontrado;

public class MedicoDaoImpl implements MedicoDao {

	private Conexion conex;

	Conexion cn = new Conexion();
	
	private static final String readall = "SELECT * FROM Medico AS m INNER JOIN Persona AS p ON p.DNI LIKE m.DNI INNER JOIN Especialidad AS es ON es.idEspecialidad = m.idEspecialidad WHERE m.Estado = 1";
	
	private static final String INSERT = "INSERT INTO Medico(idMedico, DNI, idEspecialidad) VALUES (?, ?, ?)";
	private static final String DELETE = "UPDATE Persona set Estado = 0 where DNI like ?";
	private static final String READ_ALL = "SELECT * FROM Medico as m INNER JOIN Persona as p ON p.DNI LIKE m.DNI INNER JOIN Especialidad AS es ON es.idEspecialidad = m.idEspecialidad WHERE m.Estado = 1";
	private static final String READ_ALL_FILTRO = "SELECT * FROM Medico as m INNER JOIN Persona AS p ON p.DNI like m.DNI INNER JOIN Especialidad AS es ON es.idEspecialidad = m.idEspecialidad WHERE m.idEspecialidad = ? and M.Estado = 1";
	private static final String READ_MEDICO = "SELECT P.DNI, P.Nombre, P.Apellido, P.Sexo, P.idNacionalidad, P.FechaNacimiento, P.Direccion, P.idLocalidad, P.Email, P.Telefono, M.Estado, M.idMedico, M.idEspecialidad, E.Descripcion AS DesEspe, L.Descripcion AS DesLoc, PRO.idProvincia, PRO.Descripcion AS DesPro, N.Descripcion AS DesNac, U.NombreUsuario, U.Clave FROM Medico AS M INNER JOIN Persona AS P ON P.DNI = M.DNI INNER JOIN Especialidad E ON E.idEspecialidad = M.idEspecialidad INNER JOIN Localidad L ON L.idLocalidad = P.idLocalidad INNER JOIN Provincia PRO ON PRO.idProvincia = L.idProvincia INNER JOIN Nacionalidad N ON N.idNacionalidad = P.idNacionalidad INNER JOIN Usuarios U ON U.idUsuario=M.idMedico WHERE IdMedico=?";
	private static final String UPDATE = "UPDATE Persona SET Nombre = ?, Apellido = ?, Sexo = ?, idNacionalidad = ?, FechaNacimiento = ?, Direccion = ?, idLocalidad = ?, Email = ?, Telefono = ? WHERE DNI = ?";
	private static final String TOTAL_PACIENTE = "SELECT COUNT(distinct idPaciente) AS Total FROM Turnos WHERE idMedico = ? AND idEstado=4 AND Fecha BETWEEN ? AND ? ;";
	private static final String EXISTE = "SELECT CASE WHEN EXISTS ( SELECT * FROM Medico WHERE DNI = ?) THEN 'TRUE' ELSE 'FALSE' END";

	@Override
	public int agregarMedico(Medico md, Paciente paciente, String[] Turnos, String Horario) {
		// TODO Auto-generated method stub
		int fila = 0;
		boolean blPersona = false;
		boolean blMedico = false;
		boolean blMedico_x_dia = false;
		boolean blUsuarios = false;

		try {
			cn.Open();
			String queryPersona = "INSERT INTO Persona(DNI, Nombre, Apellido, Sexo, idNacionalidad, FechaNacimiento, Direccion, idLocalidad, Email, Telefono) VALUES('"
					+ paciente.getDni() + "','" + paciente.getNombre() + "','" + paciente.getApellido() + "','"
					+ paciente.getSexo() + "','" + paciente.getNacionalidad().getIdNacionalidad() + "','"
					+ paciente.getFechaNacimiento() + "','" + paciente.getDireccion() + "','"
					+ paciente.getLocalidad().getIdLocalidad() + "','" + paciente.getEmail() + "','"
					+ paciente.getTelefono() + "')";

			String queryMedico = ("INSERT INTO Medico(DNI, idMedico, idEspecialidad, Estado) VALUES('"
					+ paciente.getDni() + "','" + md.getIdMedico() + "','" + md.getIdEspecialidad().getIdEspecialidad()
					+ "',1)");
			String inicialNombre = paciente.getNombre().substring(0, 1).toUpperCase();
			String ApellidoMayus = paciente.getApellido().substring(0, 1).toUpperCase()
					+ paciente.getApellido().substring(1);
			String queryUsuarios = "INSERT INTO Usuarios(NombreUsuario, Clave, Tipo, Estado) values('Medico"
					+ inicialNombre + ApellidoMayus + "','1212','Medico','1')";			

			blPersona = cn.execute(queryPersona);
			if (blPersona) {
				blUsuarios = cn.execute(queryUsuarios);
				blMedico = cn.execute(queryMedico);
				
			}
			
			LocalTime egreso = null;
			LocalTime ingreso = null;
			int index = 0;
			
			if (blUsuarios) {
				// despues de la query del medico
				switch (Horario) {
				case "1": {
					ingreso = Medico_x_dia.HORA_INGRESO; // 9
					egreso = Medico_x_dia.HORA_EGRESO1; // 12
					index = 3;
					break;
				}
				case "2": {
					ingreso = Medico_x_dia.HORA_EGRESO2; // 12
					egreso = Medico_x_dia.HORA_EGRESO3; // 18
					index = 6;
					break;
				}
				case "3": {
					ingreso = Medico_x_dia.HORA_EGRESO1; // 9
					egreso = Medico_x_dia.HORA_EGRESO3; // 18
					index = 9;
					break;
				}
				case "4": {
					ingreso = Medico_x_dia.HORA_INGRESO2; // 12
					egreso = Medico_x_dia.HORA_EGRESO2; // 15
					index = 3;
					break;
				}
				case "5": {
					ingreso = Medico_x_dia.HORA_EGRESO2; // 15
					egreso = Medico_x_dia.HORA_EGRESO3; // 18
					index = 3;
					break;
				}
				default:
					break;
				}
				// despues realizar el insert en medico
				for (String dia : Turnos) {
					String queryMedico_x_turno = "INSERT INTO Medico_x_Dia(idMedico, idDia, HoraIngreso, HoraEgreso, Estado) VALUES('"
							+ md.getIdMedico() + "','" + dia + "','" + ingreso + "','" + egreso + "','" + 1 + "')";
					blMedico_x_dia = cn.execute(queryMedico_x_turno);
				}
			}
			for (int i = 0; i <= index; i++) {
				String queryTurnos = "INSERT INTO Turnos (idMedico, idPaciente, Fecha, idEstado, Hora, Observacion) VALUES ("
						+ md.getIdMedico() + ", " + null + ", DATE_ADD(sysdate(), INTERVAL 10 DAY), '1', '"
						+ ingreso.plusHours(i) + "', " + null + " )";
				System.out.println(queryTurnos);
				cn.execute(queryTurnos);
			}
			if (blPersona && blMedico && blMedico_x_dia && blUsuarios) {
				fila++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.cerrarConexion();
		}
		return fila;
	}

	@Override
	public ArrayList<Medico> obtenerMedicos() {
		conex = new Conexion();
		conex.Open();
		ArrayList<Medico> list = new ArrayList<Medico>();

		try {

			/*
			 * System.out.println("entra al try MedicoDaoImplementacion"); ResultSet rs =
			 * conex.query("SELECT M.idMedico, P.Nombre, P.Apellido " + "FROM Medico M " +
			 * "INNER JOIN Persona P ON M.DNI = P.DNI " + "ORDER BY M.idMedico");
			 */
			ResultSet rs = conex.query("SELECT M.idMedico, E.idEspecialidad, E.Descripcion, P.Nombre, P.Apellido, P.Dni "
					+ "FROM Medico M " + "INNER JOIN Persona P ON M.DNI = P.DNI "
					+ "INNER JOIN Especialidad E ON E.idEspecialidad = M.idEspecialidad WHERE M.estado =1 " + "ORDER BY M.idMedico");
			while (rs.next()) {

				Medico med = new Medico();

				Especialidad especialidad = new Especialidad();
				especialidad.setIdEspecialidad(rs.getInt("idEspecialidad"));
				especialidad.setDescripcion(rs.getString("Descripcion"));

				med.setIdEspecialidad(especialidad);
				med.setIdMedico(rs.getInt("idMedico"));
				med.setNombre(rs.getString("Nombre"));
				med.setApellido(rs.getString("Apellido"));
				med.setDni(rs.getString("DNI"));
				list.add(med);
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
	public List<Medico> obtenerMedicoxDni(int dni) throws mediconoencontrado {
		List<Medico> listaMedicos = new ArrayList<>();
		NacionalidadesDao nacdao = new NacionalidadesDaoImpl();
		LocalidadDao locDao = new LocalidadDaoImpl();
		EspecialidadDao espDao = new EspecialidadDaoImpl();
		boolean existe = false;
		try {
			System.out.println("Medico obtenerMedicoxDni(int dni) TRY LLEGO");
			System.out.println("DNI : " + dni);
			cn.Open();
			String query = "SELECT "
					 + "p.DNI, "
					 + "m.idMedico, "
					 + "p.Nombre, "
					 + "p.Apellido, "
					 + "p.Sexo, "
					 + "p.FechaNacimiento, "
					 + "n.Descripcion, "
					 + "p.Direccion, "
					 + "l.Descripcion, "
					 + "p.Email, "
					 + "e.Descripcion, "
					 + "GROUP_CONCAT(dia.Descripcion) AS dias, "
					 + "p.Telefono "		 
					 + "FROM Medico m "
					 + "INNER JOIN Persona p ON m.DNI = p.DNI "
					 + "INNER JOIN Nacionalidad n ON p.idNacionalidad = n.idNacionalidad " 
					 + "INNER JOIN Localidad l ON l.idLocalidad = p.idLocalidad "
					 + "INNER JOIN Especialidad e ON m.idEspecialidad = e.idEspecialidad " 
					 + "INNER JOIN Medico_x_Dia mxd ON mxd.idMedico = m.idMedico " 
					 + "INNER JOIN Dia dia ON mxd.idDia = dia.idDia " 
					 + "WHERE m.DNI = " + dni;
			System.out.println(query);
			ResultSet rs = cn.query(query);
//			ResultSet rs = cn.query("SELECT p.dni,m.idmedico , p.nombre, p.apellido, p.sexo,p.fechanacimiento, n.Descripcion AS nacionalidad, "
//					+ "p.Direccion, l.Descripcion AS localidad, p.Email, e.Descripcion AS Especialidad, "
//					+ "GROUP_CONCAT(dia.Descripcion SEPARATOR ', ') AS dias " + "FROM Medico m "
//					+ "JOIN Persona p ON m.dni = p.dni "
//					+ "JOIN Nacionalidad n ON p.idNacionalidad = n.idNacionalidad "
//					+ "JOIN Localidad l ON l.idLocalidad = p.idLocalidad "
//					+ "JOIN Especialidad e ON m.idEspecialidad = e.idEspecialidad "
//					+ "JOIN Medico_x_Dia mxd ON mxd.idMedico = m.idMedico "
//					+ "JOIN Dia dia ON mxd.idDia = dia.idDia " + "WHERE m.dni = " + dni);

			while (rs.next()) {
				Medico med = new Medico();
				med.setDni(rs.getString(1));
				med.setIdMedico(rs.getInt(2));
				med.setNombre(rs.getString(3));
				med.setApellido(rs.getString(4));
				med.setSexo(rs.getString(5).charAt(0));
				SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
				Date dateFormateado = new Date();
				try {
					dateFormateado = formato.parse(rs.getString(6));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				java.sql.Date date1 = new java.sql.Date(dateFormateado.getTime());
				med.setFechaNacimiento(date1);
				med.setNacionalidad(new Nacionalidad(nacdao.obtenerNacionalidadxDescripcion(rs.getString(7))));
				med.setDireccion(rs.getString(8));
				int idloca = locDao.obtenerIdLocalidad(rs.getString(9));
				int idProv = locDao.obtenerIdProvincia(idloca, rs.getString(9));
				med.setLocalidad(new Localidad(idloca, new Provincia(idProv, null), rs.getString(9)));
				med.setEmail(rs.getString(10));
				med.setIdEspecialidad(
						new Especialidad(espDao.obtenerIdespecialidad(rs.getString(11)), rs.getString(11)));
				// new Especialidad(espDao.obtenerIdespecialidad(rs.getString(9));
				// Establecer los demás valores del objeto Medico según corresponda
				String dias = rs.getString(12);
				System.out.println(dias);
				med.setTelefono(rs.getString(13));
				listaMedicos.add(med);
				existe = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (existe == false) {
			throw new mediconoencontrado();
		}
		return listaMedicos;
	}

	@Override
	public boolean modificarMedico(Medico medico, int dni) {
		// TODO Auto-generated method stub
		boolean estadoPersona = false;
		boolean estadoMedico = false;
		boolean estado = false;
		Conexion cx = new Conexion();
		cx.Open();
		try {
			String queryPersona = ("UPDATE Persona SET DNI = " + medico.getDni() + ", Nombre = '" + medico.getNombre()
					+ "', Apellido = '" + medico.getApellido() + "', Sexo = '" + medico.getSexo()
					+ "', idNacionalidad = " + medico.getNacionalidad().getIdNacionalidad() + ", FechaNacimiento = '"
					+ medico.getFechaNacimiento() + "', Direccion = '" + medico.getDireccion() + "', idLocalidad = "
					+ medico.getLocalidad().getIdLocalidad() + ", Email = '" + medico.getEmail() + "', Telefono = '"
					+ medico.getTelefono() + "' WHERE DNI = '" + dni + "'");
			String queryMedEspecialidad = ("UPDATE Medico SET DNI = " + medico.getDni() + ",idEspecialidad = "
					+ medico.getIdEspecialidad().getIdEspecialidad() + " WHERE DNI = '" + dni + "'");
			estadoPersona = cx.execute(queryPersona);
			if (estadoPersona) {
				estadoMedico = cx.execute(queryMedEspecialidad);
				if (estadoMedico) {
					estado = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return estado;
	}

	@Override
	public ArrayList<Medico> obtenerMedicosConTurno() {
		System.out.println("Get medics con turno ");
		cn = new Conexion();
		cn.Open();

		ArrayList<Medico> list = new ArrayList<Medico>();
//		try {
//			ResultSet rs = cn.query("Select distinct Medico.*, Persona.*, Especialidad.* from Turnos\r\n"
//					+ "inner join Medico on Medico.idMedico = Turnos.IdMedico\r\n"
//					+ "join Persona on Medico.DNI = Persona.DNI\r\n"
//					+ "join Especialidad on Especialidad.idEspecialidad = Medico.idEspecialidad;");
//
//		
//		ArrayList<Medico> list = new ArrayList<Medico>();
		try {
			ResultSet rs = cn.query("SELECT DISTINCT Medico.*, Persona.*, Especialidad.* FROM Turnos\r\n" + 
					"INNER JOIN Medico on Medico.idMedico = Turnos.IdMedico\r\n" + 
					"JOIN Persona on Medico.DNI = Persona.DNI\r\n" + 
					"JOIN Especialidad on Especialidad.idEspecialidad = Medico.idEspecialidad;");
			
			while (rs.next()) {

				Nacionalidad nac = new Nacionalidad();
				Localidad loc = new Localidad();
				Especialidad espe = new Especialidad(rs.getInt("idEspecialidad"), rs.getString("Descripcion"));

				Medico med = new Medico(rs.getString("DNI"), rs.getString("Nombre"), rs.getString("Apellido"),
						rs.getString("Sexo").charAt(0), nac, rs.getDate("FechaNacimiento"), rs.getString("Direccion"),
						loc, rs.getString("Email"), rs.getString("Telefono"), rs.getInt("idMedico"), espe, true);

				list.add(med);


//				
//				Medico med = new Medico(rs.getString("DNI"), rs.getString("Nombre"), rs.getString("Apellido"),
//						rs.getString("Sexo").charAt(0), nac, rs.getDate("FechaNacimiento"), rs.getString("Direccion"),
//						loc, rs.getString("Email"), rs.getString("telefono"), rs.getInt("idMedico"), 
//						espe, true);
//				
//				list.add(med);
				
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.cerrarConexion();
		}
		return list;
	}

	@Override
	public ArrayList<Medico> obtenerMedicosEspecialidad(int idEsp) {
		System.out.println("Get medics by speciality");
		cn = new Conexion();
		cn.Open();
		ArrayList<Medico> list = new ArrayList<Medico>();
		try {
			ResultSet rs = cn.query("SELECT * FROM medico \r\n" + 
					"					INNER JOIN Persona on Persona.DNI = Medico.DNI\r\n" + 
					"                    WHERE Medico.idEspecialidad = " + idEsp);
			
			while (rs.next()) {
				
				Nacionalidad nac = new Nacionalidad();
				Localidad loc = new Localidad();
				Especialidad espe = new Especialidad(rs.getInt("idEspecialidad"), null);
				
				Medico med = new Medico(rs.getString("DNI"), rs.getString("Nombre"), rs.getString("Apellido"),
						rs.getString("Sexo").charAt(0), nac, rs.getDate("FechaNacimiento"), rs.getString("Direccion"),
						loc, rs.getString("Email"), rs.getString("Telefono"), rs.getInt("idMedico"), 
						espe, true);
				
				list.add(med);			
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			cn.cerrarConexion();
		}

		return list;
	}	

	@Override
	public List<Medico> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<Medico> medicoList = new ArrayList<Medico>();
		Connection connection = null;		
		
		try 
		{
			conex = new Conexion();			
			conex.Open();			
			connection = conex.getSQLConexion();
			
			statement = connection.prepareStatement(READ_ALL);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				medicoList.add(getMedico(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return medicoList;
	}
	
	private Medico getMedico(ResultSet resultSet) throws SQLException
	{
		
		Medico me = new Medico();
		Especialidad es = new Especialidad();
		Nacionalidad na = new Nacionalidad();
		Localidad lo = new Localidad();
		Usuario us = new Usuario();
		me.setDni(resultSet.getString("DNI"));
		us.setIdUsuario(resultSet.getInt("idMedico"));
		me.setIdMedico(us.getIdUsuario());
		//
		es.setIdEspecialidad(resultSet.getInt("idEspecialidad"));	
		es.setDescripcion(resultSet.getString("Descripcion"));
		me.setIdEspecialidad(es);
		//
		me.setNombre(resultSet.getString("Nombre"));
		me.setApellido(resultSet.getString("Apellido"));
		me.setSexo(resultSet.getString("Sexo").charAt(0));
		//
		na.setIdNacionalidad( resultSet.getInt("idNacionalidad"));
		me.setNacionalidad(na);
		//
		me.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
		me.setDireccion(resultSet.getString("Direccion"));
		//
		lo.setIdLocalidad(resultSet.getInt("idLocalidad"));
		me.setLocalidad(lo);
		//
		me.setEmail(resultSet.getString("Email"));
		me.setEstado(resultSet.getBoolean("Estado"));
			
		return me;
	}

	@Override
	public List<Medico> readAllfiltro(int id) {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<Medico> medicoList = new ArrayList<Medico>();
		
		Connection connection = null;
		try 
		{
			conex = new Conexion();			
			conex.Open();			
			connection = conex.getSQLConexion();
			
			statement = connection.prepareStatement(READ_ALL_FILTRO);
			statement.setInt(1,id);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				medicoList.add(getMedico(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return medicoList;
	}

	@Override
	public List<Medico> readAllBuscar(String nombre) {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<Medico> medicoList = new ArrayList<Medico>();
		
		Connection connection = null;
		
		try 
		{
			conex = new Conexion();			
			conex.Open();			
			connection = conex.getSQLConexion();
			
			statement = connection.prepareStatement("SELECT * FROM Medico AS m INNER JOIN Persona AS p ON p.DNI LIKE m.DNI INNER JOIN especialidad AS es ON es.idEspecialidad = m.idEspecialidad WHERE p.Nombre LIKE "+"'%"+ nombre+"%' or p.Apellido LIKE "+"'%"+nombre+"%' or p.DNI LIKE "+"'%"+nombre+"%' ");
			
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				medicoList.add(getMedico(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return medicoList;
	}

	@Override
	public boolean delete(String dni) {
		boolean isUpdateExitoso = false;		
		PreparedStatement statement;
		Connection connection = null;
		try 
		{
			conex = new Conexion();			
			conex.Open();			
			connection = conex.getSQLConexion();
			
			statement = connection.prepareStatement(DELETE);
			statement.setString(1, dni);
			
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
	public Medico mostrarMedico(int idMedico) {
		Connection connection = null;
		Medico me = new Medico();
		PreparedStatement statement;
		ResultSet resultSet; 
		try
		 {
			conex = new Conexion();			
			conex.Open();			
			connection = conex.getSQLConexion();
			
			statement = connection.prepareStatement(READ_MEDICO);
			statement.setInt(1, idMedico);
			resultSet = statement.executeQuery();
			resultSet.next();
			me = getMedico2(resultSet);			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return me;		
	}

	private Medico getMedico2(ResultSet resultSet) throws SQLException{		
		Medico me = new Medico();
		Especialidad es = new Especialidad();
		Nacionalidad na = new Nacionalidad();
		Provincia pro = new Provincia();
		Localidad lo = new Localidad();
		Usuario us = new Usuario();
		me.setDni(resultSet.getString("DNI"));
		us.setIdUsuario(resultSet.getInt("idMedico"));
		us.setClave(resultSet.getString("Clave"));
		us.setNombreUsuario(resultSet.getString("NombreUsuario"));
		me.setIdMedico(us.getIdUsuario());
		//
		es.setIdEspecialidad(resultSet.getInt("idEspecialidad"));
		es.setDescripcion(resultSet.getString("DesEspe"));
		me.setIdEspecialidad(es);
		//
		me.setNombre(resultSet.getString("Nombre"));
		me.setApellido(resultSet.getString("Apellido"));
		me.setSexo(resultSet.getString("Sexo").charAt(0));
		//
		na.setIdNacionalidad( resultSet.getInt("idNacionalidad"));
		na.setDescripcion(resultSet.getString("DesNac"));
		me.setNacionalidad(na);
		//
		me.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
		me.setDireccion(resultSet.getString("Direccion"));
		//
		pro.setIdProvincia(resultSet.getInt("idProvincia"));
		pro.setDescripcion(resultSet.getString("DesPro"));
		lo.setIdLocalidad(resultSet.getInt("idLocalidad"));
		lo.setDescripcion(resultSet.getString("DesLoc"));
		lo.setProvincia(pro);
		
		me.setLocalidad(lo);
		//
		me.setEmail(resultSet.getString("Email"));
		me.setEstado(resultSet.getBoolean("Estado"));
		me.setTelefono(resultSet.getString("Telefono"));
			
		return me;
	}

	@Override
	public boolean update(Medico me) {
		boolean isUpdateExitoso = false;		
		PreparedStatement statement;
		Connection connection = null;
		try 
		{
			conex = new Conexion();			
			conex.Open();			
			connection = conex.getSQLConexion();
			
			statement = connection.prepareStatement(UPDATE);
			statement.setString(1, me.getNombre());
			statement.setString(2, me.getApellido());
			statement.setString(3, String.valueOf(me.getSexo()));
			statement.setInt(4, me.getNacionalidad().getIdNacionalidad());
			statement.setDate(5, (java.sql.Date) me.getFechaNacimiento());
			statement.setString(6, me.getDireccion());
			statement.setInt(7, me.getLocalidad().getIdLocalidad());
			statement.setString(8, me.getEmail());
			statement.setString(9, me.getTelefono());
			statement.setString(10, me.getDni());
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
	public int totalPacientesXMedico(int idMedico, Date fecha1, Date fecha2) {
		Connection connection = null;
		int total = 0;
		PreparedStatement statement;
		ResultSet resultSet; 
		try
		 {
			conex = new Conexion();			
			conex.Open();			
			connection = conex.getSQLConexion();
			
			statement = connection.prepareStatement(TOTAL_PACIENTE);
			statement.setInt(1, idMedico);
			statement.setDate(2, (java.sql.Date) fecha1);
			statement.setDate(3, (java.sql.Date) fecha2);
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
	public boolean existeMedico(String dni) {
		PreparedStatement statement;
		Connection connection = null;
		boolean existe = false;
		ResultSet resultSet;
		try {
			conex = new Conexion();			
			conex.Open();			
			connection = conex.getSQLConexion();
			
			statement = connection.prepareStatement(EXISTE);
			statement.setString(1, dni);
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
	public boolean borrarMedico(String dni) {
		// TODO Auto-generated method stub
		boolean estado = false;
		PreparedStatement statement;

		Connection connection = null;

		try {
			conex = new Conexion();
			conex.Open();
			System.out.println("INICIO borrarMedico");
			String query = "UPDATE Medico SET Estado = 0 WHERE DNI = ?";
			System.out.println(query);
			
			connection = conex.getSQLConexion();
			System.out.println("DNI " + dni);
			statement = connection.prepareStatement(query);
			statement.setString(1, dni);
			System.out.println(statement);
			if (statement.executeUpdate() > 0) {
				System.out.println("INICIO STATEMENT");
				
				connection.setAutoCommit(false);
				connection.commit();
				estado = true;
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			conex.cerrarConexion();
		}
		return estado;
	}

}
