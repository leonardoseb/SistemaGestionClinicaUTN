package dao;

import java.util.List;

import dominio.Localidad;

public interface LocalidadDao {
	List<Localidad> obtenerLocalidades();
	List<Localidad> obtenerLocalidadesByProvincia(int idProvincia);
	public int obtenerIdLocalidad(String descripcion);
	public int obtenerIdProvincia(int idProvincia , String descripcion);
}
