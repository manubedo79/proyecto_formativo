package springbootartacademy.models.entity;

import java.util.Date;
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
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Ventas  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_venta")
	private Long id;

	@Column(name="direccion_entrega_venta",nullable = true)
	private String direccionentrega;
	
	@Column(name="total_venta",nullable = false)
	private float totalventa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private Usuarios usuarios;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estado_id")
	private Estados estado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipio_id")
	private Municipios municipios;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_venta")
	private Date fechaventa;

	@OneToMany(mappedBy="ventas", cascade=CascadeType.ALL)
	private List<ArticuloCarrito> articulos;
	
	@PrePersist
	public void PrePersist()
	{
		this.fechaventa=new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getTotalventa() {
		return totalventa;
	}

	public void setTotalventa(float totalventa) {
		this.totalventa = totalventa;
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	public Estados getEstado() {
		return estado;
	}

	public void setEstado(Estados estado) {
		this.estado = estado;
	}

	public Municipios getMunicipios() {
		return municipios;
	}

	public void setMunicipios(Municipios municipios) {
		this.municipios = municipios;
	}

	public Date getFechaventa() {
		return fechaventa;
	}

	public void setFechaventa(Date fechaventa) {
		this.fechaventa = fechaventa;
	}
	public String getDireccionentrega() {
		return direccionentrega;
	}

	public void setDireccionentrega(String direccionentrega) {
		this.direccionentrega = direccionentrega;
	}

	public List<ArticuloCarrito> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<ArticuloCarrito> articulos) {
		this.articulos = articulos;
	}
	
	
}