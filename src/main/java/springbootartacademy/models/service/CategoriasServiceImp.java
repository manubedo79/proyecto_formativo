package springbootartacademy.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import springbootartacademy.models.dao.ICategoriasDao;
import springbootartacademy.models.dao.IClientesDao;
import springbootartacademy.models.entity.Categorias;

@Component
public class CategoriasServiceImp implements ICategoriasService{

	@Autowired
	private ICategoriasDao catedao;
	
	@Override
	public List<Categorias> findAll() {
		return (List<Categorias>)catedao.findAll();
	}

	@Override
	public Page<Categorias> ListarCategoriasTodas(int pageNumber,String busqueda) {
		Pageable pageable = PageRequest.of(pageNumber - 1, 12 );
		if(busqueda!=null) { return catedao.findAll(busqueda,pageable);}
		
		return catedao.findAll(pageable) ;
	}

	@Override
	public void guardarCategorias(Categorias categorias) {
	catedao.save(categorias);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Categorias findbyIdCategoria(Long id) {
		// TODO Auto-generated method stub
		return catedao.findById(id).orElse(null);
	}

	@Override
	public String findByNombrecategoria(String nombre) {
		Categorias categorias = catedao.findByNombrecategoria(nombre);
		
		return (categorias==null)?"Unique":"Duplicate";
	}
}
