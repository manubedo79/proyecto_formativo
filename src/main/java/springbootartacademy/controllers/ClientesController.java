package springbootartacademy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import springbootartacademy.models.entity.Clientes;
import springbootartacademy.models.service.IClientesService;

@Controller
public class ClientesController {

	@Autowired
	private IClientesService CliService;

	@GetMapping("/listaClientes")
	public ModelAndView ListaClientesTodos() {
		return listBypage(1);
	}

	@GetMapping("/pageCliente/{pageNumber}")
	public ModelAndView listBypage(@PathVariable("pageNumber") int currentPage) {
		Page<Clientes> page = CliService.ListarClientesTodos(currentPage);
		long totalItems = page.getTotalElements();
		int totalpages = page.getTotalPages();

		List<Clientes> listaClientes = page.getContent();

		ModelAndView mav = new ModelAndView("backend/Clientes/listar");
		mav.addObject("ListaClie", listaClientes);
		mav.addObject("totalItems", totalItems);
		mav.addObject("totalpages", totalpages);
		mav.addObject("currentPage", currentPage);
		return mav;
	}
}
