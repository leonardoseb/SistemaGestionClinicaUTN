package negocioImpl;

import java.util.ArrayList;
import java.util.List;

import dao.PacienteDao;
import daoImpl.PacienteDaoImpl;
import dominio.Paciente;
import negocio.NegocioPaciente;

public class NegocioPacienteImpl implements NegocioPaciente{

	PacienteDao pacienteDao = new PacienteDaoImpl();
	
	@Override
	public List<Paciente> listarPacientes() {
		
		List<Paciente> pacientesList = new ArrayList<>();
		
		try {
			pacientesList = pacienteDao.obtenerPacientes();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
		return pacientesList;
	}

	@Override
	public Paciente obtenerPacientePorId(int id) {
	
		return null;
	}

	@Override
	public boolean insertar(Paciente paciente) {		
		return pacienteDao.insertarPaciente(paciente);
	}

	@Override
	public boolean editar(Paciente paciente) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean borrar(String dni) {
		return pacienteDao.borrarPaciente(dni);
	}

	@Override
	public boolean existePaciente(String dni) {
			pacienteDao.existePaciente(dni);
		return false;
	}

	@Override
	public Paciente mostrarPaciente(String dni) {
		return pacienteDao.mostrarPaciente(dni);
	}

	@Override
	public boolean modificar(Paciente paciente) {		
		return pacienteDao.modificar(paciente);
	}

	@Override
	public boolean delete(String dni) {
		
		return pacienteDao.delete(dni);
	}

	@Override
	public List<Paciente> readAll() {
		return pacienteDao.readAll();
	}

	@Override
	public List<Paciente> readAllBuscar(String nombre) {
		
		return pacienteDao.readAllBuscar(nombre);
	}


	@Override
	public boolean existePaciente2(String dni) {
		// TODO Auto-generated method stub
		return false;
	}

}
