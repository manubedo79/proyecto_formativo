package springbootartacademy.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootartacademy.models.entity.Municipios;

public interface IMunicipiosDao extends JpaRepository<Municipios, Long>{

}
