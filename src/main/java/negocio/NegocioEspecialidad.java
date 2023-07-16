package negocio;

import java.util.ArrayList;
import java.util.List;

import dominio.Especialidad;

public interface NegocioEspecialidad {
	
	public ArrayList<Especialidad> cargarEspecialidades();
	
	public List<Especialidad> readAll();

}
