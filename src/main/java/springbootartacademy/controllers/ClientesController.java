package springbootartacademy.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;

import springbootartacademy.models.dao.IDepartamentosDao;
import springbootartacademy.models.dao.IMunicipiosDao;
import springbootartacademy.models.entity.Clientes;
import springbootartacademy.models.entity.Municipios;
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
	@Autowired 
	private IDepartamentosDao depadao;
	

	@GetMapping("/listaClientes")
	public ModelAndView ListaClientesTodos() {
		return listBypage(1);
	}

	@GetMapping("/pageCliente/{pageNumber}")
	public ModelAndView listBypage(@PathVariable("pageNumber") int currentPage) {
		Page<Clientes> page = CliService.1(currentPage);
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
	@GetMapping("/detallaCliente/{pageNumber}")
	public ModelAndView detallacliente(@PathVariable("pageNumber") Long id) {
		Clientes cliente = CliService.findById(id);
		ModelAndView mav = new ModelAndView("backend/Clientes/detalla");
		mav.addObject("Clientes", cliente);
		return mav;
	}
	@GetMapping("/datospersonales/{idusuario}")
	public String datospersonales(@PathVariable(value="idusuario") Long idusuario, Model model) {
		Usuarios usuarios = iususer.findById(idusuario);
		Clientes clientes = new Clientes();
		clientes.setUsuarios(usuarios);
		model.addAttribute("municipios", munidao.findAll());
		model.addAttribute("departamentos", depadao.findAll());
		model.addAttribute("cliente", clientes);
		return "frontend/cliente/datospersonales";
	}
	
	@PostMapping("/terminarregistro")
	public String terminarRegistro(Clientes clientes,BindingResult result, RedirectAttributes flash, Model model) {		
			CliService.saveClientes(clientes);
		return "redirect:/mensajeRegistro";
	}
	@GetMapping("/clien/export")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=clientes_" + currentDateTime +".pdf";
		
		response.setHeader(headerKey, headerValue);
		
		List<Clientes> listaClientes = CliService.findAllClientes();
		
		ClientesPDFExporter exporter = new ClientesPDFExporter(listaClientes);
		exporter.export(response);
		
	}

	@GetMapping("/editarcliente/{id}")
	public String editarcliente(@PathVariable(name="id") Long id,Model model) {
		Clientes clientes = CliService.findById(id);
		List<Usuarios> listausuarios = iususer.findAllUsers();
		List<Municipios> listamunicipios = munidao.findAll();
		
		if(clientes==null) {
			return "redirect:/listaCliente";
		}
		model.addAttribute("cliente", clientes);
		model.addAttribute("usuarios", listausuarios);
		model.addAttribute("municipios", listamunicipios);
		return "backend/Clientes/formulario";
	}
	@PostMapping("/guardarcliente")
	public String guardarCliente(@Valid @ModelAttribute("cliente") Clientes clientes, BindingResult result, RedirectAttributes flash) {
		if(result.hasErrors()) 
		{
			return "backend/Clientes/formulario";
		}
		CliService.saveClientes(clientes);
		flash.addFlashAttribute("success", "Se edito de forma correcta el cliente");
		return "redirect:/listaClientes";	
	}
	
}
	