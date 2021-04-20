package springbootartacademy.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;

import springbootartacademy.models.entity.Categorias;
import springbootartacademy.models.entity.Clientes;
import springbootartacademy.models.service.ICategoriasService;

@Controller
public class CategoriasController {

	@Autowired
	private ICategoriasService cateservice;
	
	@GetMapping("/listarCategorias")
	public ModelAndView ListaCategoriasTodos() {
		return listBypage(1);
	}
	
	
	@GetMapping("/pageCategorias/{pageNumber}")
	public ModelAndView listBypage(@PathVariable("pageNumber") int currentPage) {
		Page<Categorias> page = cateservice.ListarCategoriasTodas(currentPage);
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
	@GetMapping("/formulariocategoria")
	public String formularioCategoria(Model model) {
		model.addAttribute("categoria", new Categorias());
		return "backend/Categorias/formulario";
	}
	@PostMapping("/guardarcategoria")
	public String guardarCategoria(Categorias categorias, RedirectAttributes flash) {
		String mensaje = (categorias.getId() != null) ? "Se edito de forma correcta la categoria" : "Se guardo de forma correcta la categoria";
		cateservice.guardarCategorias(categorias);
		
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/listarCategorias";
	}
	@GetMapping("/editarcategoria/{id}")
	public String editarcategoria(@PathVariable(name="id")Long id, Model model) {
		Categorias categorias = cateservice.findbyIdCategoria(id);
		if(categorias==null) {
			return "redirect:/listarCategorias";
		}
		model.addAttribute("categoria", categorias);
		return "backend/Categorias/formulario";
	}
	@GetMapping("/categoria/export")
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
}
