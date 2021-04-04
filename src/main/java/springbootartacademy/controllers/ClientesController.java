package springbootartacademy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import springbootartacademy.models.dao.IMunicipiosDao;
import springbootartacademy.models.entity.Clientes;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.service.IClientesService;
import springbootartacademy.models.service.IUsuariosService;

@Controller
public class ClientesController {

	@Autowired
	private IClientesService CliService;
	@Autowired
	private IMunicipiosDao munidao;
	@Autowired
	private IUsuariosService iususer;

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
	@GetMapping("/datospersonales/{idusuario}")
	public String datospersonales(@PathVariable(value="idusuario") Long idusuario, Model model) {
		Usuarios usuarios = iususer.findById(idusuario);
		Clientes clientes = new Clientes();
		clientes.setUsuarios(usuarios);
		model.addAttribute("municipios", munidao.findAll());
		model.addAttribute("cliente", clientes);
		return "frontend/cliente/datospersonales";
	}
	
	@PostMapping("/terminarregistro")
	public String terminarRegistro(Clientes clientes) {
	CliService.saveClientes(clientes);
		return "";
	}
	
}
