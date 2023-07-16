package negocioImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dao.TurnoDao;
import daoImpl.TurnoDaoImpl;
import dominio.Turno;
import negocio.NegocioTurno;

public class NegocioTurnoImpl implements NegocioTurno {
	
	TurnoDao turnoDao = new TurnoDaoImpl();
	
	@Override
	public List<Turno> listarTurnos() {

		List<Turno> turnosList = new ArrayList<Turno>();
		
		try {
			//turnosList = turnoDao.obtenerTurnos();
			turnosList = turnoDao.readAll();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
		return turnosList;
	}	

	@Override
	public List<Turno> obtenerTurnosMedicoFecha(int idMedico, String fecha) {
		
		List<Turno> turnosList = new ArrayList<Turno>();
		
		try {
			turnosList = turnoDao.obtenerTurnosMedicoFecha(idMedico, fecha);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return turnosList;
	}
	
	////
	
	@Override
	public List<Turno> readAll() {
		return turnoDao.readAll();
	}

	@Override
	public Turno devuelveTurno(int id) {
		
		return turnoDao.devuelveTurno(id);
	}

	@Override
	public boolean agendarTurno(String dni, Turno turno) {

		return turnoDao.agendarTurno(dni, turno);
	}

	@Override
	public boolean existeFechaTurno(int idMedico, Date fecha) {
		return turnoDao.existeFechaTurno(idMedico, fecha);
	}

	@Override
	public List<Turno> readPorMedico(int idMedico) {
		
		return turnoDao.readPorMedico(idMedico);
	}

	@Override
	public boolean update2(int idTurno, int estado, String observacion) {

		return turnoDao.update2(idTurno, estado, observacion);
	}

	@Override
	public boolean liberarTurno(int idTurno) {
		return turnoDao.liberarTurno(idTurno);
	}

	@Override
	public int totalAusentes(Date fecha1, Date fecha2) {
		
		return turnoDao.totalAusentes(fecha1, fecha2);
	}

	@Override
	public int totalAtendidosPorMes(int mes, int anio) {
		return turnoDao.totalAtendidosPorMes(mes, anio);
	}

	@Override
	public int total(int anio) {
		return turnoDao.total(anio);
	}

	@Override
	public int totalPresentes(int anio) {
		
		return turnoDao.totalPresentes(anio);
	}

	@Override
	public List<Turno> filtroFechaEstado(int idEstado, Date fecha) {
		
		return turnoDao.filtroFechaEstado(idEstado, fecha);
	}

	@Override
	public List<Turno> filtroFecha(Date fecha) {

		return turnoDao.filtroFecha(fecha);
	}

	@Override
	public List<Turno> filtroEstado(int idEstado) {
		return turnoDao.filtroEstado(idEstado);
	}

	@Override
	public List<Turno> filtroFechaEstado(int idEstado, Date fecha, int idMedico) {
		
		return turnoDao.filtroFechaEstado(idEstado, fecha, idMedico);
	}

	@Override
	public List<Turno> filtroFecha(Date fecha, int idMedico) {
		return turnoDao.filtroFecha(fecha, idMedico);
	}

	@Override
	public List<Turno> filtroEstado(int idEstado, int idMedico) {
		return turnoDao.filtroEstado(idEstado, idMedico);
	}

	@Override
	public boolean insert(List<Turno> listaTurnos) {		
		return turnoDao.insert(listaTurnos);
	}

	@Override
	public String readAllObservacionesByIdPaciente(int idPaciente) {
		return turnoDao.readAllObservacionesByIdPaciente(idPaciente);
	}
}
