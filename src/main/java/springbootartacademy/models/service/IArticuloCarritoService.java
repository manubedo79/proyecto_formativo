package springbootartacademy.models.service;

import java.util.List;

import springbootartacademy.models.entity.ArticuloCarrito;
import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.CarritoCompras;
import springbootartacademy.models.entity.Usuarios;

public interface IArticuloCarritoService {
	public CarritoCompras articuloCarritos(Usuarios usuarios);
	public ArticuloCarrito guardarcarrito(Integer cantidad, Caracteristicas carac, Usuarios usu);
	public Long contarCarritos(Usuarios usuarios);
	public void actualizarArticuloCarrito(ArticuloCarrito articuloCarrito,Integer Cantidad);
	public ArticuloCarrito encontrarCarritoId(Long id);
	public CarritoCompras articuloCarritosVentaNull(Usuarios usuarios);
	public List<ArticuloCarrito> CarritosVenta(Long id);
	
}
