package springbootartacademy.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import springbootartacademy.models.entity.Clientes;

@Repository
public interface IClientesDao extends PagingAndSortingRepository<Clientes, Long> {
	@Query("Select c from Clientes c where c.usuarios.correo = ?1")
	public Clientes findAllByCorreo(String correo); 
	
	@Query("SELECT c FROM Clientes c WHERE CONCAT(c.id,c.nombre,c.apellido) LIKE %?1% ")
	public Page<Clientes> findAll(String busqueda,Pageable pageable);
}
