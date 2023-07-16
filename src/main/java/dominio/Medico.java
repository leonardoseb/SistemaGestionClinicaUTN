package dominio;

import java.util.Date;

public class Medico extends Persona implements Cloneable{
	private int idMedico;
	private Especialidad idEspecialidad;
	private boolean estado;

	public Medico() {
		super();
	}

	/**
	 * @param dni
	 * @param nombre
	 * @param apellido
	 * @param sexo
	 * @param nacionalidad
	 * @param fechaNacimiento
	 * @param direccion
	 * @param idLocalidad
	 * @param email
	 * @param telefono
	 */

	public Medico(String dni, String nombre, String apellido, char sexo, Nacionalidad nacionalidad,
			Date fechaNacimiento, String direccion, Localidad localidad, String email, String telefono, int idMedico,
			Especialidad idEspecialidad, boolean estado) {
		super(dni, nombre, apellido, sexo, nacionalidad, fechaNacimiento, direccion, localidad, email, telefono);
		this.idMedico = idMedico;
		this.idEspecialidad = idEspecialidad;
		this.estado = estado;
		// TODO Auto-generated constructor stub
	}

	public int getIdMedico() {
		return idMedico;
	}

	public void setIdMedico(int idMedico) {
		this.idMedico = idMedico;
	}



//	public int getIdEspecialidad() {
//		return idEspecialidad;
//	}
//
//	public void setIdEspecialidad(int idEspecialidad) {
//		this.idEspecialidad = idEspecialidad;
//	}

	public boolean isEstado() {
		return estado;
	}

	public Especialidad getIdEspecialidad() {
		return idEspecialidad;
	}

	public void setIdEspecialidad(Especialidad idEspecialidad) {
		this.idEspecialidad = idEspecialidad;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Medico [idMedico=" + idMedico + ", idEspecialidad=" + idEspecialidad + ", estado=" + estado
				+ ", getIdMedico()=" + getIdMedico() + ", getIdEspecialidad()=" + ", isEstado()="
				+ isEstado() + ", getDni()=" + getDni() + ", getNombre()=" + getNombre() + ", getApellido()="
				+ getApellido() + ", getSexo()=" + getSexo() + ", getNacionalidad()=" + getNacionalidad()
				+ ", getFechaNacimiento()=" + getFechaNacimiento() + ", getDireccion()=" + getDireccion()
				+ ", getLocalidad()=" + getLocalidad() + ", getEmail()=" + getEmail() + ", getTelefono()="
				+ getTelefono() +  ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}


}
