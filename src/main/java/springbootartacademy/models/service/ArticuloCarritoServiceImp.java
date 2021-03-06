package springbootartacademy.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springbootartacademy.models.dao.IArticuloCarritoDao;
import springbootartacademy.models.entity.ArticuloCarrito;
import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.CarritoCompras;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.entity.Ventas;
@Service
public class ArticuloCarritoServiceImp implements IArticuloCarritoService{
	@Autowired
	private IArticuloCarritoDao articulodao;
	@Override
	public CarritoCompras articuloCarritosVentaNull(Usuarios usuarios) {
		// TODO Auto-generated method stub
		return new CarritoCompras(articulodao.findByUsuariosVentaNull(usuarios));
	}
	@Override
	public CarritoCompras articuloCarritos(Usuarios usuarios) {
		// TODO Auto-generated method stub
		return new CarritoCompras(articulodao.findByUsuariosAndVentasIsNull(usuarios));
	}
	@Override
	public List<ArticuloCarrito> CarritosVenta(Long id){
		// TODO Auto-generated method stub
		return  articulodao.findByVentaId(id);
	}

	@Override
	public ArticuloCarrito guardarcarrito(Integer cantidad,Caracteristicas carac,Usuarios usu) {
		CarritoCompras carrito = this.articuloCarritos(usu);
		ArticuloCarrito artcarrito = carrito.buscarArticuloCarritoByCaracteristicas(carac.getId());
		if(artcarrito != null)
		{		
			
			artcarrito.agregar_cantidad(cantidad);
			artcarrito.setCaracteristicas(carac);
			artcarrito = articulodao.save(artcarrito);
			
		}
		else
		{
			artcarrito = new ArticuloCarrito();
			artcarrito.setUsuarios(usu); 
			
			artcarrito.setCantidad(cantidad); 
			artcarrito.setCaracteristicas(carac); 
			artcarrito = articulodao.save(artcarrito);
		}
		return artcarrito;
	}
	
	@Override
	public void actualizarArticuloCarrito(ArticuloCarrito articuloCarrito, Integer Cantidad) {
		if(articuloCarrito.getCaracteristicas().validar_cantidad(Cantidad)) {
			articuloCarrito.setCantidad(Cantidad);
			articulodao.save(articuloCarrito);
		}
		
		
	}
	@Override
	public ArticuloCarrito encontrarCarritoId(Long id) {
		return articulodao.findById(id).orElse(null);
	}
	@Override
	public Long contarCarritos(Usuarios usuarios) {
		// TODO Auto-generated method stub
		return articulodao.contarCarritos(usuarios);
	}
	@Override
	public void eliminarCarrito(Long id) {
		articulodao.deleteById(id);
		
	}
	@Override
	public List<ArticuloCarrito> ObrasVenta(Ventas venta){
		return  articulodao.findByVentas(venta);
	}

}
