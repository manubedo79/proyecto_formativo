package springbootartacademy.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;

import springbootartacademy.models.entity.Categorias;
import springbootartacademy.models.entity.Obras;
import springbootartacademy.models.service.ICategoriasService;
import springbootartacademy.models.service.IFileService;
import springbootartacademy.models.service.IObrasService;

@Controller
@RequestMapping("categoria")
public class CategoriasController {

	@Autowired
	private ICategoriasService cateservice;
	@Autowired
	private IObrasService obrasService;
	@Autowired
	private IFileService ifileser;
	//Listar y buscar una categoria
	@GetMapping("/listar")
	public ModelAndView ListaCategoriasTodos() {
		String busqueda = null;
		return listBypage(1,busqueda);
	}
	//Ruta para paginar las categorias
	@GetMapping("/pagina/{pageNumber}")
	public ModelAndView listBypage(@PathVariable("pageNumber") int currentPage, @Param("busqueda")String busqueda) {
		Page<Categorias> page = cateservice.ListarCategoriasTodas(currentPage,busqueda);
		long totalItems = page.getTotalElements();
		int totalpages = page.getTotalPages();

		List<Categorias> listaCategorias = page.getContent();

		ModelAndView mav = new ModelAndView("backend/Categorias/listar");
		mav.addObject("ListaCate", listaCategorias);
		mav.addObject("totalItems", totalItems);
		mav.addObject("totalpages", totalpages);
		mav.addObject("currentPage", currentPage);
		return mav;
	}
	//Ruta para mostrar el detalle de una categoria
	@GetMapping("/ver/{id}")
	public ModelAndView detallacliente(@PathVariable("id") Long id) {
		Categorias cate = cateservice.findbyIdCategoria(id);
		List <Obras> obr = obrasService.findAllObras();
		ModelAndView mav = new ModelAndView("frontend/categorias/verCategoria");
		mav.addObject("categoria", cate);
		mav.addObject("obras", obr);
		return mav;
	}
	//Ruta para mostrar el formulario para agregar una categoria
	@GetMapping("/formulario")
	public String formularioCategoria(Model model) {
		model.addAttribute("categoria", new Categorias());
		return "backend/Categorias/formulario";
	}
	//Ruta para guarar una categoria en la base de datos
	@PostMapping("/guardar")
	public String guardarCategoria(@ModelAttribute("categoria")Categorias categoria, BindingResult result, RedirectAttributes flash) {
		String mensaje = (categoria.getId() != null) ? "Se edito de forma correcta la categoria" : "Se guardo de forma correcta la categoria";
		cateservice.guardarCategorias(categoria);		
		flash.addFlashAttribute("info", mensaje);
		return "redirect:/categoria/listar";
	}
	@GetMapping("/editar/{id}")
	public String editarcategoria(@PathVariable(name="id")Long id, Model model) {
		Categorias categorias = cateservice.findbyIdCategoria(id);
		if(categorias==null) {
			return "redirect:/listarCategorias";
		}
		model.addAttribute("categoria", categorias);
		return "backend/Categorias/formulario";
	}
	@GetMapping("/exportar")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=categorias_" + currentDateTime +".pdf";
		
		response.setHeader(headerKey, headerValue);
		
		List<Categorias> listacategorias = cateservice.findAllUsers();
		
		CategoriasPDFExporter exporter = new CategoriasPDFExporter(listacategorias);
		exporter.export(response);
		
	}
	@GetMapping("/categoriastodas")
	public ModelAndView todascategorias() {
		return categoriastodaspagina(1);
	}
	@GetMapping("/paginacategoriastodas/{numeropagina}")
	public ModelAndView categoriastodaspagina(@PathVariable("numeropagina") int currentPage) {
		Page<Categorias> page = cateservice.ListarCategoriasTodas(currentPage,null);
		long totalItems = page.getTotalElements();
		int totalpages = page.getTotalPages();
		List<Categorias> listaCategorias = page.getContent();
		ModelAndView mav = new ModelAndView("frontend/Categorias/categoriastodas");
		mav.addObject("ListaCate", listaCategorias);
		mav.addObject("totalItems", totalItems);
		mav.addObject("totalpages", totalpages);
		mav.addObject("currentPage", currentPage);
		return mav;
	}

	

	
	

	
	
	

	

	
}


