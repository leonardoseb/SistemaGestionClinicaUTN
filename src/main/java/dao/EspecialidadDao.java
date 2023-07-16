package dao;

import java.util.ArrayList;
import java.util.List;

import dominio.Especialidad;

public interface EspecialidadDao {
	
	public ArrayList<Especialidad> cargarEspecialidades();
	public int obtenerIdespecialidad(String descripcion);
	public List<Especialidad> readAll();
}
