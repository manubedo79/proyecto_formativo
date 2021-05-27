package springbootartacademy.controllers;


import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import springbootartacademy.models.entity.Categorias;
import springbootartacademy.models.entity.Obras;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.service.IArticuloCarritoService;
import springbootartacademy.models.service.ICategoriasService;
import springbootartacademy.models.service.IFileService;
import springbootartacademy.models.service.IObrasService;


@Controller
public class HomeController {
	@Autowired
	private IObrasService servicioobras;
	@Autowired
	private IArticuloCarritoService carritoser;
	@Autowired
	private ICategoriasService serviciocategorias;
	@Autowired
	private IFileService ifileser;

	
@GetMapping("/error403")	
public String error403() {
	return "frontend/Errores/error403";
}
@GetMapping("/")
public ModelAndView inicio() {
	String busqueda=null;
	return obrastodaspagina(1,busqueda);
}
@GetMapping(value = "/imagenes/{filename:.+}")
public ResponseEntity<Resource> verFotoObras(@PathVariable String filename) {
	Resource recurso = null;
	try {
		recurso = ifileser.cargar(filename);
	} catch (MalformedURLException e) {
		e.printStackTrace();
	}
	return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
			.body(recurso);
}
@GetMapping(value = "/imagenescategorias/{filename:.+}")
public ResponseEntity<Resource> verFotoCategorias(@PathVariable String filename) {
	Resource recurso = null;
	try {
		recurso = ifileser.cargarImagenCategoria(filename);
	} catch (MalformedURLException e) {
		e.printStackTrace();
	}
	return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
			.body(recurso);
}

@GetMapping("/pagina/obra/{numeropagina}")
public ModelAndView obrastodaspagina(@PathVariable("numeropagina") int currentPage,@Param ("busqueda") String busqueda) {
	Page<Obras> page = servicioobras.ListarObrasTodas(currentPage,busqueda);
	long totalItems = page.getTotalElements();
	int totalpages = page.getTotalPages();
	List<Obras> listaObras = page.getContent();
	ModelAndView mav = new ModelAndView("frontend/home/inicio");
	List<Categorias> listacate = serviciocategorias.findAllUsers();
	mav.addObject("obra", listaObras);
	mav.addObject("categoria", listacate);
	mav.addObject("totalItems", totalItems);
	mav.addObject("totalpages", totalpages);
	mav.addObject("currentPage", currentPage);
	return mav;
}	

@GetMapping("/tienda/categoria/{id}")
public ModelAndView buscarcategoriasproducto(@PathVariable("id") Long id) {
	return obtenerobras_categoria(id, 1);
}
@GetMapping("/obra/categoria/{numeropagina}")
public ModelAndView obtenerobras_categoria(Long id, @PathVariable("numeropagina") int currentPage ) {
	Page<Obras> page = servicioobras.findAllcategoriaobras(id, currentPage);
	
	long totalItems = page.getTotalElements();
	int totalpages = page.getTotalPages();
	List<Obras> listaObras = page.getContent();
	ModelAndView mav = new ModelAndView("frontend/home/inicio");
	mav.addObject("categoria", serviciocategorias.findAllUsers());
	mav.addObject("obra", listaObras);
	mav.addObject("totalItems", totalItems);
	mav.addObject("totalpages", totalpages);
	mav.addObject("currentPage", currentPage);
	return mav;
}
@GetMapping("/navbar")
public String navbar(Authentication authentication, Model model) {
	Usuarios usuarios = (Usuarios) authentication.getPrincipal();
	model.addAttribute("contadorcarrito", carritoser.contarCarritos(usuarios));
	
	return "recursos/navbar";
}
}