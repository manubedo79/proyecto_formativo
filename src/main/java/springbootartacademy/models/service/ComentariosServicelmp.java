package springbootartacademy.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springbootartacademy.models.dao.IComentariosDao;
import springbootartacademy.models.entity.Comentarios;
@Service
public class ComentariosServicelmp implements IComentariosService{
	@Autowired
	private IComentariosDao icomdao;
	@Override
	public List<Comentarios> findallComentarios(Long id) {
		
		return icomdao.findAllCometarios(id);
	}

}
