package dominio;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

public class Medico_x_dia {
	public static final LocalTime HORA_INGRESO = LocalTime.of(9, 0);
	public static final LocalTime HORA_INGRESO2 = LocalTime.of(12, 0);
	public static final LocalTime HORA_EGRESO1 = LocalTime.of(12, 0);
	public static final LocalTime HORA_EGRESO2 = LocalTime.of(15, 0);
	public static final LocalTime HORA_EGRESO3 = LocalTime.of(18, 0);

	private Medico idMedico;
//	private int dia;
	private Dia dia;
	/*
	 * private LocalTime horaIngreso; 
	 * private LocalTime horaEgreso;
	 */
	private Time horaIngreso;
	private Time horaEgreso;
	private boolean estado;

	public Medico_x_dia() {

	}

	public Medico_x_dia(Medico idMedico, Dia dia, Date horaIngreso, LocalTime horaEgreso, boolean estado) {
		super();
		this.idMedico = idMedico;
		//this.dia = dia;
		/*
		 * this.setHoraIngreso(HORA_INGRESO); 
		 * this.setHoraEgreso(horaEgreso);
		 */
		this.estado = estado;
	}
		

	/**
	 * @param idMedico
	 * @param dia
	 * @param horaIngreso
	 * @param horaEgreso
	 * @param estado
	 */
	public Medico_x_dia(Medico idMedico, Dia dia, Time horaIngreso, Time horaEgreso, boolean estado) {
		super();
		this.idMedico = idMedico;
		this.dia = dia;
		this.horaIngreso = horaIngreso;
		this.horaEgreso = horaEgreso;
		this.estado = estado;
	}

	public Medico getIdMedico() {
		return idMedico;
	}

	public void setIdMedico(Medico idMedico) {
		this.idMedico = idMedico;
	}

	/*
	 * public int getDia() { return dia; }
	 * 
	 * public void setDia(int dia) { this.dia = dia; }
	 */

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	/**
	 * @return the horaIngreso
	 */
	public Time getHoraIngreso() {
		return horaIngreso;
	}

	/**
	 * @param horaIngreso the horaIngreso to set
	 */
	public void setHoraIngreso(Time horaIngreso) {
		this.horaIngreso = horaIngreso;
	}

	/**
	 * @return the horaEgreso
	 */
	public Time getHoraEgreso() {
		return horaEgreso;
	}

	/**
	 * @param horaEgreso the horaEgreso to set
	 */
	public void setHoraEgreso(Time horaEgreso) {
		this.horaEgreso = horaEgreso;
	}

	/**
	 * @return the dia
	 */
	public Dia getDia() {
		return dia;
	}

	/**
	 * @param dia the dia to set
	 */
	public void setDia(Dia dia) {
		this.dia = dia;
	}
		

	/*
	 * public LocalTime getHoraIngreso() { return horaIngreso; }
	 * 
	 * public void setHoraIngreso(LocalTime horaIngreso) { this.horaIngreso =
	 * horaIngreso; }
	 * 
	 * public LocalTime getHoraEgreso() { return horaEgreso; }
	 * 
	 * public void setHoraEgreso(LocalTime horaEgreso) { this.horaEgreso =
	 * horaEgreso; }
	 */
	
	


}
