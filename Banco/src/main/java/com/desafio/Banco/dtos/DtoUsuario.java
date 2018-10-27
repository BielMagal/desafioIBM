package com.desafio.Banco.dtos;

import java.util.Calendar;

public class DtoUsuario {
	
	public static final String F_CPF = "CPF";
	public static final String F_NOME = "Nome";
	public static final String F_EMAIL = "E-mail";
	public static final String F_ENDERECO = "Endereço";
	public static final String F_SENHA = "Senha";
	public static final String F_NASCIMENTO = "Data de Nascimento";
	public static final String F_CONTA = "Número da Conta";
	public static final String F_TIPO_USUARIO = "Tipo de Usuário";

	private String cpf;
	
	private String nome;

	private String endereco;

	private String email;

	private String senha;

	private Calendar nascimento;
	
	private Integer numConta;

	private DtoTipoUsuario tipoUsuario;

	public DtoUsuario() {

	}

	public DtoUsuario(String cpf, String nome, String endereco, String email, String senha, Calendar nascimento, Integer numConta, DtoTipoUsuario tipoUsuario) {
		this.cpf = cpf;
		this.nome = nome;
		this.endereco = endereco;
		this.email = email;
		this.senha = senha;
		this.nascimento = nascimento;
		this.numConta = numConta;
		this.tipoUsuario = tipoUsuario;
	}

	public String getCpf() {
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

	public Calendar getNascimento() {
		return nascimento;
	}

	public void setNascimento(Calendar nascimento) {
		this.nascimento = nascimento;
	}

	public Integer getNumConta() {
		return numConta;
	}

	public void setNumConta(Integer numConta) {
		this.numConta = numConta;
	}

	public Integer getTipoUsuarioId() {
		return tipoUsuario != null? tipoUsuario.getId() : null;
	}

	public String getTipoUsuarioDescricao() {
		return tipoUsuario != null? tipoUsuario.getDescricao() : "";
	}

	public void setTipoUsuario(DtoTipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	public Integer getIdade() {
		Calendar hoje = Calendar.getInstance();
		hoje.setTimeInMillis(System.currentTimeMillis());
		Integer idade = hoje.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR);
		Integer difDias = hoje.get(Calendar.DAY_OF_YEAR) - nascimento.get(Calendar.DAY_OF_YEAR);
		if(difDias < 0)
			idade--;
	    return idade;
	}
	
	public boolean usuarioGerente() {
		return this.tipoUsuario.getDescricao().equals("Gerente");
	}
}
