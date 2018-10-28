package com.desafio.Banco.dtos;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.desafio.BancoModel.model.Conta;

public class DtoConta {
	
	public static final String F_ID = "Conta";
	public static final String F_CPF_CLIENTE = "CPF cliente";
	public static final String F_NOME_CLIENTE = "Nome cliente";
	public static final String F_CRIACAO = "Data de criação";
	public static final String F_SALDO = "Saldo atual";

	private String id;

	private String cpfCliente;
	
	private String nomeCliente;
	
	private Calendar criacao;

	private Double saldo;

	public DtoConta() {

	}

	public DtoConta(String id, String cpfCliente, String nomeCliente, Calendar criacao, Double saldo) {
		this.id = id;
		this.cpfCliente = cpfCliente;
		this.nomeCliente = nomeCliente;
		this.criacao = criacao;
		this.saldo = saldo;
	}

	public DtoConta(Conta c) {
		this.id = c.getStringId();
		this.cpfCliente = c.getUsuario();
		this.criacao = Calendar.getInstance();
		criacao.setTime(c.getCriacao());
		this.saldo = c.getSaldo();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Date getCriacaoData() {
		return criacao.getTime();
	}

	public void setCriacao(Calendar criacao) {
		this.criacao = criacao;
	}

		public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public String getSaldoFormatado() {
		return NumberFormat.getCurrencyInstance(new Locale("pt", "BR" )).format(saldo);
	}
	
}
