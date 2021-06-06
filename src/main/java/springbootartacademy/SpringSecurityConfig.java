package springbootartacademy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import springbootartacademy.utils.UsuarioDetailsImp;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	 @Autowired
	 private UsuarioDetailsImp usuarioDetailsImp;
	 

	 @Bean
	 public BCryptPasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	
	 
	 

	 @Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
			.antMatchers("/resetpassword/**", "/formulariocontraseña/**", "/cambiarcontraseña/**", "/oauth2/**", "/registro/**", "/mensajeRegistro/**", "/creandoregistro/**", "/registro/verificacion/**", "/verificate/**", "/admin/home/**", "/backend/dist/**", "/backend/plugins/**", "/datospersonales/**", "/terminarregistro/**", "/inicio/**","/login/**","/registroUsuarios/**","/guardarUsuario/**","/ListaUsuarios/**", "/page/**","/backend/js/**","/cambiarEstado/**","/frontend/acount/**", "/usuario/check_email/**", "/obtener/municipios/**", "/editarusuario/**","/images/**","/frontend/img/**","/imagenes/**", "/oauth/**").permitAll()
			.antMatchers("/recuperacion/**", "/frontend/css/**",
					"/frontend/img/**", "/frontend/js/**", "/frontend/webfonts/**", "/images/**", "/iniciosesion/**", 
					"/registro/**", "/mensaje/registro/**", "/cuenta/verificacion/**", "/formulario/recuperacion/**", 
					"/cambiar/contrasena/**", "/tienda/categoria/**", "/obra/categoria/**", "/pagina/obra/**", "/obra/detalle/**",
					"/obra/obtener_precio_cantidad/**", "/", "/usuario/validar/correo/**", "/categoria/categoriastodas/**", "/imagenescategorias/**").permitAll()
			.antMatchers("/categoria/listar/**", "/categoria/pagina/**", 
			"/categoria/formulario", "/categoria/guardar/**", "/categoria/editar/**", "/categoria/exportar/**", "/cliente/listar/**", "/cliente/pagina/**", "/cliente/detalle/**", "/cliente/editar/**", 
			"/cliente/guardar/**", "/cliente/exportar/**","/obra/listar/**", "/obra/pagina/**", "/obra/formulario/**", "/obra/guardar/**", "/obra/editar/**", "/obra/cambiarestado/**", "/obra/exportar/**",
			"/usuario/listar/**", "/usuario/pagina/**", "/usuario/formulario/**", "/usuario/guardar/**", "/usuario/cambiarestado/**", "/usuario/exportar/**", "/usuario/editar/**").hasAnyAuthority("ADMIN")
			.antMatchers("/usuario/miperfil/**", "/usuario/actualizar/perfil/**", "/usuario/cambio/contra/**").hasAnyAuthority("CLIENTE", "ADMIN")
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/iniciosesion") 
			.usernameParameter("correo")
			.permitAll()
			.and()
			
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/error403");
			
		}
	 @Autowired
		protected void configurerGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(usuarioDetailsImp)
			.passwordEncoder(passwordEncoder());
			
		}
	 
	  
}
