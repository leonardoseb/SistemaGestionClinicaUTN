package daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.UsuarioDao;
import dominio.Usuario;
import exceptions.UserNotFoundException;

public class UsuarioDaoImpl implements UsuarioDao{
		
	private static final String INIT_USER = "SELECT U.idUsuario, U.NombreUsuario, U.Clave, U.Tipo, U.Estado FROM Usuarios U WHERE U.NombreUsuario = ? AND U.Clave = ? AND U.Estado = 1";
	private static final String EXISTE = "SELECT * FROM Usuarios WHERE NombreUsuario LIKE ?";
	private static final String UPDATE = "UPDATE Usuarios SET Clave = ?, NombreUsuario = ? WHERE idUsuario = ?";
	private static final String INSERT_ADMIN = "INSERT INTO Usuarios(NombreUsuario, Clave, Tipo, Estado) VALUES(?, ?, ?, 1)";
	 /*private static final String update =
	 * "UPDATE Usuarios SET Clave = ?, NombreUsuario = ? where idUsuario = ?";
	 * private static final String delete =
	 * "update Usuarios set Estado =0 where idUsuario like '?'"; private static
	 * final String readall = "SELECT * FROM Usuarios"; private static final String
	 * iniciar =
	 * "SELECT U.idUsuario, U.NombreUsuario, U.clave, U.Tipo FROM Usuarios U WHERE U.NombreUsuario = ? AND U.clave = ? AND U.Estado = 1;"
	 * ; private static final String existe =
	 * "SELECT * FROM Usuarios WHERE NombreUsuario = ?"; private static final String
	 * ultimoId = "SELECT idUsuario FROM Usuarios ORDER BY idUsuario DESC LIMIT 1";
	 * 
	 */	
	
	private Conexion conexion; 
	static ResultSet resultSet = null;
	
	@Override
	/*
	 * public Usuario login(Usuario usuario) {
	 * 
	 * conexion = new Conexion(); conexion.Open(); boolean existing = false;
	 * 
	 * //preparing some objects for connection
	 * 
	 * String username = usuario.getNombreUsuario(); String password =
	 * usuario.getClave();
	 * 
	 * String searchQuery = "SELECT * FROM Usuarios WHERE NombreUsuario='" +
	 * username + "' AND Clave='" + password + "'";
	 * 
	 * try {
	 * 
	 * resultSet = conexion.query(searchQuery); existing = resultSet.next();
	 * System.out.println(existing);
	 * 
	 * // if user does not exist set the isValid variable to false if (!existing) {
	 * System.out.println("You are not a registered user! Please sign up first"); }
	 * 
	 * //if user exists set the isValid variable to true else if (existing) { String
	 * firstName = resultSet.getString("NombreUsuario");
	 * 
	 * System.out.println("Welcome " + firstName);
	 * usuario.setNombreUsuario(firstName);
	 * usuario.setEstado(Integer.parseInt(resultSet.getString("Estado"))); } }
	 * 
	 * catch (Exception ex) {
	 * System.out.println("Log In failed: An Exception has occurred! " + ex); }
	 * finally { conexion.cerrarConexion(); }
	 * 
	 * return usuario;
	 * 
	 * }
	 */
	
	public int getUltimoIdUsuario() {
		// TODO Auto-generated method stub
		conexion = new Conexion();
		conexion.Open();
		int idUltimoUsuario = 0 ;
		String queryObtener = "SELECT MAX(idUsuario) FROM Usuarios";
		try
		{
			ResultSet rs = conexion.query(queryObtener);
			while(rs.next()) {
				idUltimoUsuario = rs.getInt(1);
				System.out.println("idUltimoUsuario: " + idUltimoUsuario);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			conexion.cerrarConexion();
		}
		return idUltimoUsuario;
	}
	

	@Override
	public Usuario initUser(String nombre, String clave) {
		
		Usuario usuario = null;
		PreparedStatement statement;
		Connection connection = null;
		ResultSet resultSet = null;
		
		try {
			
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(INIT_USER);
			statement.setString(1, nombre);
			statement.setString(2, clave);
			resultSet = statement.executeQuery();
			System.out.println("INIT_USER: " + INIT_USER);
			while (resultSet.next()) {
				usuario = new Usuario();
				usuario.setIdUsuario(resultSet.getInt("idUsuario"));
				usuario.setNombreUsuario(resultSet.getString("NombreUsuario"));
				usuario.setClave(resultSet.getString("Clave"));
				usuario.setTipo(resultSet.getString("Tipo"));
				int estado = resultSet.getBoolean("Estado") ? 1 : 0;
				usuario.setEstado(estado);
				System.out.println("Cargo un usuario: " + usuario.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
		
		if (usuario == null) {
			throw new UserNotFoundException();
		}
		
		return usuario;
	}	

	
	@Override
	public boolean update(Usuario usuario) { // Funcionando

		PreparedStatement statement;
		Connection connection = null;

		boolean isUpdateExitoso = false;
		try {
			conexion = new Conexion();
			conexion.Open();
			connection = conexion.getSQLConexion();

			statement = connection.prepareStatement(UPDATE);
			statement.setString(1, usuario.getClave());
			statement.setString(2, usuario.getNombreUsuario());
			statement.setInt(3, usuario.getIdUsuario());

			if (statement.executeUpdate() > 0) {
				connection.setAutoCommit(false);
				connection.commit();
				isUpdateExitoso = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}

		return isUpdateExitoso;
	}

	@Override
	public boolean delete(Usuario usuDelete) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Usuario> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existe(String nombre) {
		PreparedStatement statement;
		Connection connection = null;
		ResultSet rs;	
		boolean estado = false;
		try 
		{
			conexion = new Conexion();			
			conexion.Open();			
			connection = conexion.getSQLConexion();
			
			statement = connection.prepareStatement(EXISTE);
			statement.setString(1, nombre);
			
			rs = statement.executeQuery();
			
			while (rs.next()) {
				connection.setAutoCommit(false);
				connection.commit();
				estado = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}finally {
			conexion.cerrarConexion();
		}
		
		return estado;
	}


	@Override
	public boolean insertAdmin(Usuario usuario) {
		
		boolean wasInserted = true;
		
		PreparedStatement statement;
		Connection connection = null;
		
		conexion = new Conexion();			
		conexion.Open();			
		connection = conexion.getSQLConexion();
			
		try
		{
			statement = connection.prepareStatement(INSERT_ADMIN);
			statement.setString(1,usuario.getNombreUsuario());
			statement.setString(2, usuario.getClave());
			statement.setString(3, usuario.getTipo());
			
			if(statement.executeUpdate() > 0)
			{
				connection.setAutoCommit(false);
				connection.commit();
				wasInserted = true;
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
		}finally
		{
			conexion.cerrarConexion();
		}
		
		return wasInserted;
	}	
}
