package springbootartacademy.models.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Caracteristicas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 4, nullable = false)
	private Integer stock;

	@Column(length = 13, nullable = false)
	private Float precio;
	
	@Column(nullable = false)
	private String size;

	@ManyToOne
	@JoinColumn(name = "obras_id")
	@JsonIgnore
	private Obras obras;
	
	
	
	
	
	
	public Caracteristicas() {
		
	}

	public Caracteristicas(Integer stock, Float precio, String size, Obras obras) {
	
		this.stock = stock;
		this.precio = precio;
		this.size = size;
		this.obras = obras;
	}
	public boolean validar_cantidad(Integer totalcantidad)
	{
		return (this.getStock()>0) && (totalcantidad<=this.getStock());
	}
	
	public void decrementar_cantidad(Integer cantidad)
	{
		this.stock -= cantidad;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}


	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Obras getObras() {
		return obras;
	}

	public void setObras(Obras obras) {
		this.obras = obras;
	}

	

	
	
	
	
	
}
