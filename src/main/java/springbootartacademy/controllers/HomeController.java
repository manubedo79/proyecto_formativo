package springbootartacademy.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
	
@GetMapping("/error403")	
public String error403() {
	return "frontend/Errores/error403";
}
@GetMapping("/inicio")
public String inicio() {

	return "frontend/home/inicio";
}
}
