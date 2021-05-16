package springbootartacademy.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UsuarioOAuth2Per implements OAuth2User{
	
	private OAuth2User oath2user;
	
	public UsuarioOAuth2Per(OAuth2User oath2user) {
		
		this.oath2user = oath2user;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return oath2user.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return oath2user.getAuthorities();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return oath2user.getAttribute("name");
	}
	
	public String geFullName() {
		// TODO Auto-generated method stub
		return oath2user.getAttribute("name");
	}
	public String getEmail() {
		return oath2user.getAttribute("email");
	}

}
