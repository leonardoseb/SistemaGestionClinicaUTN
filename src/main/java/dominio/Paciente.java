package dominio;

import java.util.Date;

public class Paciente extends Persona{
	
	private int idPaciente;
	private String dni;
	private boolean estado;

	/**
	 * Paciente extends Persona.
	 */
	public Paciente() {
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
	public Paciente(String dni, String nombre, String apellido, char sexo, Nacionalidad nacionalidad,
			Date fechaNacimiento, String direccion, Localidad idLocalidad, String email, String telefono,
			int idPaciente, boolean estado) {
		
		super(dni, nombre, apellido, sexo, nacionalidad, fechaNacimiento, direccion, idLocalidad, email, telefono);
		
		this.idPaciente = idPaciente;
		this.estado = estado;
	}

	/**
	 * @return the idPaciente
	 */
	public int getIdPaciente() {
		return idPaciente;
	}

	/**
	 * @param idPaciente the idPaciente to set
	 */
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

	/**
	 * @return the dni
	 */
	public String getDni() {
		return super.getDni();
	}

	/**
	 * @param dni the dni to set
	 */
	public void setDni(String dni) {
		super.setDni(dni);
	}

	/**
	 * @return the estado
	 */
	public boolean getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Paciente [idPaciente=" + idPaciente + ", dni=" + dni + ", estado=" + estado + ", getNombre()="
				+ getNombre() + ", getApellido()=" + getApellido() + ", getSexo()=" + getSexo()
				+ ", getNacionalidad()=" + getNacionalidad() + ", getFechaNacimiento()=" + getFechaNacimiento()
				+ ", getDireccion()=" + getDireccion() + ", getLocalidad()=" + getLocalidad() + ", getEmail()="
				+ getEmail() + ", getTelefono()=" + getTelefono() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
