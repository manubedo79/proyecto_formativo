package springbootartacademy.models.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import springbootartacademy.models.dao.IImagenesDao;
import springbootartacademy.models.dao.IObrasDao;
import springbootartacademy.models.entity.Imagenes;
import springbootartacademy.models.entity.Obras;

@Component
public class ImagenesServiceImp implements IImagenesService {

	@Autowired
	private IImagenesDao imgdao;
	
	@Override
	public String guardarimagen(MultipartFile multipartFile, String ruta) {
		String nombreoriginal = multipartFile.getOriginalFilename();
		try {
		File imagenFile = new File(ruta + nombreoriginal);		
		System.out.println("Archivo: "+imagenFile.getAbsolutePath());
		multipartFile.transferTo(imagenFile);
		return nombreoriginal;	
		} catch (IOException e) {
			System.out.println("Error "+e.getMessage());
			return null;
		}
	}

	@Override
	public Imagenes findbyId(Long id) {
		return imgdao.findById(id).orElse(null);
	}
	@Override
	public void guardarimagenbd(Imagenes imagen) {
		imgdao.save(imagen);
	}

	}



