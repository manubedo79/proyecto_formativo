package springbootartacademy.models.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.utils.UsersNotFoundException;
import springbootartacademy.utils.Utilidad;
@Service
public class ResetPasswordServiceImp  implements IResetPasswordService{
	@Autowired
	private IUsuariosService ususer;
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendTokenCorreo(String correo, String ResetPasswordLink) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("artacademy2020@email.com", "ArtAcademy");
		helper.setTo(correo);
		String subjeto = "¿Quieres cambiar tu contraseña?";
		String contenido = "<h3>Hola señor(a) usuario, si quieres recuperar tu contraseña</h3>"
				+"<h3>Clic Aquí</h3>"
				+"<a href=\""+ResetPasswordLink+"\">Cambio de contraseña</a>"
				+"<h5>Importa este enlace solo tiene como funcionalidad recuperar tu contraseña</h5>";
		helper.setSubject(subjeto);
		helper.setText(contenido, true);
		mailSender.send(message);
	}

	

	public void updateContraseña(String token, String correo) throws UsersNotFoundException {
		Usuarios usuarios = ususer.getUsuariosByCorreo(correo);
		if(usuarios !=null) {
			usuarios.setResetPasswordToken(token);
			ususer.guardopassword(usuarios);			
		}else {
			throw new UsersNotFoundException("El correo " +correo+" no está registrado");
		}
		
	}



	public Usuarios get(String resetpasswordToken) {
	return ususer.getToken(resetpasswordToken);
	}



	public void updatenuevaContraseña(Usuarios usuarios, String nuevaContraseña) {
		usuarios.setContraseña(Utilidad.passwordencode().encode(nuevaContraseña));
		usuarios.setResetPasswordToken(null);
		ususer.updatepassword(usuarios);
		
	}
	
	

}
