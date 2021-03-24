package springbootartacademy.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import springbootartacademy.models.entity.Clientes;

@Repository
public interface IClientesDao extends PagingAndSortingRepository<Clientes, Long> {
}
