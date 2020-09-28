package com.fcr.modelo.to;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Processo {

	private Long id;
	private String titulo;
	private String descricao;
	private Boolean pendenteParecer;
	private List<Usuario> usuarios;

	public Processo(Long id, String titulo, String descricao, Boolean pendenteParecer, List<Usuario> usuarios) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.setPendenteParecer(pendenteParecer);
		this.usuarios = usuarios;
	}

	public Processo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Boolean getPendenteParecer() {
		return pendenteParecer;
	}

	public void setPendenteParecer(Boolean pendenteParecer) {
		this.pendenteParecer = pendenteParecer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Processo other = (Processo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
