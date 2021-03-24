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
private Long id;

@Column(length=50, nullable=false, unique=true)
private String nombrecategoria;

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
}
