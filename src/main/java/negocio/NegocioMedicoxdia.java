package negocio;

import java.util.ArrayList;
import java.util.List;

import dominio.Medico_x_dia;

public interface NegocioMedicoxdia {
	public Medico_x_dia obtenerHorarioMedico(int idMedico, int weekday);
	
	//
	public List<Medico_x_dia> readall(int idMedico);
	public boolean insert (Medico_x_dia diaXMedico);
	public ArrayList<Medico_x_dia> readDias(int idMedico);
	public boolean diaTrabajoMedico(int idMedico,int idHorario);
	public boolean darAlta(Medico_x_dia diaXMedico);
	public boolean delete(Medico_x_dia diaXMedico);
	public boolean estadoBaja(Medico_x_dia diaXMedico);
}
