package negocioImpl;

import java.util.List;

import dao.ProvinciaDao;
import daoImpl.ProvinciasDaoImpl;
import dominio.Provincia;
import negocio.NegocioProvincia;

public class NegocioProvinciaImpl implements NegocioProvincia{

	ProvinciaDao provinciasDao = new ProvinciasDaoImpl();
	
	@Override
	public List<Provincia> listarProvincias() {
		List<Provincia> provinciasList = provinciasDao.obtenerProvincias();
		return provinciasList;
	}

}
