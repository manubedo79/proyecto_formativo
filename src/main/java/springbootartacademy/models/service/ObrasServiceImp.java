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

import springbootartacademy.models.dao.IObrasDao;
import springbootartacademy.models.entity.Clientes;
import springbootartacademy.models.entity.Obras;

@Component
public class ObrasServiceImp implements IObrasService {

	@Autowired
	private IObrasDao obrdao;
	
	@Override
	public void guardarObra(Obras obras) {
		obrdao.save(obras);
	}
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
	public Obras findbyId(Long id) {
		return obrdao.findById(id).orElse(null);
	}
	@Override
	public List<Obras> findAll() {
		return (List<Obras>)obrdao.findAll();
	}

	}



