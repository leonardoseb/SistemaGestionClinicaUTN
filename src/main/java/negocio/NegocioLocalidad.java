package negocio;

import java.util.List;

import dominio.Localidad;

public interface NegocioLocalidad {
	public List<Localidad> listarLocalidades(); 
	public List<Localidad> listarLocalidadesByProvincia(int idProvincia);
}
