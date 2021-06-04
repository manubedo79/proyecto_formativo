package springbootartacademy.models.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;

import springbootartacademy.models.entity.CarritoCompras;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.entity.Ventas;

public interface IVentasService {
	public List<Ventas> listarVentasUsuarios(Usuarios usuarios);
	public void saveVenta(Ventas venta);
	public Ventas findByIdVenta(Long id);
	public void enviarFacturaCorreo(String siteURL, Usuarios usuario,Ventas venta,CarritoCompras carrito) throws UnsupportedEncodingException, MessagingException;
	public Iterable<Ventas> findByFechas();
	public Page<Ventas> ListarVentasTodas(int pageNumber,String busqueda);

}
