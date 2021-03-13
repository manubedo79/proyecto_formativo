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
		String contenido = "<h3>Hola señor(a) usuario, Si quieres recuperar tu contraseña</h3>"
				+"<h3>Clic Aquí</h3>"
				+"<a href=\""+ResetPasswordLink+"\">Cambio de contraseña</a>"
				+"<h5>Importa este enlace solo tiene como funcionalidad recuperar tu contraseña</h5>";
		helper.setSubject(subjeto);
		helper.setText(contenido, true);
		mailSender.send(message);
	}

	

	public void updateContraseña(String token, String correo) throws UsersNotFoundException {
		Usuarios usuarios = ususer.findByCorreo(correo);
		if(usuarios !=null) {
			usuarios.setResetPasswordToken(token);
			ususer.saveUsuarios(usuarios);			
		}else {
			throw new UsersNotFoundException("No se pudo enviar el correo " +correo);
		}
		
	}



	public Usuarios get(String resetpasswordToken) {
	return ususer.getToken(resetpasswordToken);
	}



	public void updatenuevaContraseña(Usuarios usuarios, String nuevaContraseña) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePassword = encoder.encode(nuevaContraseña);
		usuarios.setContraseña(encodePassword);
		usuarios.setResetPasswordToken(null);
		ususer.saveUsuarios(usuarios);
		
	}
	
	

}
