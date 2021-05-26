  
package springbootartacademy.models.dao;



import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import springbootartacademy.models.entity.Estados;

@Repository
public interface IEstadosDao extends PagingAndSortingRepository<Estados,Long>{
	public Estados findByNombre(String nestado);
}
