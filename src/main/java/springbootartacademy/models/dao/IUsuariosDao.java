package springbootartacademy.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import springbootartacademy.models.entity.Usuarios;


public interface IUsuariosDao extends PagingAndSortingRepository <Usuarios, Long>{
public Usuarios findByCorreo(String correo);
public Usuarios findByResetPasswordToken(String resetPasswordToken);

@Transactional
@Modifying
@Query("update Usuarios u set u.estado=true, u.verification='' where u.id=?1")
public void actualizaestadoborraverifica(Long id);
@Transactional
@Modifying
@Query("update Usuarios u set u.estado=?1, u.verification=''  where u.id=?2")
public void actualizaestado(boolean nuevoestado,Long id);
@Query("Select u from Usuarios u where u.verification=?1")
public Usuarios findByVerification(String code);
@Query ("select usuarios from Usuarios usuarios where usuarios.correo =:correo")
public Usuarios getCorreoUsuario(@Param("correo") String correo );
@Query ("select usuarios.correo from Usuarios usuarios where usuarios.correo =?1")
public Usuarios getUsuariosByCorreo(String email);
@Query("SELECT u FROM Usuarios u WHERE CONCAT(u.id,u.correo) LIKE %?1% ")
public Page<Usuarios> findAll(String busqueda,Pageable pageable);






}


