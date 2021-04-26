package springbootartacademy.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
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
	public Obras findbyId(Long id) {
		return obrdao.findById(id).orElse(null);
	}
	@Override
	public List<Obras> findAll() {
		return (List<Obras>)obrdao.findAll();
	}

	}



