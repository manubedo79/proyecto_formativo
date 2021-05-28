package springbootartacademy.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import springbootartacademy.models.entity.ArticuloCarrito;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.entity.Ventas;

public interface IArticuloCarritoDao extends JpaRepository<ArticuloCarrito,Long> {

	@Query("SELECT car FROM ArticuloCarrito car WHERE car.usuarios=?1 and car.ventas=null")	
	public List<ArticuloCarrito> findByUsuariosVentaNull(Usuarios usuarios);
	@Query("SELECT car FROM ArticuloCarrito car WHERE car.usuarios=?1 and car.ventas=?2")	
	public List<ArticuloCarrito> findByUsuariosVenta(Usuarios usuarios,Ventas venta);
	@Query("select count(DISTINCT car) FROM ArticuloCarrito car Where car.usuarios=?1 and car.ventas=null")
	public Long contarCarritosVentaNull(Usuarios usuarios);
	@Query("select count(DISTINCT car) FROM ArticuloCarrito car Where car.usuarios=?1 and car.ventas=?2")
	public Long contarCarritosVenta(Usuarios usuarios,Ventas venta);
	public List<ArticuloCarrito> findByUsuarios(Usuarios usuarios);
	
}
