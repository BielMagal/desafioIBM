package com.desafio.BancoModel.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the tipos_usuarios database table.
 * 
 */
@Entity
@Table(name = "tipos_usuarios")
@NamedQuery(name = "TiposUsuarios.findAll", query = "SELECT t FROM TiposUsuarios t")
public class TiposUsuarios implements Serializable, EntidadeBase {
	private static final long serialVersionUID = 1L;

	public TiposUsuarios() {
	}
	
	@Id
	private Integer id;

	private String descricao;

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

}