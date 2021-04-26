package springbootartacademy.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springbootartacademy.models.entity.Categorias;
import springbootartacademy.models.service.ICategoriasService;


@Controller
public class HomeController {
	@Autowired
	private ICategoriasService serviciocategorias;

@GetMapping("/error403")	
public String error403() {
	return "frontend/Errores/error403";
}
@GetMapping("/inicio")
public String inicio(Model model) {
	List<Categorias> listacate = serviciocategorias.findAllUsers();
	model.addAttribute("categoria", listacate);
	return "frontend/home/inicio";
}
}
