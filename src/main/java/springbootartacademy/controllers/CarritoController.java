package springbootartacademy.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import springbootartacademy.models.entity.ArticuloCarrito;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.service.IArticuloCarritoService;
import springbootartacademy.models.service.IUsuariosService;

@Controller
public class CarritoController {
@Autowired
private IArticuloCarritoService carritoser;
@Autowired
private IUsuariosService usuarioser;

@GetMapping("/carrito")
public String carrito(Model model, Authentication authentication) {
	Usuarios usuarios = (Usuarios) authentication.getPrincipal();
	List<ArticuloCarrito> carritos = carritoser.articuloCarritos(usuarios);
	model.addAttribute("carritos", carritos);
	return "frontend/carrito/carrito";
}
}
