package springbootartacademy.models.service;

import java.util.List;
import org.springframework.data.domain.Page;
import springbootartacademy.models.entity.Obras;

public interface IObrasService {

	public void guardarObra(Obras obras);
	public Obras findbyId(Long id);
	public List<Obras> findAll();
}
