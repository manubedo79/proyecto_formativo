package springbootartacademy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
		.antMatchers("/frontend/css/**","/frontend/img/**", "/frontend/js/**", "/frontend/webfonts/**", "/images/**", "/recuperarpassword/**", "/resetpassword/**", "/formulariocontraseña/**", "/cambiarcontraseña/**", "/oauth2/**", "/registro/**", "/mensajeRegistro/**", "/creandoregistro/**", "/registro/verificacion/**", "/verificate/**", "/admin/home/**", "/backend/dist/**", "/backend/plugins/**", "/datospersonales/**", "/terminarregistro/**", "/inicio/**","/login/**").permitAll()
		
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login") .permitAll()
		.defaultSuccessUrl("/inicio")
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

}
