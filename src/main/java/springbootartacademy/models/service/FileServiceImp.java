package springbootartacademy.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileServiceImp implements IFileService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final static String UPLOADS_FOLDER = "imagenes";
    private final static String UPLOADSCATEGORIA_FOLDER = "imagenescategorias";
    private final static String UPLOADSCOMPROBANTES_FOLDER = "comprobantes";
    
    public Resource cargar(String nombre) throws MalformedURLException {
      Path pathfoto = getPath(nombre);
      log.info("pathFoto: " + pathfoto);
      Resource recurso = new UrlResource(pathfoto.toUri());
      if (!recurso.exists() || !recurso.isReadable()) {
        throw new RuntimeException("Error: no se puede cargar la imagen: " + pathfoto.toString());
    }
        return recurso;
    }

   
    public String copiar(MultipartFile file) throws IOException {
        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(uniqueFilename);

		log.info("rootPath: " + rootPath);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
    }

    
    public boolean eliminar(String nombre) {
        Path rootPath = getPath(nombre);
		File archivo = rootPath.toFile();

		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			}
		}
		return false;
    }
    public Path getPath(String nombre) {
		return Paths.get(UPLOADS_FOLDER).resolve(nombre).toAbsolutePath();
	}
    public Path getCategoriaPath(String nombre) {
		return Paths.get(UPLOADSCATEGORIA_FOLDER).resolve(nombre).toAbsolutePath();
	}

    public Path getComprobantePath(String nombre) {
		return Paths.get(UPLOADSCOMPROBANTES_FOLDER).resolve(nombre).toAbsolutePath();
	}
	@Override
	public Resource cargarImagenCategoria(String nombre) throws MalformedURLException {
		  Path pathfoto = getCategoriaPath(nombre);
	      log.info("pathFoto: " + pathfoto);
	      Resource recurso = new UrlResource(pathfoto.toUri());
	      if (!recurso.exists() || !recurso.isReadable()) {
	        throw new RuntimeException("Error: no se puede cargar la imagen: " + pathfoto.toString());
	    }
	        return recurso;
	}


	@Override
	public String copiarImagenCategoria(MultipartFile file) throws IOException {
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getCategoriaPath(uniqueFilename);

		log.info("rootPath: " + rootPath);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
	}


	@Override
	public boolean eliminarImagenCategoria(String nombre) {
		  Path rootPath = getCategoriaPath(nombre);
			File archivo = rootPath.toFile();

			if (archivo.exists() && archivo.canRead()) {
				if (archivo.delete()) {
					return true;
				}
			}
			return false;
	}
	@Override
	public Resource cargarComprobante(String nombre) throws MalformedURLException {
		  Path pathfoto = getComprobantePath(nombre);
	      log.info("pathFoto: " + pathfoto);
	      Resource recurso = new UrlResource(pathfoto.toUri());
	      if (!recurso.exists() || !recurso.isReadable()) {
	        throw new RuntimeException("Error: no se puede cargar la imagen: " + pathfoto.toString());
	    }
	        return recurso;
	}


	@Override
	public String copiarComprobante(MultipartFile file) throws IOException {
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getComprobantePath(uniqueFilename);

		log.info("rootPath: " + rootPath);

		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
	}


	@Override
	public boolean eliminarComprobante(String nombre) {
		  Path rootPath = getComprobantePath(nombre);
			File archivo = rootPath.toFile();

			if (archivo.exists() && archivo.canRead()) {
				if (archivo.delete()) {
					return true;
				}
			}
			return false;
	}
    
}
