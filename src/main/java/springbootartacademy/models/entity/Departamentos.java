package springbootartacademy.models.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Departamentos {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(length=50, nullable=false, unique=true)
private String nombredepartamento;

@OneToMany (mappedBy = "departamentos",cascade = CascadeType.ALL)
private List<Municipios> municipios;


public List<Municipios> getMunicipios() {
	return municipios;
}

public void setMunicipios(List<Municipios> municipios) {
	this.municipios = municipios;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getNombredepartamento() {
	return nombredepartamento;
}

public void setNombredepartamento(String nombredepartamento) {
	this.nombredepartamento = nombredepartamento;
}


}
