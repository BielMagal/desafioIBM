package com.desafio.Banco.dtos;

import java.util.Calendar;

public class DtoConta {
	
	public static final String F_ID = "Conta";
	public static final String F_CPF_CLIENTE = "CPF cliente";
	public static final String F_NOME_CLIENTE = "Nome cliente";
	public static final String F_CRIACAO = "Data de criação";
	public static final String F_ULTIMA_TRANSACAO = "Última transação";
	public static final String F_SALDO = "Saldo atual";

	private Integer id;

	private String cpfCliente;
	
	private String nomeCliente;
	
	private Calendar criacao;

	private Calendar ultimaTransacao;

	private Double saldo;

	public DtoConta() {

	}

	public DtoConta(Integer id, String cpfCliente, String nomeCliente, Calendar criacao, Calendar ultimaTransacao, Double saldo) {
		this.id = id;
		this.cpfCliente = cpfCliente;
		this.nomeCliente = nomeCliente;
		this.criacao = criacao;
		this.ultimaTransacao = ultimaTransacao;
		this.saldo = saldo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Calendar getCriacao() {
		return criacao;
	}

	public void setCriacao(Calendar criacao) {
		this.criacao = criacao;
	}

	public Calendar getUltimaTransacao() {
		return ultimaTransacao;
	}

	public void setUltimaTransacao(Calendar ultimaTransacao) {
		this.ultimaTransacao = ultimaTransacao;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
}
