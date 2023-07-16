package negocio;

import java.util.List;

import dominio.Paciente;

public interface NegocioPaciente {
	
	public List<Paciente> listarPacientes();
	public Paciente obtenerPacientePorId(int id);
	public boolean insertar(Paciente paciente);
	public boolean editar(Paciente paciente);
	public boolean borrar(String dni);
	public boolean existePaciente(String dni);	
	public Paciente mostrarPaciente(String dni);
	public boolean modificar(Paciente paciente);

	//
	public boolean delete(String dni);
	public List<Paciente> readAll();
	public List<Paciente> readAllBuscar(String nombre);
	public boolean existePaciente2(String dni);
}
