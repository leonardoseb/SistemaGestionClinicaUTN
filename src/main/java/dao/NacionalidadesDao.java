package dao;

import java.util.List;

import dominio.Nacionalidad;

public interface NacionalidadesDao {
	public List<Nacionalidad> obtenerNacionalidades();
	public int obtenerNacionalidadxDescripcion(String descripcion);
}
