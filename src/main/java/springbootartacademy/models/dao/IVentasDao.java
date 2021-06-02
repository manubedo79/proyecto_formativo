package springbootartacademy.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import springbootartacademy.models.entity.Estados;
import springbootartacademy.models.entity.Ventas;

@Repository
public interface IVentasDao extends PagingAndSortingRepository<Ventas,Long>{
	@Query("update Ventas v set v.estado=?1 where v.id=?2")
	public void actualizaEstado(Estados estado,Long id);

}
