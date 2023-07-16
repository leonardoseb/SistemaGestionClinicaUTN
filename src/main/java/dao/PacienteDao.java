package dao;

import java.util.List;

import dominio.Paciente;

public interface PacienteDao {
	
	public List<Paciente> obtenerPacientes();
	public Paciente obtenerPacientePorId(int id);
	public boolean insertarPaciente(Paciente paciente);
	public boolean editarPaciente(Paciente paciente);
	public boolean borrarPaciente(String dni);
	public boolean existePaciente(String dni);
	public int getIdPacienteByDNI(String dni);
	public Paciente mostrarPaciente(String dni);
	public boolean modificar(Paciente paciente);

	//
	public boolean delete(String dni);
	public List<Paciente> readAll();
	public List<Paciente> readAllBuscar(String nombre);
	public boolean existePaciente2(String dni);
}
