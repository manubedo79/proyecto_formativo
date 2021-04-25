package springbootartacademy.controllers;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.lowagie.text.DocumentException;
import springbootartacademy.models.dao.IRolesDao;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.service.IUsuariosService;
import springbootartacademy.utils.UsuarioDetailsImp;
import springbootartacademy.utils.Utilidad;

@Controller
public class UsuariosController {

	@Autowired
	@Qualifier("UserDetailsService")
	private UsuarioDetailsImp idetailsimp;
	@Autowired
	private IUsuariosService service;
	@Autowired
	private IRolesDao rolesdao;
	
	
	@GetMapping("/ListaUsuarios")
	public ModelAndView ListaUsuarioTodos() {
	String busqueda = null;
		return listBypage(1,busqueda);
	}

@GetMapping("/page/{pageNumber}")
public ModelAndView listBypage(@PathVariable("pageNumber")int currentPage, @Param("busqueda")String busqueda) {
	Page<Usuarios> page = service.ListarUsuariosTodos(currentPage,busqueda);
	long totalItems = page.getTotalElements();
	int totalpages = page.getTotalPages();
	
	
	List<Usuarios> listaUsuarios =page.getContent();
	
	ModelAndView mav = new ModelAndView("backend/usuarios/listar"); 
	mav.addObject("ListaUsu", listaUsuarios);
	mav.addObject("totalItems", totalItems);
	mav.addObject("totalpages", totalpages);
	mav.addObject("currentPage", currentPage);
	mav.addObject("busqueda",busqueda);
	return mav;
}

@GetMapping("/registroUsuarios")
public String registro( Model model) {
	
	model.addAttribute("roles",rolesdao.findAll());
	model.addAttribute("usuario",new Usuarios());
	return "backend/usuarios/formulario";

}

@PostMapping("/guardarUsuario")
public String guardarUsuario (@Valid @ModelAttribute("usuario") Usuarios usuario , BindingResult result, RedirectAttributes flash, Model model) {
	
	if(result.hasErrors()) {
		model.addAttribute("roles",rolesdao.findAll());
		return "backend/usuarios/formulario";
	}
	String mesaje=(usuario.getId()!=null)?"Se editó correctamente"
	:"Se guardo correctamente";
	flash.addFlashAttribute("success", mesaje);
	service.saveNewUsuarios(usuario);
	return "redirect:/ListaUsuarios";
}



@GetMapping("/cambiarEstado/{id}")
public String cambiarEstado(@PathVariable(value="id") Long id) {
	service.cambioEstado(id);
	return "redirect:/ListaUsuarios";
}

	@GetMapping("/users/export")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=usuarios_" + currentDateTime +".pdf";
		
		response.setHeader(headerKey, headerValue);
		
		List<Usuarios> listaUsuarios = service.findAllUsers();
		
		UserPDFExporter exporter = new UserPDFExporter(listaUsuarios);
		exporter.export(response);
		
	}
// Metodo para mostrar el formulario de perfil
@GetMapping("/miperfil")
public String miperfil(Model model, Authentication authentication) {
	Usuarios usuarios = (Usuarios) authentication.getPrincipal();
	model.addAttribute("usuario", usuarios);
	return "frontend/cuenta/miperfil";
}

// Metodo para actaulizar el perfil
@RequestMapping(value = "/update-user-info", method = RequestMethod.POST)
public String updateUserInfo(@ModelAttribute("usuario") Usuarios usuarios,
		 Model model, Principal principal)
		throws Exception {
	Usuarios currentUser = service.findByCorreo(principal.getName());
	if (currentUser == null) {
		throw new Exception("User not found");
	}

	
	/* check email already exists */
	Usuarios existingUser = service.findByCorreo(usuarios.getCorreo());
	if (existingUser != null && !existingUser.getId().equals(currentUser.getId())) {
		model.addAttribute("emailExists", true);
		return "frontend/cuenta/miperfil";
	}

	currentUser.setCorreo(usuarios.getCorreo());
	service.actualizarPefil(currentUser);
	model.addAttribute("updateSuccess","Se actualizaron sus datos de usuarios de forma correcta");
	model.addAttribute("usuario", currentUser);
	idetailsimp.authenticateUser(currentUser.getUsername());
	return "redirect:/miperfil";
}
@GetMapping("/editarpassword")
public String editarpassword(Authentication authentication, Model model) {
	Usuarios usuarios = (Usuarios) authentication.getPrincipal();
	model.addAttribute("usuario", usuarios);
	return "frontend/cuenta/editarpassword";
}
@PostMapping("/editarpassword/guardar")
public String guardarpassword(@ModelAttribute("usuario") Usuarios usuarios,
		 Model model, Principal principal) throws Exception {
	Usuarios currentUser = service.findByCorreo(principal.getName());
	if (currentUser == null) {
		throw new Exception("User not found");
	}
	String pass = Utilidad.passwordencode().encode(usuarios.getContraseña());
	currentUser.setContraseña(pass);
	service.actualizarPefil(currentUser);
	model.addAttribute("updateSuccess", true);
	model.addAttribute("usuario", currentUser);
	idetailsimp.authenticateUser(currentUser.getUsername());
	return "redirect:/miperfil";
	}
@GetMapping("/editarusuario/{id}")
public String editarusuario(Model model, @PathVariable(name="id")Long id) {
	Usuarios usuarios = service.findById(id);
	model.addAttribute("roles",rolesdao.findAll());
	model.addAttribute("usuario", usuarios);
	return "backend/usuarios/formulario";
}
}
