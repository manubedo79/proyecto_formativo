package springbootartacademy.models.service;

import java.util.List;

import org.springframework.data.domain.Page;

import springbootartacademy.models.entity.Clientes;

public interface IClientesService {
	
	public List<Clientes> findAllClientes();
	public Clientes findById(Long idcliente);
	public Page<Clientes> ListarClientesTodos(int pageNumber, String busqueda);
	public void saveClientes(Clientes  clientes);
	public Clientes findAllByCorreo(String correo);

}
