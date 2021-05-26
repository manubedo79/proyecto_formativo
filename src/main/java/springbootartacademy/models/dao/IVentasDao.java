package springbootartacademy.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import springbootartacademy.models.entity.Ventas;

@Repository
public interface IVentasDao extends PagingAndSortingRepository<Ventas,Long>{
}
