package negocioImpl;

import java.util.ArrayList;

import dao.ReporteDao;
import daoImpl.ReporteDaoImpl;
import negocio.NegocioReporte;

public class NegocioReporteImpl implements NegocioReporte{
	
	private ReporteDao repDao = new ReporteDaoImpl();

	@Override
	public int obtenerCantPacientesXEspecialidad(String especialidad, String fechaInicio, String fechaFin) {
		return repDao.obtenerCantPacientesXEspecialidad(especialidad, fechaInicio, fechaFin);
	}

	@Override
	public ArrayList<String> obtenerListado(int dia, int mes, int anio) {
		// TODO Auto-generated method stub
		return repDao.obtenerListado(dia,mes,anio);
	}
	@Override
	public int obtenerTurnosXMedicoXfecha(int idMedico, String mes, String anio, int estado) {
		return repDao.obtenerTurnosXMedicoXfecha(idMedico, mes, anio, estado);
	}


}
