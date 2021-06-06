 package springbootartacademy.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;

import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.Categorias;
import springbootartacademy.models.entity.Clientes;
import springbootartacademy.models.entity.Obras;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.service.ICaracteristicasService;
import springbootartacademy.models.service.ICategoriasService;
import springbootartacademy.models.service.IClientesService;
import springbootartacademy.models.service.IFileService;
import springbootartacademy.models.service.IObrasService;
import springbootartacademy.models.service.IUsuariosService;

@Controller
@RequestMapping("/obra")
public class ObrasController {

	@Autowired
	private IObrasService servicioobras;
	@Autowired
	private IObrasService service;
	@Autowired
	private ICategoriasService serviciocategorias;
	@Autowired
	private ICaracteristicasService serviciocaracteristica;
	@Autowired
	private IFileService ifileser;

	
	@GetMapping("/listar")
	public ModelAndView ListaObrasTodos() {
	String busqueda = null;
		return listBypage(1,busqueda);
	}
	
	@GetMapping("/pagina/{pageNumber}")
	public ModelAndView listBypage(@PathVariable("pageNumber")int currentPage, @Param("busqueda")String busqueda) {
		Page<Obras> page = servicioobras.ListarObrasTodas(true,currentPage,busqueda);
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
	
	@GetMapping("/formulario")
	public String formularioCategoria(Model model) {
		List<Categorias> listacate = serviciocategorias.findAll();
		model.addAttribute("obra", new Obras());
		model.addAttribute("caracteristica", new Caracteristicas());
		model.addAttribute("categoria", listacate);
		return "backend/Obras/formulario";
	}
	
	@PostMapping("/guardar")
	public String guardarObra(@ModelAttribute("obra") Obras obra,@ModelAttribute("caracteristica")Caracteristicas caracteristica, 
	BindingResult result, @RequestParam("imagenobra")MultipartFile multipart , @RequestParam("imagenobra2") MultipartFile multipart2 ,
	@RequestParam("imagenobra3") MultipartFile multipart3 , RedirectAttributes flash, HttpServletRequest request) throws IOException {
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
			
		}
		String size[] = request.getParameterValues("size[]");
		String cantidad[] = request.getParameterValues("cantidades[]");
		String precio[] = request.getParameterValues("precio[]");
		for(int i=0; i<size.length;i++) {
			obra.addCaracteristicas(Integer.parseInt(cantidad[i]), Float.parseFloat(precio[i]), size[i]);
		}
		servicioobras.guardarObra(obra);

		flash.addFlashAttribute("info", "Se guardo de forma correcta la obra");
		return "redirect:/obra/listar";
	}
	@GetMapping("/editar/{id}")
	public String editar (@PathVariable(value="id") Long id, Model model) {
		Obras obra=servicioobras.findbyId(id);
		
		model.addAttribute("obra", obra);
		Caracteristicas caracteristicas= new Caracteristicas();
		caracteristicas.setObras(obra);
		model.addAttribute("caracteristica", caracteristicas);
		model.addAttribute("categoria",serviciocategorias.findAll());
		return "backend/Obras/editar";
	}
	
	@PostMapping("/editar")
	public String editarobra(@ModelAttribute("obra") Obras obra,
	BindingResult result, @RequestParam("rutaimagen_principal")MultipartFile multipart , @RequestParam("imagenobra2") MultipartFile multipart2 ,
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
		}else if(!multipart2.isEmpty()&& !multipart3.isEmpty()) {
			Obras verifica = servicioobras.findbyId(obra.getId());
			obra.setRutaimagen_principal(verifica.getRutaimagen_principal());
			if(obra.getImagen2() != null && obra.getImagen2().length()>0 
			&& obra.getImagen3() != null && obra.getImagen3().length()>0) {
				ifileser.eliminar(obra.getImagen2());
				ifileser.eliminar(obra.getImagen3());
				String  nombreruta2 = null,nombreruta3 = null;
				try 
				{
					nombreruta2 = ifileser.copiar(multipart2);
					nombreruta3 = ifileser.copiar(multipart3);
					
				} catch (Exception e) {e.printStackTrace();}
				
				obra.setRutaimagen_2(nombreruta2);
				obra.setRutaimagen_3(nombreruta3);
			}
		}else {
			Obras verifica = servicioobras.findbyId(obra.getId());
			obra.setRutaimagen_principal(verifica.getRutaimagen_principal());
			obra.setRutaimagen_2(verifica.getRutaimagen_2());
			obra.setRutaimagen_3(verifica.getRutaimagen_3());
		}
		
		servicioobras.guardarObra(obra);
		
	
		flash.addFlashAttribute("info", "Se edito de forma correcta la obra");
		return "redirect:/obra/listar";
	}
	@GetMapping("/cambiarestado/{id}")
	public String cambiarEstado(@PathVariable(value="id") Long id, RedirectAttributes flash) {
       Obras obras = service.findbyId(id);
       String mensaje = (obras.isEstado()==false)?"Activo":"Inactivo";
    	   servicioobras.cambioEstado(id);
   		flash.addFlashAttribute("info", "Se actualizo el estado de la obra a "+mensaje);
       
		
		return "redirect:/obra/listar";
	}
	
	
	
	
	@GetMapping("/detalle/{id}")
	public ModelAndView detalleobras(@PathVariable("id") Long id) {
		Obras obras = servicioobras.findbyId(id);
		Categorias obracate = obras.getCategoria();
		List<Obras> obrasrelacionadas = servicioobras.ObrasRelacionadas(obracate.getId(),obras.getId());
		ModelAndView mav = new ModelAndView("frontend/carrito/detalle");
		mav.addObject("caracteristicas", serviciocaracteristica.findAllCaracteristocas(id));
		mav.addObject("Obras", obras);
		mav.addObject("ObrasRel", obrasrelacionadas);
		return mav;
	}
	@GetMapping("/exportar")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Obras_" + currentDateTime +".pdf";
		
		response.setHeader(headerKey, headerValue);
		
		List<Obras> listaObras = service.findAllObras();
		
		ObrasPDFExporter exporter = new ObrasPDFExporter(listaObras);
		exporter.export(response);
		
	}


@GetMapping(value="/obtener_precio_cantidad/{idcaracteristica}", produces= {"application/json"})
public @ResponseBody Caracteristicas obtener_precio_cantidad(@PathVariable("idcaracteristica")Long idcaracteristica) {
return  serviciocaracteristica.listarcaracteristicas_obras(idcaracteristica);
}	

@GetMapping(value="/validar/nombre")
public @ResponseBody String checkNameObra(@Param("nombre") String nombre) {

	return service.findByNombre(nombre);
}
@GetMapping("/detall/{id}")
public String detall(@PathVariable Long id, Model model) {
	Obras obras = service.findbyId(id);
	model.addAttribute("obra", obras);
	return "backend/Obras/detalle";
}
}
