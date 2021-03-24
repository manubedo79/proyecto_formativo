package springbootartacademy.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import springbootartacademy.models.entity.Categorias;

@Repository
public interface ICategoriasDao extends PagingAndSortingRepository<Categorias,Long>{

}
