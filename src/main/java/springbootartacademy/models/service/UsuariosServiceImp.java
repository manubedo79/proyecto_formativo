package springbootartacademy.models.service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bytebuddy.utility.RandomString;
import springbootartacademy.models.dao.IClientesDao;
import springbootartacademy.models.dao.IRolesDao;
import springbootartacademy.models.dao.IUsuariosDao;
import springbootartacademy.models.entity.*;
import springbootartacademy.utils.Utilidad;

@Service
public class UsuariosServiceImp implements IUsuariosService {
	@Autowired
	private IUsuariosDao usudao;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private IRolesDao rolesdao;
	@Autowired
	private IClientesDao cliedao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public Usuarios findByCorreo(String correo) {
		return usudao.findByCorreo(correo);
	}

	public boolean verificacionenlace(String verification) {
		Usuarios verificacion = usudao.findByVerification(verification);
		if (verificacion == null) {
			return false;
		} else {
			long id = verificacion.getId();
			usudao.actualizaestadoborraverifica(id);
			return true;
		}
	}

	public boolean cambioEstado(Long id) {
		Usuarios busca = usudao.findById(id).orElse(null);
		if (busca == null) {
			return false;
		} else {
			if (busca.isEstado()) {
				usudao.actualizaestado(false, busca.getId());
			} else {
				usudao.actualizaestado(true, busca.getId());
			}
			return true;
		}
	}

	public void saveUsuarios(Usuarios usuarios, Clientes clientes) {
		String token = RandomString.make(45);
		Roles rol = rolesdao.findByNombre("CLIENTE");
		Set<Roles> listaroles = new HashSet<Roles>(Arrays.asList(rol));
		usuarios.setVerification(token);
		usudao.findByCorreo(usuarios.getCorreo());
		String encodedPassword = Utilidad.passwordencode().encode(usuarios.getContrase??a());
		usuarios.setContrase??a(encodedPassword);
		usuarios.setEstado(false);
		usuarios.setRoles(listaroles);
		usudao.save(usuarios);
		Usuarios usuarioCliente = usudao.findById(usuarios.getId()).orElse(null);
		clientes.setUsuarios(usuarioCliente);
		cliedao.save(clientes);
	}

	public Usuarios getToken(String resetPasswordToken) {
		// TODO Auto-generated method stub
		return usudao.findByResetPasswordToken(resetPasswordToken);
	}

	public void sendVerificationEmail(Usuarios nuevousuario, String siteURL)
			throws UnsupportedEncodingException, MessagingException {
		String subject = "Por favor verifica tu registro";
		String verificion = siteURL + "/cuenta/verificacion?code=" + nuevousuario.getVerification();
		String mailcontent = "<div style=\"width: 450px;height: 650px;border-radius: 20px;margin:20px auto;position: relative;padding: 5px;font-family:Arial;text-decoration:none;text-align:center;\">"
				+ "<p style=\"color:black;\">Se??or(a) usuario  para poder acceder por completo a nuestra p??gina web debe de verificarse presionando en el siguiente link </p>"
				+ " <p style=\"color:black;\">Art Academy Team</p>"
				+ "<a style=\"width: 80%;background-color: #45B39D;display: block;margin:auto;border: none;padding: 15px;color: #D4D4D4;border-radius: 5px;margin-top: 30px;\" href=\""
				+ verificion + "\">Verificar mi cuenta</a>" + "</div>";
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(nuevousuario.getCorreo());
		helper.setSubject(subject);
		helper.setText(mailcontent, true);
		mailSender.send(message);
	}

	@Override
	public Usuarios getUsuariosByCorreo(String correo) {

		return usudao.getUsuariosByCorreo(correo);
	}

	@Override
	public Usuarios findById(Long id) {
		// TODO Auto-generated method stub
		return usudao.findById(id).orElse(null);
	}

	@Override
	public Page<Usuarios> ListarUsuariosTodos(int pageNumber, String busqueda) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 12);
		if (busqueda != null) {
			return usudao.findAll(busqueda, pageable);
		}
		return usudao.findAll(pageable);
	}

	@Override
	public void saveNewUsuarios(Usuarios usuarios) {
		usuarios.setContrase??a(Utilidad.passwordencode().encode(usuarios.getContrase??a()));
		usuarios.setEstado(true);
		usudao.save(usuarios);

	}

	@Override
	public List<Usuarios> findAllUsers() {
		return (List<Usuarios>) usudao.findAll();
	}

	@Transactional
	@Override
	public void actualizarPefil(Usuarios usuarios, Clientes clientes) {

		usudao.save(usuarios);
		cliedao.save(clientes);

	}

	@Override
	public void updatepassword(Usuarios usuarios) {
		usudao.save(usuarios);

	}

	@Override
	public void edituser(Usuarios usuarios) {
		usuarios.setEstado(true);
		usudao.save(usuarios);

	}

	

	@Override
	public void guardopassword(Usuarios usuarios) {
		usudao.save(usuarios);

	}

	@Override
	public String uniqueemail(String correo) {
		Usuarios usuarios = usudao.findByCorreo(correo);
		return (usuarios == null) ? "Unique" : "Duplicate";
	}
	@Override	
	public void enviarCorreoContacta(String nombre,String correo,String mensaje,String telefono )throws UnsupportedEncodingException, MessagingException {
		String subject ="Te Contactaron Art Academy";
		String mailcontent = 
		"<!DOCTYPE html>\r\n" + 
		"<html>\r\n" + 
		"<head>\r\n" + 
		"</head>\r\n" + 
		"<body>\r\n" ;
		mailcontent+="<p>Se??or ADMIN "+nombre+" te ha escrito lo siguiente, si quieres contactarlo su correo electronico es: "+correo+
		" y su n??mero de telefono es "+telefono+
		"</p>\r\n" + 
		"<h3>\r\n" +mensaje+" </h3>\r\n" +
		"</body>\r\n" + 
		"</html>\r\n" ;
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo("artacademy357@gmail.com");
		helper.setSubject(subject);
		helper.setText(mailcontent, true);
		mailSender.send(message);

	}
	

}
