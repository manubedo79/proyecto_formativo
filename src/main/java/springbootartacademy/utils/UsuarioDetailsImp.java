package springbootartacademy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootartacademy.models.dao.IUsuariosDao;
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
		Usuarios usuarios=DAO.getCorreoUsuario(username);
		if(usuarios == null) {
			logger.error("Error en el Login: no existe el usuario '" + username + "' en el sistema!");
			throw new UsernameNotFoundException ("El usuario no existe");
		}
		return usuarios;
		
	}
	public void authenticateUser(String username) {
		UserDetails userDetails = loadUserByUsername(username);
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
