package daoImpl;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.ProvinciaDao;
import dominio.Nacionalidad;
import dominio.Provincia;

public class ProvinciasDaoImpl implements ProvinciaDao{

	private Conexion conexion;
	
	@Override
	public List<Provincia> obtenerProvincias() {
		
		conexion = new Conexion();
		conexion.Open();
		List<Provincia> list = new ArrayList<>();
		
		try 
		{
			 ResultSet rs= conexion.query("SELECT * FROM Provincia");
			 while(rs.next())
			 {
				 
				 Provincia provincia = new Provincia();
				 provincia.setIdProvincia(rs.getInt("idProvincia"));
				 provincia.setDescripcion(rs.getString("Descripcion"));
				 list.add(provincia);
			 }
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			conexion.cerrarConexion();
		}
		
		return list;
	}

}
