package springbootartacademy.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootartacademy.models.entity.Departamentos;

public interface IDepartamentosDao extends JpaRepository<Departamentos, Long>{

	
}
