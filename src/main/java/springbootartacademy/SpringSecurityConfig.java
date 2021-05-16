package springbootartacademy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;



import springbootartacademy.models.service.IUsuariosService;
import springbootartacademy.security.CustomOAuth2User;
import springbootartacademy.security.CustomOAuth2UserService;
import springbootartacademy.security.GoogleOAuth2SuccessHandler;
import springbootartacademy.security.OAuth2LoginSuccessHandler;
import springbootartacademy.security.UsuarioOAuth2UserService;
import springbootartacademy.utils.UsuarioDetailsImp;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	 @Autowired
	 private UsuarioDetailsImp usuarioDetailsImp;
	 @Autowired
	 private OAuth2LoginSuccessHandler oauth2Login;

	 @Bean
	 public BCryptPasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	 @Bean
	 public UsuarioOAuth2UserService usuariosOauth2user() {
		 return new UsuarioOAuth2UserService();
	 }
	 
	 

	 @Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
			.antMatchers("/frontend/css/**","/frontend/img/**", "/frontend/js/**", "/frontend/webfonts/**", "/images/**", "/recuperarpassword/**", "/resetpassword/**", "/formulariocontraseña/**", "/cambiarcontraseña/**", "/oauth2/**", "/registro/**", "/mensajeRegistro/**", "/creandoregistro/**", "/registro/verificacion/**", "/verificate/**", "/admin/home/**", "/backend/dist/**", "/backend/plugins/**", "/datospersonales/**", "/terminarregistro/**", "/inicio/**","/login/**","/registroUsuarios/**","/guardarUsuario/**","/ListaUsuarios/**", "/page/**","/backend/js/**","/cambiarEstado/**","/frontend/acount/**", "/usuario/check_email/**", "/obtener/municipios/**", "/editarusuario/**", "/registro/verification/**","/images/**","/frontend/img/**","/obrastodaspagina/**","/imagenes/**", "/oauth/**","/cambiarpassword/**","/formulariopassword/**").permitAll()
			
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login") 
			.usernameParameter("correo")
			.defaultSuccessUrl("/inicio")
			.permitAll()
			.and()
			.oauth2Login()
			.loginPage("/login")
			.userInfoEndpoint().userService(usuariosOauth2user())
			
			.and()
			.successHandler(oauth2Login)
			.defaultSuccessUrl("/inicio")
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
	 @Autowired
	    GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
	  
}
