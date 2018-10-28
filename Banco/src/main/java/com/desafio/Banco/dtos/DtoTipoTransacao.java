package com.desafio.Banco.dtos;

import com.desafio.BancoModel.model.TiposTransacoes;

public class DtoTipoTransacao {

	public static final String F_TIPO = "Tipo de transação";
	public static final String F_DESCRICAO = "Descrição";
	public static final String F_CAMPOS_UTILIZADOS = "Campos utilizados";

	private String tipo;

	private String descricao;
	
	private Integer camposUtilizados;

	public DtoTipoTransacao() {

	}

	public DtoTipoTransacao(String tipo, String descricao, Integer camposUtilizados) {
		this.tipo = tipo;
		this.descricao = descricao;
		this.camposUtilizados = camposUtilizados;
	}

	public DtoTipoTransacao(TiposTransacoes tt) {
		this.tipo = tt.getTipo();
		this.descricao = tt.getDescricao();
		this.camposUtilizados = tt.getCamposUtilizados();
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

	public boolean utilizaOrigem() {
		return (camposUtilizados & 1) == 1;
	}

	public boolean utilizaDestino() {
		return ((camposUtilizados>>1) & 1) == 1;
	}

	public Integer getCamposUtilizados() {
		return camposUtilizados;
	}

	public void setCamposUtilizados(Integer camposUtilizados) {
		this.camposUtilizados = camposUtilizados;
	}	

}
