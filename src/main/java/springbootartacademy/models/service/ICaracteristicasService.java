package springbootartacademy.models.service;

import java.util.List;
import org.springframework.data.domain.Page;

import springbootartacademy.models.entity.Caracteristicas;
import springbootartacademy.models.entity.Obras;

public interface ICaracteristicasService {

	public void guardarCaracteristica(Caracteristicas caracteristica);
	public Caracteristicas findbyId(Long id);
}
