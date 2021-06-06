package springbootartacademy.controllers;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.lowagie.text.DocumentException;

import springbootartacademy.models.dao.IDepartamentosDao;
import springbootartacademy.models.dao.IMunicipiosDao;
import springbootartacademy.models.dao.IRolesDao;
import springbootartacademy.models.entity.Clientes;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.service.IClientesService;
import springbootartacademy.models.service.IUsuariosService;
import springbootartacademy.utils.UsuarioDetailsImp;
import springbootartacademy.utils.Utilidad;

@Controller
@RequestMapping("/usuario")
public class UsuariosController {

	@Autowired
	@Qualifier("UserDetailsService")
	private UsuarioDetailsImp idetailsimp;
	@Autowired
	private IUsuariosService service;
	@Autowired
	private IRolesDao rolesdao;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private IClientesService cliser;
	@Autowired
	private IMunicipiosDao munidao;
	@Autowired
	private IDepartamentosDao idepartadao;

	@GetMapping("/listar")
	public ModelAndView ListaUsuarioTodos() {
		String busqueda = null;
		return listBypage(1, busqueda);
	}

	@GetMapping("/pagina/{pageNumber}")
	public ModelAndView listBypage(@PathVariable("pageNumber") int currentPage, @Param("busqueda") String busqueda) {
		Page<Usuarios> page = service.ListarUsuariosTodos(currentPage, busqueda);
		long totalItems = page.getTotalElements();
		int totalpages = page.getTotalPages();

		List<Usuarios> listaUsuarios = page.getContent();

		ModelAndView mav = new ModelAndView("backend/usuarios/listar");
		mav.addObject("ListaUsu", listaUsuarios);
		mav.addObject("totalItems", totalItems);
		mav.addObject("totalpages", totalpages);
		mav.addObject("currentPage", currentPage);
		mav.addObject("busqueda", busqueda);
		return mav;
	}

	@GetMapping("/formulario")
	public String registro(Model model) {

		model.addAttribute("roles", rolesdao.findAll());
		model.addAttribute("usuario", new Usuarios());
		return "backend/usuarios/formulario";

	}

	@PostMapping("/guardar")
	public String guardarUsuario(@ModelAttribute() Usuarios usuario, RedirectAttributes flash, Model model) {

		flash.addFlashAttribute("success", "Se guardo el usuario correctamente");
		service.saveNewUsuarios(usuario);
		return "redirect:/usuario/listar";
	}

	@GetMapping("/cambiarestado/{id}")
	public String cambiarEstado(@PathVariable(value = "id") Long id) {
		service.cambioEstado(id);
		return "redirect:/usuario/listar";
	}
	@GetMapping("/editar/{id}")
	public String editarusuario(Model model, @PathVariable(name = "id") Long id) {
		Usuarios usuarios = service.findById(id);
		model.addAttribute("roles", rolesdao.findAll());
		model.addAttribute("usuario", usuarios);
		return "backend/usuarios/editar";
	}

	@PostMapping("/editar")
	public String editarnuevoUsuario(Usuarios usuarios, RedirectAttributes flash) {
		service.edituser(usuarios);
		flash.addFlashAttribute("success", "Se edito el usuario correctamente");
		return "redirect:/usuario/listar";
	}

	@GetMapping("/miperfil")
	public String miperfil(Model model, Principal principal) {
		Usuarios usuarios = service.findByCorreo(principal.getName());
		Clientes clientes = cliser.findAllByCorreo(principal.getName());
		model.addAttribute("usuario", usuarios);
		model.addAttribute("cliente", clientes);
		model.addAttribute("municipios", munidao.findAll());
		model.addAttribute("departamentos", idepartadao.findAll());
		return "frontend/cuenta/miperfil";
	}
	@RequestMapping(value = "/actualizar/perfil", method = RequestMethod.POST)
	public String updateUserInfo(@ModelAttribute("usuario") Usuarios usuarios,
			@ModelAttribute("cliente") Clientes clientes, Model model, Principal principal) throws Exception {
		Usuarios currentUser = service.findByCorreo(principal.getName());
		Clientes currentClientes = cliser.findAllByCorreo(principal.getName());
		if (currentUser == null) {
			throw new Exception("User not found");
		}
		if (currentClientes == null) {
			throw new Exception("Cliente not found");
		}

		/* check email already exists */
		Usuarios existingUser = service.findByCorreo(usuarios.getCorreo());
		if (existingUser != null && !existingUser.getId().equals(currentUser.getId())) {
			model.addAttribute("emailExists", "El correo ya existe");
			return "redirect:/usuario/miperfil";
		}

		currentUser.setCorreo(usuarios.getCorreo());
		currentClientes.setNombre(clientes.getNombre());
		currentClientes.setApellido(clientes.getApellido());
		currentClientes.setDireccion(clientes.getDireccion());
		currentClientes.setTelefono(clientes.getTelefono());
		currentClientes.setMunicipios(clientes.getMunicipios());
		service.actualizarPefil(currentUser, currentClientes);
		model.addAttribute("updateSuccess", "Se actualizaron sus datos de usuarios de forma correcta");
		model.addAttribute("usuario", currentUser);
		model.addAttribute("cliente", currentClientes);
		idetailsimp.authenticateUser(currentUser.getCorreo());
		return "redirect:/usuario/miperfil";
	}
	@GetMapping("/exportar")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=usuarios_" + currentDateTime + ".pdf";

		response.setHeader(headerKey, headerValue);

		List<Usuarios> listaUsuarios = service.findAllUsers();

		UserPDFExporter exporter = new UserPDFExporter(listaUsuarios);
		exporter.export(response);

	}
	@GetMapping("/cambio/contra")
	public String editarpassword(Authentication authentication, Model model) {
		Usuarios usuarios = (Usuarios) authentication.getPrincipal();

		model.addAttribute("usuario", usuarios);
		return "frontend/cuenta/editarpassword";
	}

	@PostMapping("/editarpassword/guardar")
	public String guardarpassword(@ModelAttribute("usuario") Usuarios usuarios, Model model, Principal principal)
			throws Exception {
		Usuarios currentUser = service.findByCorreo(principal.getName());
		if (currentUser == null) {
			throw new Exception("User not found");
		}
		String pass = Utilidad.passwordencode().encode(usuarios.getContraseña());
		currentUser.setContraseña(pass);
		service.updatepassword(currentUser);
		model.addAttribute("updateSuccess", true);
		model.addAttribute("usuario", currentUser);
		idetailsimp.authenticateUser(currentUser.getCorreo());
		return "redirect:/miperfil";
	}
	@GetMapping(value="/validar/correo")
	public @ResponseBody String checkEmailUser(@Param("correo") String correo) {

		return service.uniqueemail(correo);
	}
	

}
