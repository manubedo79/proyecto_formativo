package springbootartacademy.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import springbootartacademy.models.entity.Categorias;

@Repository
public interface ICategoriasDao extends PagingAndSortingRepository<Categorias,Long>{
	@Query("SELECT c FROM Categorias c WHERE CONCAT(c.id,c.nombrecategoria) LIKE %?1% ")
	public Page<Categorias> findAll(String busqueda,Pageable pageable);
}
