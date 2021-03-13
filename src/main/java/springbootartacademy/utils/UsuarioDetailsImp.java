package springbootartacademy.utils;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springbootartacademy.models.dao.IUsuariosDao;
import springbootartacademy.models.entity.Roles;
import springbootartacademy.models.entity.Usuarios;
@Service("UserDetailsService")
public class UsuarioDetailsImp implements UserDetailsService {

@Autowired 
private IUsuariosDao DAO;  
private Logger logger = LoggerFactory.getLogger(UsuarioDetailsImp.class);
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Usuarios usuarios=DAO.getUsuarios(username);
		if(usuarios == null) {
			logger.error("Error en el Login: no existe el usuario '" + username + "' en el sistema!");
			throw new UsernameNotFoundException ("El usuario no existe");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(Roles roles: usuarios.getRoles()) {
			logger.info("Role: ".concat(roles.getNombre()));
			authorities.add(new SimpleGrantedAuthority(roles.getNombre()));
		}
		 if(authorities.isEmpty()) {
	        	logger.error("Error en el Login: Usuario '" + username + "' no tiene roles asignados!");
	        	throw new UsernameNotFoundException("Error en el Login: usuario '" + username + "' no tiene roles asignados!");
	        }
		 return new User(usuarios.getNombreusuario(), usuarios.getContraseña(), usuarios.isEstado(), true, true, true, authorities);
		
	}

}
