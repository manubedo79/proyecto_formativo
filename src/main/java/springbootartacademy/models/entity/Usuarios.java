package springbootartacademy.models.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuarios implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	private String contraseña;

	@Column(length = 50, nullable = false, unique = true)
	private String correo;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuarios_id")
	private Set<Roles> roles;

	@OneToOne(mappedBy = "usuarios", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Clientes clientes;

	public Clientes getClientes() {
		return clientes;
	}

	public void setClientes(Clientes clientes) {
		this.clientes = clientes;
	}

	@Column(name = "reset_password_token", length = 45, nullable = true)

	private String resetPasswordToken;

	@Column(name = "verification_token", length = 45, updatable = false, nullable = true)
	private String verification;
	private boolean estado;
	@Enumerated(EnumType.STRING)
	private Provider provider;

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorites = new HashSet<>();
		for (Roles rol : roles) {
			authorites.add(new SimpleGrantedAuthority(rol.getNombre()));
		}
		return authorites;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.contraseña;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.correo;
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
		return this.estado;
	}

	public String fullname() {
		return clientes.getNombre();
	}
	
}
