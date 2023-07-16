package daoImpl;

import java.sql.ResultSet;

import dao.DiaDao;

public class DiaDaoImpl implements DiaDao {

	@Override
	public int obtenerNumeroDia(String descripcion) {
		// TODO Auto-generated method stub
		int idDia =0;
		Conexion cn = new Conexion();
		try {
			cn.Open();
			ResultSet rs = cn.query("Select * from dia where UPPER(descripcion) = UPPER('"+descripcion+"')");
			while(rs.next()) {
				idDia = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return idDia;
	}

}
