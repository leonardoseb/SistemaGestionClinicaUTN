package negocio;

import java.util.ArrayList;

public interface NegocioReporte {
	
	public int obtenerCantPacientesXEspecialidad(String especialidad, String fechaInicio, String fechaFin);

	public ArrayList<String> obtenerListado(int dia, int mes, int anio);
	public int obtenerTurnosXMedicoXfecha(int idMedico, String mes, String anio, int estado);

}
