package springbootartacademy.models.dao;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import springbootartacademy.models.entity.Obras;

@Repository
public interface IObrasDao extends PagingAndSortingRepository<Obras,Long>{

	@Query("SELECT o FROM Obras o INNER JOIN Categorias c ON c.id = o.categoria.id WHERE c.id = :id")
	public Page<Obras> findAllcategoriaobras(@Param("id") Long id, Pageable pageable );
	@Transactional
	@Modifying
	@Query("update Obras o set o.estado=?1  where o.id=?2")
	public void actualizaestado(boolean nuevoestado,Long id);
	@Query("SELECT o FROM Obras o WHERE CONCAT(o.id,o.nombre) LIKE %?1%")
	public Page<Obras> findAll(String busqueda,Pageable pageable);
	
	@Query("SELECT o FROM Obras o INNER JOIN Categorias c ON c.id = o.categoria.id WHERE o.categoria.id = :idcate AND o.id != :id ORDER BY o.fechacreacion ")
	public List<Obras> ObrasRelacionadas(@Param("idcate") Long idcate,@Param("id") Long id, Pageable pageable);
	public Obras findByNombre(String nombre);
}
