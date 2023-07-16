package negocio;

import dominio.Usuario;

public interface NegocioUsuario {
	public int getUltimoIdUsuario();
	public boolean insertAdmin(Usuario usuario);
	public boolean update(Usuario usuario);
	public boolean delete(Usuario usuario);
	public Usuario initUser(String user, String clave);
	public boolean existeUsuario(String nombre);
}
