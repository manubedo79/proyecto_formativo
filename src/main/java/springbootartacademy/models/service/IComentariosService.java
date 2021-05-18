package springbootartacademy.models.service;

import java.util.List;

import springbootartacademy.models.entity.Comentarios;

public interface IComentariosService {
	public List<Comentarios> findallComentarios (Long id);
}
