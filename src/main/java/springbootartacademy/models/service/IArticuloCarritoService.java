package springbootartacademy.models.service;

import java.util.List;

import springbootartacademy.models.entity.ArticuloCarrito;
import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.CarritoCompras;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.entity.Ventas;

public interface IArticuloCarritoService {
	public ArticuloCarrito guardarcarrito(Integer cantidad, Caracteristicas carac, Usuarios usu);
	public void actualizarArticuloCarrito(ArticuloCarrito articuloCarrito,Integer Cantidad);
	public ArticuloCarrito encontrarCarritoId(Long id);
	public Long contarCarritosVentaNull(Usuarios usuarios);
	public Long contarCarritosVenta(Usuarios usuarios,Ventas venta);
	public CarritoCompras articuloCarritosVentaNull(Usuarios usuarios);
	public CarritoCompras articuloCarritosVenta(Usuarios usuarios,Ventas venta);
	
}
