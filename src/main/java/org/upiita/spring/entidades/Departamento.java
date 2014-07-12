package org.upiita.spring.entidades;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity(name = "departamentos")
public class Departamento {

	@Id
	@SequenceGenerator(name = "departamentoIdSecuencia", sequenceName = "departamento_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departamentoIdSecuencia")
	@Column(name = "id")
	private Integer id;

	@Column(name = "nombre")
	private String nombre;

	@ManyToMany(mappedBy="departamentos",fetch=FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT) 	//solo cuando se tiene EAGER EN AMBAS ENTIDADES
	// ESTOY SIGUIENDO LA CONVENCION DE QUE usuario es LA ENTIDAD DUENA
	private List<Usuario> usuarios;
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();

		builder.append("{");
		builder.append("id:").append(this.id).append(",");
		builder.append("nombre:").append(this.nombre);
		builder.append("}");

		return builder.toString();
	}

}
