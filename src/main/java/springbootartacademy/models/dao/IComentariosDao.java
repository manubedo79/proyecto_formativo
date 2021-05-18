package springbootartacademy.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import springbootartacademy.models.entity.Comentarios;

public interface IComentariosDao extends JpaRepository<Comentarios, Long>{
	@Query("Select com from Comentarios com where com.obras.id = ?1")
	public List<Comentarios> findAllCometarios (Long id);
}
