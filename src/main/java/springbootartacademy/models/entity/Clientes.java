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
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Clientes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="El campo nombre es requerido")
	@Column(length = 50, nullable = false)
	private String nombre;
	
	@NotBlank(message="El campo apellido es requerido")
	@Column(length = 50, nullable = false)
	private String apellido;

	@NotBlank(message="El campo direccion es requerido")
	@Column(length = 50, nullable = false)
	private String direccion;

	@NotBlank(message="El campo telefono es requerido")
	@Column(length = 13, nullable = false)
	private String telefono;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuarios_id")
	private Usuarios usuarios;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipios_id")
	private Municipios municipios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	public Municipios getMunicipios() {
		return municipios;
	}

	public void setMunicipios(Municipios municipios) {
		this.municipios = municipios;
	}
}
