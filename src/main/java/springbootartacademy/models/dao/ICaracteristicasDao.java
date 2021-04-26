package springbootartacademy.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.Categorias;
import springbootartacademy.models.entity.Obras;

@Repository
public interface ICaracteristicasDao extends PagingAndSortingRepository<Caracteristicas,Long>{

}
