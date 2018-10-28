package com.desafio.Banco.dtos;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import com.desafio.Banco.utils.BancoUtil;

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
	
	private String contaOrigem;

	private String contaDestino;
	
	private String usuarioOrigem;

	private String usuarioDestino;
	
	private Double valor;

	public DtoTransacao() {

	}

	public DtoTransacao(Integer id, Calendar data, DtoTipoTransacao tipoTransacao, String contaOrigem, String contaDestino, String usuarioOrigem, String usuarioDestino, Double valor) {
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

	public Calendar getDataCalendar() {
		return data;
	}

	public Date getData() {
		return data.getTime();
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public DtoTipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}

	public String getTipoTransacaoNome() {
		return tipoTransacao.getTipo();
	}

	public void setTipoTransacao(DtoTipoTransacao tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public String getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(String contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public String getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(String contaDestino) {
		this.contaDestino = contaDestino;
	}

	public String getUsuarioOrigem() {
		return usuarioOrigem;
	}

	public void setUsuarioOrigem(String usuarioOrigem) {
		this.usuarioOrigem = usuarioOrigem;
	}

	public String getUsuarioDestino() {
		return usuarioDestino;
	}

	public void setUsuarioDestino(String usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	public Double getValor() {
		return valor;
	}

	public String getValorFormatado() {
		return NumberFormat.getCurrencyInstance().format(valor);
	}

	public String getValorString() {
		return valor != null? valor.toString() : null;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public void setValorString(String valor) {
		this.valor =  BancoUtil.stringVirgulaToDouble((valor));
	}
}
