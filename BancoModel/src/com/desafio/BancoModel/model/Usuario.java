package com.desafio.BancoModel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name = "usuario")
@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
public class Usuario implements Serializable, EntidadeBase {
	private static final long serialVersionUID = 1L;

	@Id
	private String cpf;
	
	private String nome;

	private String endereco;

	private String email;

	private String senha;

	@Temporal(TemporalType.TIMESTAMP)
	private Date nascimento;

	@ManyToOne
	@JoinColumn(name = "conta_id")
	private Conta conta;

	@ManyToOne
	@JoinColumn(name = "tipo_usuario_id")
	private TiposUsuarios tipoUsuario;

	public Usuario() {
	}

	@Override
	public String getId() {
		return getCPF();
	}

	public String getCPF() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public TiposUsuarios getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TiposUsuarios tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}