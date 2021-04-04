package springbootartacademy.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import springbootartacademy.models.dao.IClientesDao;
import springbootartacademy.models.entity.Clientes;

@Component
public class ClientesServiceImp implements IClientesService {

	@Autowired
	private IClientesDao clidao;
	
	@Override
	public List<Clientes> findAllUsers() {
		return (List<Clientes>)clidao.findAll();
	}

	@Override
	public Page<Clientes> ListarClientesTodos(int pageNumber) {
		
		Pageable pageable = PageRequest.of(pageNumber - 1, 2 );
		
			return clidao.findAll(pageable) ;
	}

	@Override
	public void saveClientes(Clientes clientes) {
		clidao.save(clientes);
		
	}
	}



