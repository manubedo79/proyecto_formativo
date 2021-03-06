package springbootartacademy.models.entity;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="articulo_carrito")
public class ArticuloCarrito {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name = "id_articulo_carrito")
private Long id;
@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name="caracteristica_id")
private Caracteristicas  caracteristicas;

@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name="usuario_id")
private Usuarios usuarios;
@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
@JoinColumn(name="venta_id")
private Ventas ventas;
@Column(name="cantidad_articulo_carrito", length=10)
private Integer cantidad;

public void agregar_cantidad(Integer cantidad)
{
	if(cantidad >0){this.cantidad += cantidad;}
	
}
public boolean actualizarCantidad(Integer cantidad) {
	return cantidad == null || cantidad <= 0 || this.getCaracteristicas().validar_cantidad(cantidad);
}

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public Caracteristicas getCaracteristicas() {
	return caracteristicas;
}
public void setCaracteristicas(Caracteristicas caracteristicas) {
	this.caracteristicas = caracteristicas;
}

public Usuarios getUsuarios() {
	return usuarios;
}
public void setUsuarios(Usuarios usuarios) {
	this.usuarios = usuarios;
}
public Integer getCantidad() {
	return cantidad;
}
public void setCantidad(Integer cantidad) {
	this.cantidad = cantidad;
}

public Ventas getVentas() {
	return ventas;
}
public void setVentas(Ventas ventas) {
	this.ventas = ventas;
}
public float subtotal () {
	return cantidad.floatValue()*caracteristicas.getPrecio() ;
}

}
