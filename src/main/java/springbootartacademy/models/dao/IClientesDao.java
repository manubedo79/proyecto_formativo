package springbootartacademy.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import springbootartacademy.models.entity.Clientes;

public interface IClientesDao extends PagingAndSortingRepository<Clientes, Long> {

}
