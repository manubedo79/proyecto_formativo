package springbootartacademy.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springbootartacademy.models.dao.IArticuloCarritoDao;
import springbootartacademy.models.entity.ArticuloCarrito;
import springbootartacademy.models.entity.Usuarios;
@Service
public class ArticuloCarritoServiceImp implements IArticuloCarritoService{
	@Autowired
	private IArticuloCarritoDao articulodao;
	
	@Override
	public List<ArticuloCarrito> articuloCarritos(Usuarios usuarios) {
		// TODO Auto-generated method stub
		return articulodao.findByUsuarios(usuarios);
	}

}
