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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Municipios {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_municipio")
	private Long id;

	@Column(name="nombre_municipio",length=50, nullable=false, unique=true)
	private String nombremunicipio;
	
	@Column(name="tarifa_municipio",length=50, nullable=false, unique=true)
	private long tarifaenviomunicipio;
	
	
	@ManyToOne
	@JoinColumn(name = "departamento_id")
	@JsonIgnore
	private Departamentos departamentos;


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public String getNombremunicipio() {
	return nombremunicipio;
}


public void setNombremunicipio(String nombremunicipio) {
	this.nombremunicipio = nombremunicipio;
}


public long getTarifaenviomunicipio() {
	return tarifaenviomunicipio;
}


public void setTarifaenviomunicipio(long tarifaenviomunicipio) {
	this.tarifaenviomunicipio = tarifaenviomunicipio;
}


public Departamentos getDepartamentos() {
	return departamentos;
}


public void setDepartamentos(Departamentos departamentos) {
	this.departamentos = departamentos;
}
}
