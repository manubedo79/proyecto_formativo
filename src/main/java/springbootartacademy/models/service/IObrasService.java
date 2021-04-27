package springbootartacademy.models.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import springbootartacademy.models.entity.Obras;

public interface IObrasService {

	public void guardarObra(Obras obras);
	public Obras findbyId(Long id);
	public List<Obras> findAll();
	public String guardarimagen(MultipartFile multipartFile, String ruta);
	public Page<Obras> ListarObrasTodas(int pageNumber);	
	public Page<Obras> findAllcategoriaobras( Long id, int pageNumber );
	
}
