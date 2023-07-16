package daoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
	public static Conexion instancia;
	
	private String host = "jdbc:mysql://localhost:3306/";
	private String user = "root";
	//private String pass = "root";
	private String pass = "admin";
//	private String dbName = "clinicautn??profileSQL=true&useSSL=false";
	private String dbName = "clinicautn";
	
	protected Connection connection;

	public Connection Open() {
		try {

//			System.out.println("inicia primera conexion");

//			Class.forName("com.mysql.jdbc.Driver"); // quitar si no es necesario
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			this.connection = DriverManager.getConnection(host+dbName, user, pass);
			
			// this.connection.setAutoCommit(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.connection;
	}

	public static Conexion getConexion() {
		if (instancia == null) {
			instancia = new Conexion();
		}
		return instancia;
	}

	public Connection getSQLConexion() {
		return this.connection;
	}

	public void cerrarConexion() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		instancia = null;
	}

	public ResultSet query(String query) {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public boolean execute(String query) {
		Statement st;
		boolean save = true;
		try {
			st = connection.createStatement();
			st.executeUpdate(query);
		} catch (SQLException e) {
			save = false;
			e.printStackTrace();
		}
		return save;
	}
}
