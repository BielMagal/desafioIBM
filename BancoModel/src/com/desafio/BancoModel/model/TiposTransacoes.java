package com.desafio.BancoModel.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the tipos_transacoes database table.
 * 
 */
@Entity
@Table(name = "tipos_transacoes")
@NamedQuery(name = "TiposTransacoes.findAll", query = "SELECT t FROM TiposTransacoes t")
public class TiposTransacoes implements Serializable, EntidadeBase {
	private static final long serialVersionUID = 1L;

	
	@Id
	private String tipo;

	private String descricao;
	
	private Integer camposUtilizados;

	public TiposTransacoes() {
	}

	@Override
	public String getId() {
		return getTipo();
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getCamposUtilizados() {
		return camposUtilizados;
	}

	public void setCamposUtilizados(Integer camposUtilizados) {
		this.camposUtilizados = camposUtilizados;
	}
	
}