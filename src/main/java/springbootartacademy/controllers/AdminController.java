package springbootartacademy.controllers;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springbootartacademy.models.dao.ICategoriasDao;
import springbootartacademy.models.dao.IClientesDao;
import springbootartacademy.models.dao.IObrasDao;
import springbootartacademy.models.dao.IUsuariosDao;
import springbootartacademy.models.dao.IVentasDao;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private IUsuariosDao usudao;
	@Autowired
	private IClientesDao cliedao;
	@Autowired
	private ICategoriasDao catedao;
	@Autowired
	private IObrasDao obradao;
	@Autowired
	private IVentasDao ventdao;
	
@GetMapping("/inicio")
public String home(Model model) {
	model.addAttribute("contarusuario", usudao.count());
	model.addAttribute("contarcliente", cliedao.count());
	model.addAttribute("contarcategoria", catedao.count());
	model.addAttribute("contarobras", obradao.count());
	model.addAttribute("contarventas", ventdao.count());
	model.addAttribute("sumaventas", ventdao.SumaTotalVentas());
	return "backend/home";
}

}