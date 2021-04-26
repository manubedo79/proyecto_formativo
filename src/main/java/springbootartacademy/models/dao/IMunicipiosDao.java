package springbootartacademy.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import springbootartacademy.models.entity.Municipios;

public interface IMunicipiosDao extends JpaRepository<Municipios, Long>{
	@Query("Select m from Municipios m where m.departamentos.id=:id")

	public List<Municipios> consultarMunicipios(@Param("id") Long id);
}
