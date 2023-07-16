package negocioImpl;

import java.util.List;

import dao.LocalidadDao;
import daoImpl.LocalidadDaoImpl;
import dominio.Localidad;
import negocio.NegocioLocalidad;

public class NegocioLocalidadesImpl implements NegocioLocalidad{
	
	LocalidadDao localidadDao = new LocalidadDaoImpl();

	@Override
	public List<Localidad> listarLocalidades() {
		List<Localidad> localidadesList = localidadDao.obtenerLocalidades();
		return localidadesList;
	}

	@Override
	public List<Localidad> listarLocalidadesByProvincia(int idProvincia) {
		List<Localidad> localidadesList = localidadDao.obtenerLocalidadesByProvincia(idProvincia);
		return localidadesList;
	}

}
