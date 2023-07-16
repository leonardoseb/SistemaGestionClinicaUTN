package negocioImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dao.MedicoDao;
import daoImpl.MedicoDaoImpl;
import dominio.Medico;
import dominio.Medico_x_dia;
import dominio.Paciente;
import dominio.Persona;
import exception.mediconoencontrado;
import negocio.NegocioMedico;

public class NegocioMedicoImpl implements NegocioMedico{
	MedicoDao mdDao = new MedicoDaoImpl(); 


	@Override
	public int agregarMedico(Medico md, Paciente paciente, String[] turnos,String Horario) {
		// TODO Auto-generated method stub
		int filas = 0;
		try {
			System.out.println("Inicio Negocio Medico Impl agregarMedico" );
			filas = mdDao.agregarMedico(md, paciente, turnos, Horario);
			System.out.println("Fin Negocio Medico Impl agregarMedico" );
		}catch(Exception e) {
			e.printStackTrace();
		}
		return filas;
	}

	@Override
	public ArrayList<Medico> obtenerMedicos() {
		return (ArrayList<Medico>) mdDao.obtenerMedicos();
	}

	@Override
	public List<Medico> obtenerMedicoxDni(int dni) throws mediconoencontrado {
		// TODO Auto-generated method stub
		return (List<Medico>) mdDao.obtenerMedicoxDni(dni);
	}

	@Override
	public boolean modificarMedico(Medico medico, int dni) {
		// TODO Auto-generated method stub
		return mdDao.modificarMedico(medico, dni);
	}

//	@Override
//	public Medico obtenerMedicoxDni(int dni) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
	@Override
	public ArrayList<Medico> listarMedicosConTurno() {

		ArrayList<Medico> medicosList = new ArrayList<Medico>();
		
		try {
			medicosList = mdDao.obtenerMedicosConTurno();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return medicosList;
	}
	
	@Override
	public ArrayList<Medico> listarMedicosPorEspecialidad(int idEsp) {
		ArrayList<Medico> medicosList = new ArrayList<Medico>();
		
		try {
			medicosList = mdDao.obtenerMedicosEspecialidad(idEsp);
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
		return medicosList;
	}

	@Override
	public List<Medico> readAll() {
		return mdDao.readAll();
	}

	@Override
	public List<Medico> readAllFiltro(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Medico> readAllBuscar(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Medico mostrarMedico(int idMedico) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int totalPacientesXMedico(int idMedico, Date fecha1, Date fecha2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean existeMedico(String dni) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean borrarMedico(String dni) {
		// TODO Auto-generated method stub
		return mdDao.borrarMedico(dni);
	}
}
