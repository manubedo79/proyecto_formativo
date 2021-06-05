package springbootartacademy.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import springbootartacademy.models.entity.Obras;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.entity.Ventas;

@Repository
public interface IVentasDao extends PagingAndSortingRepository<Ventas,Long>{
@Query("SELECT sum(ven.totalventa) FROM Ventas ven")
public Float SumaTotalVentas();
@Query("SELECT o FROM Ventas o WHERE CONCAT(o.id,o.usuarios.clientes.nombre) LIKE %?1%")
public Page<Ventas> findAll(String busqueda,Pageable pageable);
@Query("select DAY(v.fechaventa) as mes, SUM(v.totalventa) from Ventas v where v.estado=2 GROUP BY mes order by v.id ")
public Iterable<Ventas> findByFechas();

public List<Ventas> findByusuarios(Usuarios usuarios);

}
