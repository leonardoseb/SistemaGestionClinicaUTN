package daoImpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.NacionalidadesDao;
import dominio.Nacionalidad;

public class NacionalidadesDaoImpl implements NacionalidadesDao {

	private Conexion conexion;

	public NacionalidadesDaoImpl() {
	}

	@Override
	public List<Nacionalidad> obtenerNacionalidades() {

		conexion = new Conexion();
		conexion.Open();
		List<Nacionalidad> list = new ArrayList<>();

		try {
			ResultSet rs = conexion.query("SELECT * FROM Nacionalidad");
			while (rs.next()) {

				Nacionalidad nacionalidad = new Nacionalidad();
				nacionalidad.setIdNacionalidad(rs.getInt("idNacionalidad"));
				nacionalidad.setDescripcion(rs.getString("Descripcion"));
				list.add(nacionalidad);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}

		return list;
	}

	@Override
	public int obtenerNacionalidadxDescripcion(String descripcion) {
		// TODO Auto-generated method stub
		conexion = new Conexion();
		conexion.Open();
		int idNac = 0 ;
		try {
			ResultSet rs = conexion.query(
					"Select idnacionalidad from nacionalidad where UPPER(descripcion)= UPPER('" + descripcion + "')");
			while(rs.next()) {
				System.out.println("NACIONALIDA DDAO IMPL");
				idNac = rs.getInt(1);
				System.out.println(idNac);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idNac;
	}

}
