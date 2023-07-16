package negocioImpl;

import dao.UsuarioDao;
import daoImpl.UsuarioDaoImpl;
import dominio.Usuario;
import negocio.NegocioUsuario;

public class NegocioUsuarioImpl implements NegocioUsuario {
	
	UsuarioDao usuarioDao = new UsuarioDaoImpl();

	@Override
	public int getUltimoIdUsuario() {				
		return usuarioDao.getUltimoIdUsuario();			
	}		 
	
	@Override
	public boolean insertAdmin(Usuario usuario) {
		return usuarioDao.insertAdmin(usuario);
	}

	@Override
	public boolean update(Usuario usuario) {
		return usuarioDao.update(usuario);
	}

	@Override
	public boolean delete(Usuario usuario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Usuario initUser(String username, String clave) {
		
		Usuario usuario = new Usuario();
		
		usuario = usuarioDao.initUser(username, clave);
		
		return usuario;
	}
	
	@Override
	public boolean existeUsuario(String nombre) {
		return usuarioDao.existe(nombre);
	}
	
}
