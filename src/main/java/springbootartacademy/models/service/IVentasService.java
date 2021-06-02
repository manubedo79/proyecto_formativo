package springbootartacademy.models.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import springbootartacademy.models.entity.CarritoCompras;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.entity.Ventas;

public interface IVentasService {

	public void saveVenta(Ventas venta);
	public void enviarFacturaCorreo(String siteURL, Usuarios usuario,Ventas venta,CarritoCompras carrito) throws UnsupportedEncodingException, MessagingException;

}
