package springbootartacademy.models.entity;

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
private Long id;
@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name="caracteristica_id")
private Caracteristicas  caracteristicas;

@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name="usuario_id")
private Usuarios usuarios;
@Column(length=10)
private int cantidad;
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
public int getCantidad() {
	return cantidad;
}
public void setCantidad(int cantidad) {
	this.cantidad = cantidad;
}


}
