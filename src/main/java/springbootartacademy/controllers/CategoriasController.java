package springbootartacademy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

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
}
