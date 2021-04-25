package springbootartacademy.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import springbootartacademy.models.entity.Roles;
import springbootartacademy.models.entity.Usuarios;

public class MyUsuarioDetails implements UserDetails {
private Usuarios usuarios;

	public MyUsuarioDetails(Usuarios usuarios) {
	this.usuarios = usuarios;
}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Set<Roles> roles= new HashSet<>();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for(Roles i:roles) {
			authorities.add(new SimpleGrantedAuthority(i.getNombre()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return usuarios.getContraseña();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return usuarios.getCorreo();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return usuarios.isEstado();
	}

}
