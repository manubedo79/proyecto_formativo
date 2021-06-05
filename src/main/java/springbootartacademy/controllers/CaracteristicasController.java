package springbootartacademy.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import springbootartacademy.models.dao.ICaracteristicasDao;
import springbootartacademy.models.entity.Caracteristicas;

@Controller
public class CaracteristicasController {
	@Autowired
	private ICaracteristicasDao icaradao;
@GetMapping("/cara/eliminar/{id}")
public String eliminar(@PathVariable Long id) {
	Caracteristicas caracteristicas=icaradao.findById(id).orElse(null);
	icaradao.deleteById(id);
	return "redirect:/obra/editar/"+caracteristicas.getObras().getId();
	}
@PostMapping("/cara/guardar")
public String guardar(Caracteristicas caracteristicas) {
	icaradao.save(caracteristicas);
	return "redirect:/obra/editar/"+caracteristicas.getObras().getId();
}
@PostMapping("/cara/editar")
public String editar(HttpServletRequest request) {
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
	return "redirect:/obra/editar/"+caracteristicas.getObras().getId();
}
}
