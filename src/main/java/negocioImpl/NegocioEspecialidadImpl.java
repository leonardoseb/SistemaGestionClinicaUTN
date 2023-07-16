package negocioImpl;

import java.util.ArrayList;
import java.util.List;

import dao.EspecialidadDao;
import daoImpl.EspecialidadDaoImpl;
import dominio.Especialidad;
import negocio.NegocioEspecialidad;

public class NegocioEspecialidadImpl implements NegocioEspecialidad {
	
	private EspecialidadDao espDao = new EspecialidadDaoImpl(); 

	@Override
	public ArrayList<Especialidad> cargarEspecialidades() {
		return (ArrayList<Especialidad>) espDao.cargarEspecialidades();
	}

	@Override
	public List<Especialidad> readAll() {
		return espDao.readAll();
	}

}
