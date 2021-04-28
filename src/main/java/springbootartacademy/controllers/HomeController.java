package springbootartacademy.controllers;


import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import springbootartacademy.models.entity.Categorias;
import springbootartacademy.models.entity.Obras;
import springbootartacademy.models.service.ICategoriasService;
import springbootartacademy.models.service.IFileService;
import springbootartacademy.models.service.IObrasService;


@Controller
public class HomeController {
	@Autowired
	private IObrasService servicioobras;

	@Autowired
	private ICategoriasService serviciocategorias;
	@Autowired
	private IFileService ifileser;

	
@GetMapping("/error403")	
public String error403() {
	return "frontend/Errores/error403";
}
@GetMapping("/inicio")
public ModelAndView inicio() {
	return obrastodaspagina(1);
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

@GetMapping("/obrastodaspagina/{numeropagina}")
public ModelAndView obrastodaspagina(@PathVariable("numeropagina") int currentPage) {
	Page<Obras> page = servicioobras.ListarObrasTodas(currentPage);
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

@GetMapping("/tienda/categorias/{id}")
public String buscarcategoriasproducto(@PathVariable Long id, Model model) {
	servicioobras.findAllcategoriaobras(id);
	model.addAttribute("categorias", servicioobras.findAllcategoriaobras(id));
	return "frontend/home/inicio" ;
}

}