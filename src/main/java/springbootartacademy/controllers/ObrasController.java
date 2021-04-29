 package springbootartacademy.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.Categorias;
import springbootartacademy.models.entity.Obras;
import springbootartacademy.models.service.ICaracteristicasService;
import springbootartacademy.models.service.ICategoriasService;
import springbootartacademy.models.service.IFileService;
import springbootartacademy.models.service.IObrasService;

@Controller
public class ObrasController {

	@Autowired
	private IObrasService servicioobras;
	@Autowired
	private ICategoriasService serviciocategorias;
	@Autowired
	private ICaracteristicasService serviciocaracteristica;
	@Autowired
	private IFileService ifileser;
	
	@GetMapping("/formularioobra")
	public String formularioCategoria(Model model) {
		List<Categorias> listacate = serviciocategorias.findAllUsers();
		model.addAttribute("obra", new Obras());
		model.addAttribute("caracteristica", new Caracteristicas());
		model.addAttribute("categoria", listacate);
		return "backend/Obras/formulario";
	}

	@PostMapping("/guardarobra")
	public String guardarObra(@Valid @ModelAttribute("obra") Obras obra,@Valid @ModelAttribute("caracteristica")Caracteristicas caracteristica, 
	BindingResult result, @RequestParam("imagenobra")MultipartFile multipart , @RequestParam("imagenobra2") MultipartFile multipart2 ,
	@RequestParam("imagenobra3") MultipartFile multipart3 , RedirectAttributes flash) throws IOException {
		if(!multipart.isEmpty()) 
		{
			if(obra.getImagenprincipal() != null && obra.getImagenprincipal().length()>0 
			&& obra.getImagen2() != null && obra.getImagen2().length()>0 
			&& obra.getImagen3() != null && obra.getImagen3().length()>0) 
			{
				ifileser.eliminar(obra.getImagenprincipal());
				ifileser.eliminar(obra.getImagen2());
				ifileser.eliminar(obra.getImagen3());
			}
			String nombreruta1 = null, nombreruta2 = null,nombreruta3 = null;
			try 
			{
				if (!multipart2.isEmpty()) {nombreruta2 = ifileser.copiar(multipart2);}
				if (!multipart3.isEmpty()) {nombreruta3 = ifileser.copiar(multipart3);}
				nombreruta1 = ifileser.copiar(multipart);
			} catch (Exception e) {e.printStackTrace();}
			obra.setRutaimagen_principal(nombreruta1);
			obra.setRutaimagen_2(nombreruta2);
			obra.setRutaimagen_3(nombreruta3);
			servicioobras.guardarObra(obra);
		}
		Obras obracaracter = servicioobras.findbyId(obra.getId());
		String mensaje = (obra.getId() != null) ? "Se edito de forma correcta la obra" : "Se guardo de forma correcta la obra";
		caracteristica.setObras(obracaracter);
		serviciocaracteristica.guardarCaracteristica(caracteristica);
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/ListaObras";
	}
	@GetMapping("/cambiarEstadoobras/{id}")
	public String cambiarEstado(@PathVariable(value="id") Long id) {
		servicioobras.cambioEstado(id);
		return "redirect:/ListaObras";
	}
	
	/*@PostMappings("/editarobras/{id}")
	public String editar (@PathVariable(value="id") Long id, Model model) {
		Optional<Obras>obras=servicioobras.listarid(id);
		return "form"
	}*/
	
	@GetMapping("/ListaObras")
	public ModelAndView ListaObrasTodos() {
	String busqueda = null;
		return listBypage(1,busqueda);
	}

@GetMapping("/pageObras/{pageNumber}")
public ModelAndView listBypage(@PathVariable("pageNumber")int currentPage, @Param("busqueda")String busqueda) {
	Page<Obras> page = servicioobras.ListarObrasTodas(currentPage,busqueda);
	long totalItems = page.getTotalElements();
	int totalpages = page.getTotalPages();
	
	
	List<Obras> ListarObrasTodas =page.getContent();
	
	ModelAndView mav = new ModelAndView("backend/Obras/listar"); 
	mav.addObject("Listaobras", ListarObrasTodas);
	mav.addObject("totalItems", totalItems);
	mav.addObject("totalpages", totalpages);
	mav.addObject("currentPage", currentPage);
	mav.addObject("busqueda",busqueda);
	return mav;
}
	
}
