package springbootartacademy.models.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
	public Resource cargar(String nombre) throws MalformedURLException;
    public String copiar(MultipartFile file) throws IOException;
    public boolean eliminar(String nombre);
    public Resource cargarImagenCategoria(String nombre) throws MalformedURLException;
    public String copiarImagenCategoria(MultipartFile file) throws IOException;
    public boolean eliminarImagenCategoria(String nombre);
    
    public Resource cargarComprobante(String nombre) throws MalformedURLException;
    public String copiarComprobante(MultipartFile file) throws IOException;
    public boolean eliminarComprobante(String nombre);   
}
