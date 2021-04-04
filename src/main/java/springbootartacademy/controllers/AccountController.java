package springbootartacademy.controllers;



import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;
import net.bytebuddy.utility.RandomString;
import springbootartacademy.models.dao.IRolesDao;
import springbootartacademy.models.entity.Roles;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.service.IResetPasswordService;
import springbootartacademy.models.service.IUsuariosService;
import springbootartacademy.utils.UsersNotFoundException;
import springbootartacademy.utils.Utilidad;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.security.Principal;


import javax.mail.MessagingException;

@Controller
public class AccountController {

	@Autowired
	private IResetPasswordService passser;
	@Autowired
	private IUsuariosService serviciousuario;
	@Autowired
	private IRolesDao rolesdao;
	
@GetMapping("/registro")
public String registro(Model model) {
	Roles rol = rolesdao.findByNombre("CLIENTE");
	List<Roles> listaroles = Arrays.asList(rol);
	model.addAttribute("roles",listaroles);
	model.addAttribute("usuario",new Usuarios());
	return "frontend/registro/registro";

}

@GetMapping("/login")
public String login(@RequestParam(value="error",required = false) String error, Model model,Principal principal) {
	if(principal != null ) {
		model.addAttribute("info", "La sesión sigue activa");
		return "redirect:/inicio";
	}
	if(error != null ) {
		model.addAttribute("error", "Los datos no coinciden");
	}
	return "frontend/cuenta/login";

}

@GetMapping("/recuperarpassword")
public String resetContraseña(){
	return "frontend/recuperarcontraseña/recuperarcontraseña";
}
@GetMapping("/registro/verificacion")
public String registroverificacion(){
	return "frontend/registro/verificacion";
}

@GetMapping("/mensajeRegistro")
public String mensajeRegistro(){
	return "frontend/registro/mensaje";
}
@PostMapping("/creandoregistro")
public String creandoregistro( @ModelAttribute("usuario")Usuarios nuevousuario, Model model,HttpServletRequest request, BindingResult bindingResults, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException, MessagingException{
	Roles rol = rolesdao.findByNombre("CLIENTE");
	List<Roles> listaroles = Arrays.asList(rol);
	model.addAttribute("roles",listaroles);
	model.addAttribute("usuario", nuevousuario);
	
	
	serviciousuario.saveUsuarios(nuevousuario);
	String siteURL = Utilidad.getSitioUrl(request);
	serviciousuario.sendVerificationEmail(nuevousuario, siteURL);
	return "redirect:/datospersonales/"+nuevousuario.getId();
}
@PostMapping("/resetpassword")
public String resetPassword(HttpServletRequest request, Model model) throws UnsupportedEncodingException, MessagingException {
	String correo = request.getParameter("correo");
	String token = RandomString.make(45);
	try {
		
		
		passser.updateContraseña(token, correo);
		String recuperarContraseñaUrl= Utilidad.getSitioUrl(request) + "/formulariocontraseña?token="+token;
		passser.sendTokenCorreo(correo, recuperarContraseñaUrl);
		model.addAttribute("info", "Se envío un enlace a tu correo por favor revisa");
		
		} catch (UsersNotFoundException e) {
			model.addAttribute("error", e.getMessage());
		}
	return "frontend/recuperarcontraseña/recuperarcontraseña";
}
	@GetMapping("/verificate")
	public String verificando(@Param("code") String code,Model model)
	{
		boolean verifica = serviciousuario.verificacionenlace(code);
		String paginaTitulo = verifica ? " ¡Verificacion Completa!" : " ¡Verificacion Fallida!";
		model.addAttribute("paginaTitulo",paginaTitulo);
		return "frontend/registro/verificacion" ;
	}

	@GetMapping("/formulariocontraseña")
	public String formContraseña(@Param(value="token") String token, 
			Model model) {
		Usuarios usuarios = passser.get(token);
		if(usuarios==null) {
			model.addAttribute("error", "Error no se puede acceder a este formulario");
			return "redirect:/recuperarpassword";
		}
		model.addAttribute("token", token);
		return "frontend/recuperarcontraseña/formularioContraseña";
	}
	@PostMapping("/cambiarcontraseña")
	public String cambiarContraseña(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		Usuarios usuarios = passser.get(token);
		if(usuarios==null) {
			model.addAttribute("error", "Error no se puede acceder a este formulario");
		}else {
			passser.updatenuevaContraseña(usuarios, password);
			model.addAttribute("info", "Se cambio su contraseña de forma correcta");
		}
		
		return "redirect:/login";
	}
}
