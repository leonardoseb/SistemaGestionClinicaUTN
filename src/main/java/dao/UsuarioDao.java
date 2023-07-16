package dao;

import java.util.List;

import dominio.Usuario;

public interface UsuarioDao {
	
	public boolean insertAdmin(Usuario usuario);
	public int getUltimoIdUsuario();	
	public Usuario initUser(String nombre, String clave);
	public boolean update(Usuario usuario);
	public boolean delete(Usuario usuario);
	public List<Usuario> readAll();	
	public boolean existe(String nombre);
}
