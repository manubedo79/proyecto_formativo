package springbootartacademy.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springbootartacademy.models.dao.ICaracteristicasDao;
import springbootartacademy.models.entity.Caracteristicas;

@Controller
public class CaracteristicasController {
	@Autowired
	private ICaracteristicasDao icaradao;
@GetMapping("/cara/eliminar/{id}")
public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
	Caracteristicas caracteristicas=icaradao.findById(id).orElse(null);
	icaradao.deleteById(id);
	flash.addFlashAttribute("info", "Se elimino la caracteristica "+caracteristicas.getSize()+ " de forma correcta");
	return "redirect:/obra/editar/"+caracteristicas.getObras().getId();
	}
@PostMapping("/cara/guardar")
public String guardar(Caracteristicas caracteristicas, RedirectAttributes flash) {
	icaradao.save(caracteristicas);
	flash.addFlashAttribute("info", "Se agrego la caracteristica "+caracteristicas.getSize()+ " de forma correcta");
	return "redirect:/obra/editar/"+caracteristicas.getObras().getId();
}
@PostMapping("/cara/editar")
public String editar(HttpServletRequest request,  RedirectAttributes flash) {
	Caracteristicas caracteristicas=null;
	String id[]= request.getParameterValues("id[]");
	String size[]=request.getParameterValues("size[]");
	String cantidad[]=request.getParameterValues("stock[]");
	String precio[]=request.getParameterValues("precio[]");
	for(int i=0; i<id.length;i++) {
		caracteristicas=icaradao.findById(Long.parseLong(id[i])).orElse(null);
		caracteristicas.setSize(size[i]);
		caracteristicas.setStock(Integer.parseInt(cantidad[i]));
		caracteristicas.setPrecio(Float.parseFloat(precio[i]));
	}
	icaradao.save(caracteristicas);
	flash.addFlashAttribute("info", "Se edito la caracteristica "+caracteristicas.getSize()+ " de forma correcta");
	return "redirect:/obra/editar/"+caracteristicas.getObras().getId();
}
}
