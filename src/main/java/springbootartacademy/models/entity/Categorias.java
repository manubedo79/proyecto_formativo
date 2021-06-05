package springbootartacademy.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Categorias {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="id_categoria")
private Long id;
@Column(name="nombre_categoria",length=50, nullable=false, unique=true)
private String nombrecategoria;
@Column(name="imagen_principal_categoria",nullable=false, length=65)
private String imagen;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getNombrecategoria() {
	return nombrecategoria;
}

public void setNombrecategoria(String nombrecategoria) {
	this.nombrecategoria = nombrecategoria;
}

public String getImagen() {
	return imagen;
}

public void setImagen(String imagen) {
	this.imagen = imagen;
}

}
