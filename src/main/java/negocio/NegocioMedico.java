package negocio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dominio.Medico;
import dominio.Medico_x_dia;
import dominio.Paciente;
import dominio.Persona;
import exception.mediconoencontrado;

public interface NegocioMedico {
	public int agregarMedico(Medico md, Paciente paciente, String[] turnos,String Horario);
	public ArrayList<Medico> obtenerMedicos();
	//public Medico obtenerMedicoxDni(int dni) ;
	public List<Medico> obtenerMedicoxDni(int dni) throws mediconoencontrado ;
	public boolean modificarMedico(Medico medico, int dni);

//	public Medico obtenerMedicoxDni(int dni) ;
	public ArrayList<Medico> listarMedicosConTurno();
	public ArrayList<Medico> listarMedicosPorEspecialidad(int idEsp);
	public List<Medico> readAll();
	public List<Medico> readAllFiltro(int id);
	public List<Medico> readAllBuscar(String nombre);
	public Medico mostrarMedico(int idMedico);
	public int totalPacientesXMedico(int idMedico, Date fecha1, Date fecha2);
	public boolean existeMedico(String dni);
	public boolean borrarMedico(String dni);
}
