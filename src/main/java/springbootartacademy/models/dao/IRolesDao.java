package springbootartacademy.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootartacademy.models.entity.Roles;

public interface IRolesDao extends JpaRepository<Roles, Long>{
	public Roles findByNombre(String Nombre);
}
