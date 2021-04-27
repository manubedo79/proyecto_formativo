 package springbootartacademy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;

import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.Categorias;
import springbootartacademy.models.entity.Clientes;
import springbootartacademy.models.entity.Imagenes;
import springbootartacademy.models.entity.Municipios;
import springbootartacademy.models.entity.Obras;
import springbootartacademy.models.service.ICaracteristicasService;
import springbootartacademy.models.service.ICategoriasService;
import springbootartacademy.models.service.IFileService;
import springbootartacademy.models.service.IImagenesService;
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
	private IImagenesService servicioimagenes;
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
	public String guardarObra(@Valid @ModelAttribute("obra") Obras obra,@Valid @ModelAttribute("caracteristica")Caracteristicas caracteristica,Imagenes imagenes, BindingResult result, @RequestParam("imagenobra") MultipartFile multipart , RedirectAttributes flash) throws IOException {
		if(result.hasErrors()) {
			return "backend/Obras/formulario";
		}
		Obras obracaracter = null;
		if(!multipart.isEmpty()) {
			if(imagenes.getId() !=null && imagenes.getId() > 0 && imagenes.getImagen() != null && imagenes.getImagen().length()>0) {
				ifileser.eliminar(imagenes.getImagen());
			}
			String uniconombre = null;
			try {
	            uniconombre = ifileser.copiar(multipart);
	        } catch (Exception e) {
	            //TODO: handle exception
	            e.printStackTrace();
	        }
			
			servicioobras.guardarObra(obra);
			obracaracter = servicioobras.findbyId(obra.getId());
			imagenes.setObras(obracaracter);
			imagenes.setRutaimagen(uniconombre);
			
		}
		
		
		String mensaje = (obra.getId() != null) ? "Se edito de forma correcta la obra" : "Se guardo de forma correcta la obra";
		caracteristica.setObras(obracaracter);
		servicioimagenes.guardarimagenbd(imagenes);
		
		
		
	
		serviciocaracteristica.guardarCaracteristica(caracteristica);
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/listarCategorias";
	}
}
