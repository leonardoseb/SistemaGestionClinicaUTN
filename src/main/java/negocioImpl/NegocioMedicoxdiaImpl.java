package negocioImpl;

import java.util.ArrayList;
import java.util.List;

import dao.MedicoxdiaDao;
import daoImpl.MedicoxdiaDaoImpl;
import dominio.Medico_x_dia;
import negocio.NegocioMedicoxdia;

public class NegocioMedicoxdiaImpl implements NegocioMedicoxdia {
	
	MedicoxdiaDao mxdDao = new MedicoxdiaDaoImpl();
	
	@Override
	public Medico_x_dia obtenerHorarioMedico(int idMedico, int weekday) {

		Medico_x_dia horario = new Medico_x_dia();
		
		try {
			horario = mxdDao.obtenerHorarioMedico(idMedico, weekday);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return horario;
	}
	
	@Override
	public List<Medico_x_dia> readall(int idMedico) {
		return mxdDao.readall(idMedico);
	}

	@Override
	public boolean insert(Medico_x_dia diaXMedico) {
	
		return mxdDao.Insert(diaXMedico);
	}

	@Override
	public ArrayList<Medico_x_dia> readDias(int idMedico) {
		 
		return mxdDao.readDias(idMedico);
	}

	@Override
	public boolean diaTrabajoMedico(int idMedico, int idHorario) {
		return mxdDao.diaTrabajoMedico(idMedico, idHorario);
	}

	@Override
	public boolean darAlta(Medico_x_dia diaXMedico) {
		return mxdDao.darAlta(diaXMedico);
		
	}

	@Override
	public boolean delete(Medico_x_dia diaXMedico) {
		return mxdDao.delete(diaXMedico);
	}

	@Override
	public boolean estadoBaja(Medico_x_dia diaXMedico) {
		return mxdDao.estadoBaja(diaXMedico);
	}
}
