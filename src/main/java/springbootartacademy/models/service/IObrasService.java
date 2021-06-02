package springbootartacademy.models.service;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import springbootartacademy.models.entity.Obras;

public interface IObrasService {

	public void guardarObra(Obras obras);
	public Obras findbyId(Long id);
	public List<Obras> findAllObras();
	public String guardarimagen(MultipartFile multipartFile, String ruta);	
	public Page<Obras> findAllcategoriaobras( Long id, int pageNumber );
	public boolean cambioEstado(Long id);
	public Page<Obras> ListarObrasTodas(int pageNumber,String busqueda);
	public List<Obras> ObrasRelacionadas(Long idcate,Long id);
	public String findByNombre(String nombre);
}
