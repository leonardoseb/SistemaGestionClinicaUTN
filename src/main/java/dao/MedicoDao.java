package dao;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import dominio.Medico;
import dominio.Medico_x_dia;
import dominio.Paciente;


public interface MedicoDao {
	public int agregarMedico(Medico md, Paciente paciente, String[] turnos,String Horario);
	public ArrayList<Medico> obtenerMedicos();
	public List<Medico> obtenerMedicoxDni(int dni);
	public ArrayList<Medico> obtenerMedicosConTurno();
	public ArrayList<Medico> obtenerMedicosEspecialidad(int idEsp);

	public boolean modificarMedico(Medico medico, int dni);
	public boolean borrarMedico(String dni);
	public boolean delete(String dni);
	public boolean update(Medico me);
	public List<Medico> readAll();
	public List<Medico> readAllfiltro(int id);
	public List<Medico> readAllBuscar(String nombre);
	public Medico mostrarMedico(int idMedico);
	public int totalPacientesXMedico(int idMedico, Date fecha1, Date fecha2);
	public boolean existeMedico(String dni);


}
