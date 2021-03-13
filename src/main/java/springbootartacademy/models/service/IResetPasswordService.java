package springbootartacademy.models.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;


import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.utils.UsersNotFoundException;

public interface IResetPasswordService {
	
	public void sendTokenCorreo(String correo, String ResetPasswordLink) throws UnsupportedEncodingException, MessagingException;
	public Usuarios get(String resetpasswordToken);
	public void updateContraseña(String token, String correo) throws UsersNotFoundException;
	public void updatenuevaContraseña(Usuarios usuarios, String nuevaContraseña);

}
