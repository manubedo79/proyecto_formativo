package springbootartacademy.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import springbootartacademy.models.dao.ICaracteristicasDao;
import springbootartacademy.models.dao.IObrasDao;
import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.Obras;

@Component
public class CaracteristicasServiceImp implements ICaracteristicasService {

	@Autowired
	private ICaracteristicasDao caracdao;
	
	@Override
	public void guardarCaracteristica(Caracteristicas caracteristica) {
		caracdao.save(caracteristica);
	}

	@Override
	public Caracteristicas findbyId(Long id) {
		return caracdao.findById(id).orElse(null);
	}
	
	@Override
	public Caracteristicas listarcaracteristicas_obras(Long id) {
		// TODO Auto-generated method stub
		return caracdao.listarcaracteristicas_obras(id);
	}

	}



