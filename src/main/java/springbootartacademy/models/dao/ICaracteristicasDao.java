package springbootartacademy.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.Categorias;
import springbootartacademy.models.entity.Obras;

@Repository
public interface ICaracteristicasDao extends PagingAndSortingRepository<Caracteristicas,Long>{

	@Query("SELECT obrac from Caracteristicas obrac where obrac.id = :id")
	public Caracteristicas listarcaracteristicas_obras(@Param("id")Long id);
	
	@Query("SELECT obrac from Caracteristicas obrac where obrac.obras.id = :id")
	public List<Caracteristicas> listarTodasCaracteristicas(@Param("id")Long id);
	
	
	
}
