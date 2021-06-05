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
	public List<Obras> ObrasRelacionadas(Long idcate,Long id) {
		return (List<Obras>)obrdao.ObrasRelacionadas(idcate,id,PageRequest.of(0,3));
	}
	
	@Override
	public List<Obras> findAllObras() {
		return (List<Obras>)obrdao.findAll();
	}

	
	@Override
	public Page<Obras> findAllcategoriaobras(Long id, int pageNumber) {
Pageable pageable = PageRequest.of(pageNumber - 1, 12);
		
		return obrdao.findAllcategoriaobras(id, pageable) ;
		
	}

	@Override
	public boolean cambioEstado(Long id) {
		Obras findbyid= obrdao.findById(id).orElse(null);
		if(findbyid==null) {
			return false;
		}
		else {
			if(findbyid.isEstado()) {
				obrdao.actualizaestado(false,findbyid.getId());
			}
			else {
				obrdao.actualizaestado(true,findbyid.getId());
			}
			return true;
		}
		
	}
	@Override
	public Page<Obras> ListarObrasTodas(int pageNumber,String busqueda) {
		 Pageable pageable = PageRequest.of(pageNumber - 1, 12 );
		if(busqueda!=null) {
			 return obrdao.findAll(busqueda, pageable);
		}
		 return obrdao.findAll(pageable);
	}
	
	@Override
	public String findByNombre(String nombre) {
		Obras obras = obrdao.findByNombre(nombre);
		return (obras==null)?"Unique":"Duplicate";
	}


}



