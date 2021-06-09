package springbootartacademy.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import springbootartacademy.models.entity.Estados;
import springbootartacademy.models.entity.Obras;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.entity.Ventas;

@Repository
public interface IVentasDao extends PagingAndSortingRepository<Ventas,Long>{
@Query("SELECT sum(ven.totalventa) FROM Ventas ven")
public Float SumaTotalVentas();
@Query("SELECT o FROM Ventas o WHERE CONCAT(o.id,o.usuarios.clientes.nombre,o.usuarios.clientes.telefono) LIKE %?1%")
public Page<Ventas> findAll(String busqueda,Pageable pageable);
@Query("select function('date_format', v.fechaventa, '%d, %m, %Y') as fecha,sum(v.totalventa) as total from Ventas v group by fecha")
public Iterable<Ventas> findByFechas();

public List<Ventas> findByusuarios(Usuarios usuarios);
@Transactional
@Modifying
@Query("update Ventas v set v.estado=?1  where v.id=?2")
public void actualizaestado(Estados estados,Long id);

}
