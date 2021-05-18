package springbootartacademy.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import springbootartacademy.models.entity.ArticuloCarrito;
import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.Usuarios;

public interface IArticuloCarritoDao extends JpaRepository<ArticuloCarrito,Long> {
@Query("SELECT car.cantidad FROM ArticuloCarrito car WHERE car.caracteristicas=?1")
public Integer validarsuma_cantidad(Caracteristicas caracteristicas);
public List<ArticuloCarrito> findByUsuarios(Usuarios usuarios);
}
