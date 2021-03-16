package springbootartacademy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import springbootartacademy.models.service.IUsuariosService;

@Controller
@RequestMapping("/Usuarios")
public class UsuariosController {

	@Autowired
	private IUsuariosService usuarios;
	
@GetMapping("/ListaUsuarios")
	public ModelAndView ListaUsuarioTodos() {
		
		ModelAndView mav = new ModelAndView("backend/usuarios/listar"); 
		mav.addObject("ListaUsu", usuarios.ListarUsuariosTodos());
		return mav;
	}
}
