package springbootartacademy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.entity.Ventas;
import springbootartacademy.models.service.IVentasService;

@Controller
public class VentasController {
	@Autowired
	private IVentasService iventaser;
@GetMapping("/ordenes")
public String ordenes(Authentication authentication,Model model) {
	Usuarios usuarios = (Usuarios) authentication.getPrincipal();
	model.addAttribute("ventas", iventaser.listarVentasUsuarios(usuarios));
	return "frontend/ordenes/ordenes";
}
@GetMapping("/ordenes/detalle/{id}")
public String detalle(@PathVariable("id") Long id, Model model) {
	Ventas ventas = iventaser.findByIdVenta(id);
	model.addAttribute("venta", ventas);
	return "frontend/ordenes/detalle";
}

}
