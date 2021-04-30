package springbootartacademy.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Comentarios {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "obras_id")
	private Obras obras;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clientes_id")
	private Clientes clientes;
	
	@Column(length = 2000, nullable = true)
	private String comentario;
	
	@Temporal(TemporalType.DATE)
	private Date fechacreacion;
	
	private boolean estado;
	
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




	public Obras getObras() {
		return obras;
	}



	public void setObras(Obras obras) {
		this.obras = obras;
	}



	public Clientes getClientes() {
		return clientes;
	}



	public void setClientes(Clientes clientes) {
		this.clientes = clientes;
	}



	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Date getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	
}
