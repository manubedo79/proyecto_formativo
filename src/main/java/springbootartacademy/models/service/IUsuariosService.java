package springbootartacademy.models.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import springbootartacademy.models.entity.Clientes;
import springbootartacademy.models.entity.Usuarios;
import java.util.List;

public interface IUsuariosService {

public Usuarios findByCorreo(String correo);
public void saveUsuarios(Usuarios usuarios,Clientes clientes);
public Usuarios getToken(String resetPasswordToken);
public void sendVerificationEmail(Usuarios nuevousuario,String siteURL)throws UnsupportedEncodingException, MessagingException;
public boolean verificacionenlace(String verification);
public Usuarios getUsuariosByCorreo(String correo);
public Usuarios findById(Long id);
public void saveNewUsuarios(Usuarios usuario);
public List<Usuarios> findAllUsers();
public boolean cambioEstado(Long id);
public void actualizarPefil(Usuarios usuarios, Clientes clientes);
public Page<Usuarios> ListarUsuariosTodos(int pageNumber,String busqueda);
public void edituser(Usuarios usuarios);
public void updatepassword(Usuarios usuarios);
public void guardopassword(Usuarios usuarios);
public String uniqueemail(String correo);
public void enviarCorreoContacta(String nombre,String correo,String mensaje,String telefono )throws UnsupportedEncodingException, MessagingException;
}
