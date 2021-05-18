package springbootartacademy.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springbootartacademy.models.dao.IArticuloCarritoDao;
import springbootartacademy.models.entity.ArticuloCarrito;
import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.CarritoCompras;
import springbootartacademy.models.entity.Usuarios;
@Service
public class ArticuloCarritoServiceImp implements IArticuloCarritoService{
	@Autowired
	private IArticuloCarritoDao articulodao;
	
	@Override
	public CarritoCompras articuloCarritos(Usuarios usuarios) {
		// TODO Auto-generated method stub
		return new CarritoCompras(articulodao.findByUsuarios(usuarios));
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
	public Integer validarsuma_cantidad(Caracteristicas caracteristicas) {
		
		return articulodao.validarsuma_cantidad(caracteristicas);
	}
	

}
