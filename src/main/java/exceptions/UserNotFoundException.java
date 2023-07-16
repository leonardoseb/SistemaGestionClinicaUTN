package exceptions;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {}

	@Override
	public String getMessage() {
		return "Usuario y/o Clave incorrecta";
	}
}
