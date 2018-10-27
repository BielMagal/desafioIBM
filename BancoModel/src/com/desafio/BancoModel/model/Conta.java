package com.desafio.BancoModel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the conta database table.
 * 
 */
@Entity
@Table(name = "conta")
@NamedQuery(name = "Conta.findAll", query = "SELECT c FROM Conta c")
public class Conta implements Serializable, EntidadeBase {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Integer id;

	private String usuario;

	private Double saldo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date criacao;

	public Conta() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Date getCriacao() {
		return criacao;
	}

	public void setCriacao(Date criacao) {
		this.criacao = criacao;
	}

}