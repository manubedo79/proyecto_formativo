package springbootartacademy.models.service;

import java.util.List;

import org.springframework.data.domain.Page;

import springbootartacademy.models.entity.Clientes;

public interface IClientesService {
	
	public List<Clientes> findAllUsers();

	public Page<Clientes> ListarClientesTodos(int pageNumber);
	public void saveClientes(Clientes  clientes);

}
