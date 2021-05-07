package springbootartacademy.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootartacademy.models.entity.ArticuloCarrito;
import springbootartacademy.models.entity.Usuarios;

public interface IArticuloCarritoDao extends JpaRepository<ArticuloCarrito,Long> {

public List<ArticuloCarrito> findByUsuarios(Usuarios usuarios);
}
