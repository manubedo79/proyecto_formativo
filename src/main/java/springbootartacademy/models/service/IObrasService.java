package springbootartacademy.models.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import springbootartacademy.models.entity.Obras;

public interface IObrasService {

	public void guardarObra(Obras obras);
	public Obras findbyId(Long id);
	public List<Obras> findAll();
	public String guardarimagen(MultipartFile multipartFile, String ruta);	
	
}
