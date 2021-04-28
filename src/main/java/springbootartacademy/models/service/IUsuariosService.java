package springbootartacademy.models.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;

import springbootartacademy.models.entity.AuthenticationProvider;
import springbootartacademy.models.entity.Clientes;
import springbootartacademy.models.entity.Usuarios;
import java.util.List;

public interface IUsuariosService {
public Usuarios findByCorreo(String correo);
public String findbyCorreo(String correo);
public void saveUsuarios(Usuarios usuarios,Clientes clientes);
public Usuarios getToken(String resetPasswordToken);
public void sendVerificationEmail(Usuarios nuevousuario,String siteURL)throws UnsupportedEncodingException, MessagingException;
public boolean verificacionenlace(String verification);
public Usuarios getUsuariosByCorreo(String correo);
public void CreateNuevoUsuarioAfterOAuthLoginSuccess(String name, String email, 
		AuthenticationProvider provider);
public Usuarios findById(Long id);
public void saveNewUsuarios(Usuarios usuario);
public List<Usuarios> findAllUsers();
public boolean cambioEstado(Long id);
public void actualizarPefil(Usuarios usuarios, Clientes clientes);
public Page<Usuarios> ListarUsuariosTodos(int pageNumber,String busqueda);
public boolean iscorreounique(String correo);
public void edituser(Usuarios usuarios);
public void updatepassword(Usuarios usuarios);
}
