package springbootartacademy.controllers;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Controller
@RequestMapping("/admin")
public class AdminController {
@GetMapping("/home")
public String home() {
	return "backend/usuarios/listar";
}
public void addResourceHandlers(ResourceHandlerRegistry registro)
{
	Path imagenActualizaDirc = Paths.get("./imagenes/**");
	String imagenActualizaPath = imagenActualizaDirc.toFile().getAbsolutePath();

	registro.addResourceHandler("/imagenes/**").addResourceLocations("file:/"+imagenActualizaDirc+"/");

}
}