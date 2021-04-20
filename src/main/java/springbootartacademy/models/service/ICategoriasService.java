package springbootartacademy.models.service;

import java.util.List;

import org.springframework.data.domain.Page;

import springbootartacademy.models.entity.Categorias;

public interface ICategoriasService {

	public List<Categorias> findAllUsers();

	public Page<Categorias> ListarCategoriasTodas(int pageNumber);
	
	public void guardarCategorias(Categorias categorias);
	public Categorias findbyIdCategoria(Long id);
}
