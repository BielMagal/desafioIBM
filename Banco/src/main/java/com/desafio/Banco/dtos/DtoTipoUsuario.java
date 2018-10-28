package com.desafio.Banco.dtos;

import com.desafio.BancoModel.model.TiposUsuarios;

public class DtoTipoUsuario {

	public static final String F_ID = "Código";
	public static final String F_DESCRICAO = "Descrição";

	private Integer id;

	private String descricao;

	public DtoTipoUsuario() {

	}

	public DtoTipoUsuario(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public DtoTipoUsuario(TiposUsuarios tu) {
		this.id = tu.getId();
		this.descricao = tu.getDescricao();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
