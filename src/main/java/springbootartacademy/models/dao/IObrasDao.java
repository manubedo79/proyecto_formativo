package springbootartacademy.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import springbootartacademy.models.entity.Categorias;
import springbootartacademy.models.entity.Obras;

@Repository
public interface IObrasDao extends PagingAndSortingRepository<Obras,Long>{

}