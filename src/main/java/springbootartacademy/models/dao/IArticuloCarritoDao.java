package springbootartacademy.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import springbootartacademy.models.entity.ArticuloCarrito;
import springbootartacademy.models.entity.Usuarios;

public interface IArticuloCarritoDao extends JpaRepository<ArticuloCarrito,Long> {
	@Query("SELECT car FROM ArticuloCarrito car WHERE car.usuarios=?1 and car.ventas=null")	
	public List<ArticuloCarrito> findByUsuariosVentaNull(Usuarios usuarios);
public List<ArticuloCarrito> findByUsuariosAndVentasIsNull(Usuarios usuarios);
int countDistinctByUsuariosAndVentasIsNull(Usuarios usuarios);
@Query("select count(DISTINCT car) FROM ArticuloCarrito car Where car.usuarios=?1")
public Long contarCarritos(Usuarios usuarios);
}
