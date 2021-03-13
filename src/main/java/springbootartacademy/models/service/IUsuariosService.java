package springbootartacademy.models.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import springbootartacademy.models.entity.AuthenticationProvider;
import springbootartacademy.models.entity.Usuarios;

public interface IUsuariosService {
public Usuarios findByCorreo(String correo);
public Usuarios findByNombreusuario(String nombreusuario);
public void saveUsuarios(Usuarios usuarios);
public Usuarios getToken(String resetPasswordToken);
public void sendVerificationEmail(Usuarios nuevousuario,String siteURL)throws UnsupportedEncodingException, MessagingException;
public boolean verificacionenlace(String verification);

public Usuarios getUsuariosByCorreo(String correo);

public void CreateNuevoUsuarioAfterOAuthLoginSuccess(String name, String email, 
		AuthenticationProvider provider);
public Usuarios findById(Long id);





}
