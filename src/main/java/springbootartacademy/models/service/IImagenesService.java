package springbootartacademy.models.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import springbootartacademy.models.entity.Imagenes;
import springbootartacademy.models.entity.Obras;

public interface IImagenesService {

	public Imagenes findbyId(Long id);
	public String guardarimagen(MultipartFile multipartFile, String ruta);
	public void guardarimagenbd(Imagenes imagen);
}
