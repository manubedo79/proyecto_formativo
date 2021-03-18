package springbootartacademy.controllers;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

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

}
