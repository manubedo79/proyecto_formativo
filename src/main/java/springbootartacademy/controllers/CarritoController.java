package springbootartacademy.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springbootartacademy.models.entity.ArticuloCarrito;
import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.CarritoCompras;
import springbootartacademy.models.entity.Obras;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.service.IArticuloCarritoService;
import springbootartacademy.models.service.ICaracteristicasService;
import springbootartacademy.models.service.IObrasService;
import springbootartacademy.models.service.IUsuariosService;

@Controller
public class CarritoController {
	@Autowired
	private IArticuloCarritoService carritoser;
	@Autowired
	private ICaracteristicasService caracser;
	@Autowired
	private IObrasService obraser;
	@Autowired
	private IUsuariosService usuarioser;

	@GetMapping("/carrito")
	public String carrito(Model model, Authentication authentication) {
		Usuarios usuarios = (Usuarios) authentication.getPrincipal();
		CarritoCompras carritos = carritoser.articuloCarritos(usuarios);
		model.addAttribute("carritos", carritos.getCarritoitems());
		return "frontend/carrito/carrito";
	}
	@PostMapping("/agregar_carrito")
	public String agregacarrito(Authentication authentication,RedirectAttributes flash,Obras obra,Model model, ArticuloCarrito art, @RequestParam("caracteristica") Long id, @RequestParam("cantidad") String cantidad ) {
		Caracteristicas carac = caracser.findbyId(id);
		Obras obras = obraser.findbyId(carac.getObras().getId());
		if(!carac.validar_cantidad(Integer.parseInt(cantidad)))
		{
			flash.addFlashAttribute("errorcanti" ,true);
			return  "redirect:/detalleObra/"+obras.getId();
		}
			Usuarios usuario = (Usuarios) authentication.getPrincipal();
			carritoser.guardarcarrito(Integer.parseInt(cantidad), carac, usuario);
			return "redirect:/carrito";		
	}
}
