package springbootartacademy.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;

import springbootartacademy.models.dao.IRolesDao;
import springbootartacademy.models.entity.Roles;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.service.IResetPasswordService;
import springbootartacademy.models.service.IUsuariosService;
import springbootartacademy.utils.Utilidad;

@Controller
public class UsuariosController {

	@Autowired
	private IResetPasswordService passser;
	@Autowired
	private IUsuariosService service;
	@Autowired
	private IRolesDao rolesdao;
	
	
@GetMapping("/ListaUsuarios")
	public ModelAndView ListaUsuarioTodos() {
		return listBypage(1);
	}

@GetMapping("/page/{pageNumber}")
public ModelAndView listBypage(@PathVariable("pageNumber")int currentPage) {
	Page<Usuarios> page = service.ListarUsuariosTodos(currentPage);
	long totalItems = page.getTotalElements();
	int totalpages = page.getTotalPages();
	
	
	List<Usuarios> listaUsuarios =page.getContent();
	
	ModelAndView mav = new ModelAndView("backend/usuarios/listar"); 
	mav.addObject("ListaUsu", listaUsuarios);
	mav.addObject("totalItems", totalItems);
	mav.addObject("totalpages", totalpages);
	mav.addObject("currentPage", currentPage);
	return mav;
}

@GetMapping("/registroUsuarios")
public String registro(Model model) {
	model.addAttribute("roles",rolesdao.findAll());
	model.addAttribute("usuario",new Usuarios());
	return "backend/usuarios/formulario";

}

@PostMapping("/guardarUsuario")
public String guardarUsuario( @ModelAttribute("usuario")Usuarios nuevousuario, Model model,HttpServletRequest request, BindingResult bindingResults, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException, MessagingException{
	
	service.saveUsuarios(nuevousuario);
	return "redirect:/ListaUsuarios";
}
@GetMapping("/cambiarEstado/{Nombreusuario}")
public String cambiarEstado(@PathVariable(value="Nombreusuario") String Nombreusuario) {
	service.cambioEstado(Nombreusuario);
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

}
