package negocioImpl;

import java.util.List;

import dao.NacionalidadesDao;
import daoImpl.NacionalidadesDaoImpl;
import dominio.Nacionalidad;
import negocio.NegocioNacionalidad;

public class NegocioNacionalidadImpl implements NegocioNacionalidad{
	
	NacionalidadesDao nacionalidadesDao = new NacionalidadesDaoImpl();

	@Override
	public List<Nacionalidad> listarNacionalidades() {
		List<Nacionalidad> nacionalidaList = nacionalidadesDao.obtenerNacionalidades();
		return nacionalidaList;
	}

}
