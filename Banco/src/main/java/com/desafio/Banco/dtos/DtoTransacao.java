package com.desafio.Banco.dtos;

import java.util.Calendar;

public class DtoTransacao {
	
	public static final String F_ID = "Código";
	public static final String F_DATA = "Data da transação";
	public static final String F_TIPO_TRANSACAO = "Tipo de transação";
	public static final String F_USUARIO_ORIGEM = "Cliente de origem";
	public static final String F_CONTA_ORIGEM = "Conta de origem";
	public static final String F_USUARIO_DESTINO = "Cliente de destino";
	public static final String F_CONTA_DESTINO = "Conta de destino";
	public static final String F_VALOR = "Valor";

	private Integer id;
	
	private Calendar data;

	private DtoTipoTransacao tipoTransacao;
	
	private Integer contaOrigem;

	private Integer contaDestino;
	
	private Integer usuarioOrigem;

	private Integer usuarioDestino;
	
	private Double valor;

	public DtoTransacao() {

	}

	public DtoTransacao(Integer id, Calendar data, DtoTipoTransacao tipoTransacao, Integer contaOrigem, Integer contaDestino, Integer usuarioOrigem, Integer usuarioDestino, Double valor) {
		this.id = id;
		this.data = data;
		this.tipoTransacao = tipoTransacao;
		this.contaOrigem = contaOrigem;
		this.contaDestino = contaDestino;
		this.usuarioOrigem = usuarioOrigem;
		this.usuarioDestino = usuarioDestino;
		this.valor = valor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public DtoTipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(DtoTipoTransacao tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public Integer getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(Integer contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public Integer getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Integer contaDestino) {
		this.contaDestino = contaDestino;
	}

	public Integer getUsuarioOrigem() {
		return usuarioOrigem;
	}

	public void setUsuarioOrigem(Integer usuarioOrigem) {
		this.usuarioOrigem = usuarioOrigem;
	}

	public Integer getUsuarioDestino() {
		return usuarioDestino;
	}

	public void setUsuarioDestino(Integer usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}
