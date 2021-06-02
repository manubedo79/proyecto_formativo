package springbootartacademy.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.entity.Ventas;

@Repository
public interface IVentasDao extends PagingAndSortingRepository<Ventas,Long>{
@Query("SELECT sum(ven.totalventa) FROM Ventas ven")
public Float SumaTotalVentas();

public List<Ventas> findByusuarios(Usuarios usuarios);
}
