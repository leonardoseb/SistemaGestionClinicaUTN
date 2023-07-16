package dominio;

import java.sql.Time;
import java.util.Date;

public class Turno {
	
	private int idTurno;
	private Medico medico;
	private Paciente paciente;
	private Estado estado;
	private Date fecha;
	private Time hora;
	private String observacion;
	
	public Turno() {}	
	
	public int getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}
	public Medico getMedico() {
		return medico;
	}
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}



	/**
	 * @return the estado
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Time getHora() {
		return hora;
	}
	public void setHora(Time hora) {
		this.hora = hora;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@Override
	public String toString() {
		return "Turno [idTurno=" + idTurno + ", medico=" + medico + ", paciente=" + paciente + ", fecha=" + fecha
				+ ", hora=" + hora + ", observacion=" + observacion + "]";
	}	
		
}

