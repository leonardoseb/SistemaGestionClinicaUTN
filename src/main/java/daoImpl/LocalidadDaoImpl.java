package daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.LocalidadDao;
import dominio.Localidad;
import dominio.Provincia;

public class LocalidadDaoImpl implements LocalidadDao {

	private static final String LOCALIDADES_BY_ID_PROVINCIA = "SELECT Lo.idLocalidad, Lo.Descripcion as DescripcionLo, Pro.idProvincia, Pro.Descripcion as DescripcionPro FROM Localidad Lo INNER JOIN Provincia Pro ON Pro.idProvincia = Lo.idProvincia where Lo.idProvincia = ?";

	private PreparedStatement statement;
	private ResultSet resultSet;

	private Conexion conexion;

	public LocalidadDaoImpl() {
	}

	@Override
	public List<Localidad> obtenerLocalidades() {

		conexion = new Conexion();
		conexion.Open();

		List<Localidad> list = new ArrayList<>();

		try {
			ResultSet rs = conexion.query("SELECT * FROM Localidad");
			while (rs.next()) {

				Localidad localidad = new Localidad();
				Provincia provincia = new Provincia();
				localidad.setIdLocalidad(rs.getInt("idLocalidad"));
				localidad.setProvincia(provincia);
				localidad.getProvincia().setIdProvincia(rs.getInt("idProvincia"));
				localidad.setDescripcion(rs.getString("Descripcion"));
				list.add(localidad);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}

		return list;
	}

	@Override
	public List<Localidad> obtenerLocalidadesByProvincia(int idProvincia) {

		conexion = new Conexion();
		conexion.Open();

		List<Localidad> list = new ArrayList<>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(LOCALIDADES_BY_ID_PROVINCIA);
			statement.setInt(1, idProvincia);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Localidad localidad = new Localidad();
				Provincia provincia = new Provincia();

				provincia.setIdProvincia(resultSet.getInt("idProvincia"));
				provincia.setDescripcion(resultSet.getString("DescripcionPro"));
				localidad.setIdLocalidad(resultSet.getInt("idLocalidad"));
				localidad.setDescripcion(resultSet.getString("DescripcionLo"));
				localidad.setProvincia(provincia);

				list.add(localidad);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}

		return list;
	}

	@Override
	public int obtenerIdLocalidad(String descripcion) {
		// TODO Auto-generated method stub
		int idLocalidad = 0;
		Conexion cn = new Conexion();
		try {
			cn.Open();
			String query = "Select * from localidad WHERE descripcion ='" + descripcion + "'";
			ResultSet rs = cn.query(query);
			while (rs.next()) {
				idLocalidad = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idLocalidad;
	}

	@Override
	public int obtenerIdProvincia(int idLocalidad, String descripcion) {
		int idProvincia = 0;
		Conexion cn = new Conexion();
		cn.Open();
		try {
			String query = "Select idProvincia from localidad where idLocalidad =" + idLocalidad
					+ " and descripcion = '" + descripcion + "'";
			ResultSet rs = cn.query(query);
			while (rs.next()) {
				idProvincia = rs.getInt(1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idProvincia;
	}

}
