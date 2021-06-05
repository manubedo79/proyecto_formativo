package springbootartacademy.models.entity;

import java.util.ArrayList;
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
import javax.persistence.Transient;

@Entity
public class Obras {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_obra")
	private Long id;
	
	@Column(name="nombre_obra",length = 50, unique = true, nullable = true)
	private String nombre;
	
	@Column(name="descripcion_obra",length = 50, nullable = true)
	private String descripcion;

	@Column(name="estado_obra")
	private boolean estado;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_creacion_obra")
	private Date fechacreacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_id")
	private Categorias categoria;
	
	@OneToMany(mappedBy="obras",cascade=CascadeType.ALL)
	public List<Caracteristicas> caracteristicas = new ArrayList<>(); 
	@Column(name="imagen_principal_obra",nullable = false)
	private String rutaimagen_principal;

	@Column(name="imagen_segundaria_obra",nullable = true)
	private String rutaimagen_2;
	
	@Column(name="imagen_tercearia_obra",nullable = true)
	private String rutaimagen_3;

	@PrePersist
	public void PrePersist()
	{
		this.fechacreacion=new Date();
	}

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public Categorias getCategoria() {
		return categoria;
	}

	public void setCategoria(Categorias categoria) {
		this.categoria = categoria;
	}

	public String getRutaimagen_principal() {
		return rutaimagen_principal;
	}

	public void setRutaimagen_principal(String rutaimagen_principal) {
		this.rutaimagen_principal = rutaimagen_principal;
	}

	public String getRutaimagen_2() {
		return rutaimagen_2;
	}

	public void setRutaimagen_2(String rutaimagen_2) {
		this.rutaimagen_2 = rutaimagen_2;
	}

	public String getRutaimagen_3() {
		return rutaimagen_3;
	}

	public void setRutaimagen_3(String rutaimagen_3) {
		this.rutaimagen_3 = rutaimagen_3;
	}

	@Transient
	public String getImagenprincipal()
	{
		if(id==null||rutaimagen_principal==null) {return null;}
		return "imagenes/"+id+"/"+rutaimagen_principal;
	}
	@Transient
	public String getImagen2()
	{
		if(id==null||rutaimagen_2==null) {return null;}
		return "imagenes/"+id+"/"+rutaimagen_2;
	}
	@Transient
	public String getImagen3()
	{
		if(id==null||rutaimagen_3==null) {return null;}
		return "imagenes/"+id+"/"+rutaimagen_3;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public List<Caracteristicas> getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(List<Caracteristicas> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	public void addCaracteristicas(int stock, Float precio, String size) {
		this.caracteristicas.add(new Caracteristicas(stock, precio, size, this));
	}
	


}
