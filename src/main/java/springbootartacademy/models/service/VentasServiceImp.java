package springbootartacademy.models.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import springbootartacademy.models.dao.IEstadosDao;

import springbootartacademy.models.dao.IVentasDao;
import springbootartacademy.models.entity.*;

@Service
public class VentasServiceImp implements IVentasService {

	@Autowired
	private IEstadosDao estadao;

	@Autowired
	private IVentasDao ventadao;

	@Override	
	public void saveVenta(Ventas venta) {
		Estados estado = estadao.findByNombre("pendiente");
		venta.setEstado(estado);
		ventadao.save(venta);
	}
	
}
