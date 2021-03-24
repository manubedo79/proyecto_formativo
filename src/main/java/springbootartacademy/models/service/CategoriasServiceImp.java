package springbootartacademy.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import springbootartacademy.models.dao.ICategoriasDao;
import springbootartacademy.models.dao.IClientesDao;
import springbootartacademy.models.entity.Categorias;

@Component
public class CategoriasServiceImp implements ICategoriasService{

	@Autowired
	private ICategoriasDao catedao;
	
	@Override
	public List<Categorias> findAllUsers() {
		return (List<Categorias>)catedao.findAll();
	}

	@Override
	public Page<Categorias> ListarCategoriasTodas(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 2 );
		
		return catedao.findAll(pageable) ;
	}

}
