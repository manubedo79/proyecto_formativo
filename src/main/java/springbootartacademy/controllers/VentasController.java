package springbootartacademy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.entity.Ventas;
import springbootartacademy.models.service.IVentasService;

@Controller
public class VentasController {
	@Autowired
	private IVentasService iventaser;
@GetMapping("/ordenes")
public String ordenes(Authentication authentication,Model model) {
	Usuarios usuarios = (Usuarios) authentication.getPrincipal();
	model.addAttribute("ventas", iventaser.listarVentasUsuarios(usuarios));
	return "frontend/ordenes/ordenes";
}
@GetMapping("/ordenes/detalle/{id}")
public String detalle(@PathVariable("id") Long id, Model model) {
	Ventas ventas = iventaser.findByIdVenta(id);
	model.addAttribute("venta", ventas);
	return "frontend/ordenes/detalle";
}
@GetMapping(value="/ventas/fecha")
public @ResponseBody Iterable<Ventas> findAllFecha(){
	return iventaser.findByFechas();
}
@GetMapping("/venta/listar")
public ModelAndView ListarVentasTodas() {
String busqueda = null;
	return listBypage(1,busqueda);
}

@GetMapping("/pagina/{pageNumber}")
public ModelAndView listBypage(@PathVariable("pageNumber")int currentPage, @Param("busqueda")String busqueda) {
	Page<Ventas> page = iventaser.ListarVentasTodas(currentPage,busqueda);
	long totalItems = page.getTotalElements();
	int totalpages = page.getTotalPages();
	
	
	List<Ventas> ListarVentasTodas =page.getContent();
	
	ModelAndView mav = new ModelAndView("backend/Ventas/listar"); 
	mav.addObject("Listaventas", ListarVentasTodas);
	mav.addObject("totalItems", totalItems);
	mav.addObject("totalpages", totalpages);
	mav.addObject("currentPage", currentPage);
	mav.addObject("busqueda",busqueda);
	return mav;
}
}
