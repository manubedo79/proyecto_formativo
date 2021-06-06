package springbootartacademy.models.service;



import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import springbootartacademy.models.dao.IEstadosDao;

import springbootartacademy.models.dao.IVentasDao;
import springbootartacademy.models.entity.*;

@Service
public class VentasServiceImp implements IVentasService {

	@Autowired
	private IEstadosDao estadao;

	@Autowired
	private IVentasDao ventadao;
	@Autowired
	private JavaMailSender mailSender ;
	
	
	@Override	
	@Transactional
	public void saveVenta(Ventas venta) {
		Estados estado = estadao.findByNombre("pendiente");
		venta.setEstado(estado);
		ventadao.save(venta);
	}

	@Override
	public void cambioEstado(String idestado,String idventa) {
		Long idestadon = Long.parseLong(idestado);
		Long idventan = Long.parseLong(idventa);
		Ventas venta = ventadao.findById(idventan).orElse(null);
		Estados estado = estadao.findById(idestadon).orElse(null);
		venta.setEstado(estado);
		ventadao.save(venta);
	}

	
	@Override
	@Transactional(readOnly=true)
	public List<Ventas> listarVentasUsuarios(Usuarios usuarios) {
		// TODO Auto-generated method stub
		return ventadao.findByusuarios(usuarios);
	}


	@Override
	public Ventas findByIdVenta(Long id) {
		// TODO Auto-generated method stub
		return ventadao.findById(id).orElse(null);
	}
	public void enviarFacturaCorreo(String siteURL,Usuarios usuario,Ventas venta,CarritoCompras carrito)throws UnsupportedEncodingException, MessagingException {
		Long id =venta.getId();
		String subject ="Factura Art Academy";
		String mailcontent = 
		"<!DOCTYPE html>\r\n" + 
		"<html>\r\n" + 
		"<head>\r\n" + 
		"<style>\r\n" + 
		"#customers {\r\n" + 
		"  font-family: Arial, Helvetica, sans-serif;\r\n" + 
		"  border-collapse: collapse;\r\n" + 
		"  width: 100%;\r\n" + 
		"}\r\n" + 
		"\r\n" + 
		"#customers td, #customers th {\r\n" + 
		"  border: 1px solid #ddd;\r\n" + 
		"  padding: 8px;\r\n" + 
		"}\r\n" + 
		"\r\n" + 
		"#customers tr:nth-child(even){background-color: #f2f2f2;}\r\n" + 
		"\r\n" + 
		"#customers tr:hover {background-color: #ddd;}\r\n" + 
		"\r\n" + 
		"#customers th {\r\n" + 
		"  padding-top: 12px;\r\n" + 
		"  padding-bottom: 12px;\r\n" + 
		"  text-align: left;\r\n" + 
		"  background-color: #04AA6D;\r\n" + 
		"  color: white;\r\n" + 
		"}\r\n" + 
		"</style>\r\n" + 
		"</head>\r\n" + 
		"<body>\r\n" ;
		mailcontent+="<p>Señor Usuario usted realizo una compra en Art Academy con los siguientes productos:\r\n" + 
		"</p>"+
		"\r\n" + 
		"<table id=\"customers\">\r\n" + 
		"  <tr>\r\n" + 
		"    <th>Cantidad</th>\r\n" + 
		"    <th>Producto</th>\r\n" + 
		"    <th>Precio Unitario</th>\r\n" + 
		"    <th>Subtotal</th>\r\n" + 
		"  </tr>\r\n" ;
		for (ArticuloCarrito compras : carrito.getCarritoitems()) {
		mailcontent += "  <tr>\r\n" + 
		"    <td>"+compras.getCantidad()+"</td>\r\n" + 
		"    <td>"+compras.getCaracteristicas().getObras().getNombre()+"/"+compras.getCaracteristicas().getSize()+"</td>\r\n" + 
		"    <td>"+compras.getCaracteristicas().getPrecio()+"</td>\r\n" +
		"    <td>"+compras.subtotal()+"</td>\r\n" +
		"  </tr>\r\n" ;
		}
		mailcontent +=
		"</table>\r\n" + 
		"\r\n" ;
		String enlace = siteURL+"/cuenta/verificacion?code="+id;
		mailcontent+="<h1>Total a Pagar <strong>"+venta.getTotalventa()+"</strong></h1>" +
		"<h3>Para finalizar la compra debe de realizar el pago por medio de la siguiente cuenta <strong>0000</strong>" + 
		" y debe de proporcionar el comprobante de pago en el siguiente enlace "+enlace+"</h3>\r\n" + 
		"</body>\r\n" + 
		"</html>\r\n" ;
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(usuario.getCorreo());
		helper.setSubject(subject);
		helper.setText(mailcontent, true);
		mailSender.send(message);

	}


	@Override
 public Iterable<Ventas> findByFechas() {
		// TODO Auto-generated method stub
		return ventadao.findByFechas();
	}
 
	@Override
	public Page<Ventas> ListarVentasTodas(int pageNumber, String busqueda) {
		 Pageable pageable = PageRequest.of(pageNumber - 1, 12 );
		if(busqueda!=null) {
			 return ventadao.findAll(busqueda, pageable);
		}
		 return ventadao.findAll(pageable);
	}
	
	@Override
	public void guardarVentas(Ventas ventas) {
	ventadao.save(ventas);
	}

	@Override
	public boolean actualizaestado(Long id) {
		Ventas ventas = ventadao.findById(id).orElse(null);
		Estados estados = estadao.findByNombre("CANCELADO");
		if(ventas==null) {
			return false;
		}else if(ventas.getEstado().getNombre().equals("PENDIENTE")) {
			ventadao.actualizaestado(estados, id);
		}
		return true;
	} 
	
}
