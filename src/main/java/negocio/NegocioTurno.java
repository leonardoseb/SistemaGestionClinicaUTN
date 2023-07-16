package negocio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import dominio.Turno;

public interface NegocioTurno {
	public List<Turno> listarTurnos();
	public List<Turno> obtenerTurnosMedicoFecha(int idMedico, String fecha);
	public boolean insert (List<Turno> listaTurnos);
	
	//
    public List<Turno> readAll();
	
	public Turno devuelveTurno(int id);
	
	public boolean agendarTurno(String dni, Turno turno);
	
	public boolean existeFechaTurno(int idMedico, Date fecha);
	
	public List<Turno> readPorMedico(int idMedico);

	public boolean update2(int idTurno, int estado, String observacion);
	
	public boolean liberarTurno (int idTurno);
	
	public int totalAusentes(Date fecha1, Date fecha2);
	
	public int totalAtendidosPorMes(int mes, int anio);
	
	public int total(int anio);

	public int totalPresentes(int anio);
	
	public List<Turno> filtroFechaEstado(int idEstado, Date fecha);
	
	public List<Turno> filtroFecha(Date fecha);
	
	public List<Turno> filtroEstado(int idEstado);
	
	public List<Turno> filtroFechaEstado(int idEstado, Date fecha, int idMedico);
	
	public List<Turno> filtroFecha(Date fecha, int idMedico);
	
	public List<Turno> filtroEstado(int idEstado, int idMedico);
	
	public String readAllObservacionesByIdPaciente(int idPaciente);
	
}
