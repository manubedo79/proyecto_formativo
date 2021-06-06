package springbootartacademy.controllers;

import java.io.UnsupportedEncodingException;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springbootartacademy.models.dao.IDepartamentosDao;
import springbootartacademy.models.dao.IMunicipiosDao;
import springbootartacademy.models.entity.ArticuloCarrito;
import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.CarritoCompras;
import springbootartacademy.models.entity.Municipios;
import springbootartacademy.models.entity.Obras;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.entity.Ventas;
import springbootartacademy.models.service.IArticuloCarritoService;
import springbootartacademy.models.service.ICaracteristicasService;
import springbootartacademy.models.service.IObrasService;

import springbootartacademy.models.service.IVentasService;
import springbootartacademy.utils.Utilidad;

@Controller
public class CarritoController {
	// Inyecciones de dependencias
	@Autowired
	private IArticuloCarritoService carritoser;
	@Autowired
	private ICaracteristicasService caracser;
	@Autowired
	private IObrasService obraser;
	@Autowired
	private IVentasService ventasser;
	@Autowired
	private IMunicipiosDao munidao;
	@Autowired
	private IDepartamentosDao depadao;
	
	// Sirve para mostrar mensaje por consola
	Logger logger = LoggerFactory.getLogger(CarritoController.class);
	//Ruta para agregar un item al carrito de compras
	@PostMapping("/agregaritem/carrito")
	public String agregacarrito(Authentication authentication, RedirectAttributes flash, Obras obra, Model model,
			ArticuloCarrito art, @RequestParam("caracteristica") Long id, @RequestParam("cantidad") String cantidad) {
		Caracteristicas carac = caracser.findbyId(id);
		Obras obras = obraser.findbyId(carac.getObras().getId());
		Integer totalcantidad = 0;
		Usuarios usuario = (Usuarios) authentication.getPrincipal();
		CarritoCompras carritos = carritoser.articuloCarritos(usuario);

		for (ArticuloCarrito compras : carritos.getCarritoitems()) {
			totalcantidad += (Integer.parseInt(cantidad) + compras.getCantidad());
			if (totalcantidad > carac.getStock()) {
				flash.addFlashAttribute("error", true);
				return "redirect:/obra/detalle/" + obras.getId();
			}
		}

		carritoser.guardarcarrito(Integer.parseInt(cantidad), carac, usuario);

		return "redirect:/carrito";
	}
	//Ruta para mostrar todos los carritos del usuario
	@GetMapping("/carrito")
	public String carrito(Model model, Authentication authentication) {
		Usuarios usuarios = (Usuarios) authentication.getPrincipal();
		CarritoCompras carritos = carritoser.articuloCarritos(usuarios);
		model.addAttribute("carritos", carritos.getCarritoitems());
		model.addAttribute("total", carritos.total());
		return "frontend/carrito/carrito";
	}
	@PostMapping("/actualizar/cantidad")
	public String actualizarCantidad(@RequestParam(name="id") Long id, @RequestParam(name="cantidad") Integer cantidad, RedirectAttributes flash) {
		ArticuloCarrito articuloCarrito = carritoser.encontrarCarritoId(id);
		if(articuloCarrito.actualizarCantidad(cantidad)) {
			carritoser.actualizarArticuloCarrito(articuloCarrito, cantidad);
			flash.addFlashAttribute("info", "se actualizó de forma correcta");
		}
		else {
			flash.addFlashAttribute("error", "La cantidad sobrepasa el stock");
			return "redirect:/carrito";
		}
		return "redirect:/carrito";
	}
	@GetMapping("/validarcantidad")
	public String validaCantidad(RedirectAttributes flash,@RequestParam(name="total",required=false) String totalventa ,@RequestParam(name="opcion",required=false) String opcion ,@RequestParam(name="direccionnuevo",required=false) String direccionnuevo  ,@RequestParam(name="municipionuevo",required=false) String municipionuevo,Authentication authentication,Model model) {
		Usuarios usuarios = (Usuarios) authentication.getPrincipal();
		CarritoCompras carritos = carritoser.articuloCarritos(usuarios);
		Integer totalcantidad = 0;
		for (ArticuloCarrito compras : carritos.getCarritoitems()) {
			totalcantidad =  compras.getCantidad();
			if (totalcantidad > compras.getCaracteristicas().getStock()) {
				flash.addFlashAttribute("error", "El producto con tamaño "+compras.getCaracteristicas().getSize()+" no cuenta con stock");
				
				return "redirect:/carrito";
			}
		}
		return "redirect:/realizar_pago";
	}
	@GetMapping(value="/obtener_tarifa/{id}", produces= {"application/json"})
	public @ResponseBody Municipios obtener_tarifa(@PathVariable("id")Long id) {
	return  munidao.findById(id).orElse(null);
	}	
	@GetMapping("/realizar_pago")
	public String realizapago(Authentication authentication,Model model) {
		Usuarios usuarios = (Usuarios) authentication.getPrincipal();
		CarritoCompras carritos = carritoser.articuloCarritos(usuarios);
		model.addAttribute("usuario", usuarios);
		model.addAttribute("total", carritos.total() + usuarios.getClientes().getMunicipios().getTarifaenviomunicipio());
		model.addAttribute("totalsint", carritos.total());
		model.addAttribute("carritos", carritos.getCarritoitems());
		model.addAttribute("municipios", munidao.findAll());
		model.addAttribute("conteo", carritoser.contarCarritos(usuarios));
		model.addAttribute("departamentos", depadao.findAll());
		return "frontend/carrito/realizar";
	}
	@GetMapping("/ordenes/mensaje")
	public String mensaje() {
		return "frontend/carrito/mensaje";
	}
	@PostMapping("/generando_venta")
	public String generaventa(RedirectAttributes flash,@RequestParam(name="total",required=false) String totalventa ,HttpServletRequest request ,@RequestParam(name="opcion",required=false) String opcion ,@RequestParam(name="direccionnuevo",required=false) String direccionnuevo  ,@RequestParam(name="municipionuevo",required=false) String municipionuevo,Authentication authentication,Model model) throws UnsupportedEncodingException, MessagingException {
		Usuarios usuarios = (Usuarios) authentication.getPrincipal();
		CarritoCompras carritos = carritoser.articuloCarritosVentaNull(usuarios);
		Integer totalcantidad = 0;
		for (ArticuloCarrito compras : carritos.getCarritoitems()) {
			totalcantidad =  compras.getCantidad();
			if (totalcantidad > compras.getCaracteristicas().getStock()) {
				flash.addFlashAttribute("errorP", true);
				flash.addFlashAttribute("errorN", compras.getCaracteristicas().getSize());				
				return "redirect:/carrito";
			}
		}
		Ventas venta = new Ventas();
		if(opcion.equals("si")) 
		{
			venta.setDireccionentrega(usuarios.getClientes().getDireccion());
			venta.setMunicipios(usuarios.getClientes().getMunicipios());
			venta.setTotalventa(Float.parseFloat(totalventa));
		}		
		if(opcion.equals("no")) 
		{
			Municipios mun = munidao.findById(Long.parseLong(municipionuevo)).orElse(null);
			venta.setDireccionentrega(direccionnuevo);
			venta.setTotalventa((Float.parseFloat(totalventa)+mun.getTarifaenviomunicipio())-usuarios.getClientes().getMunicipios().getTarifaenviomunicipio());
			venta.setMunicipios(mun);
		}
		venta.setUsuarios(usuarios);
		String siteURL = Utilidad.getSitioUrl(request);

		ventasser.enviarFacturaCorreo(siteURL,usuarios, venta, carritos);
		for (ArticuloCarrito compras : carritos.getCarritoitems()) {
			compras.getCaracteristicas().decrementar_cantidad(compras.getCantidad());
			compras.setVentas(venta);
		}
		ventasser.saveVenta(venta);
		return "redirect:/ordenes/mensaje";
	}
	@GetMapping("/eliminar/carrito/{id}")
	public String eliminar(@PathVariable Long id) {
	carritoser.eliminarCarrito(id);
		return "redirect:/carrito";
	}

}
