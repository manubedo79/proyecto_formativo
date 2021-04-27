package springbootartacademy.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springbootartacademy.models.entity.Obras;

@Repository
public interface IObrasDao extends PagingAndSortingRepository<Obras,Long>{

	@Query("SELECT o FROM Obras o INNER JOIN Categorias c ON c.id = o.categoria.id WHERE c.id = :id")
	public Page<Obras> findAllcategoriaobras(@Param("id") Long id, Pageable pageable );
	
}
