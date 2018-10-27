package com.desafio.Banco.facades;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.desafio.Banco.dtos.DtoTipoUsuario;
import com.desafio.Banco.dtos.DtoUsuario;
import com.desafio.BancoModel.daos.DaoConta;
import com.desafio.BancoModel.daos.DaoTiposUsuarios;
import com.desafio.BancoModel.daos.DaoUsuario;
import com.desafio.BancoModel.model.Usuario;

public class FacadeUsuario {
	DaoConta daoConta = new DaoConta();
	DaoUsuario daoUsuario = new DaoUsuario();
	DaoTiposUsuarios daoTiposUsuarios = new DaoTiposUsuarios();
	public FacadeUsuario() {

	}

	public DtoUsuario verificarCPFSenha(String cpf, String senha) {
		try {
			String senhaUsuario = getSenhaUsuario(cpf);
			if (senhaUsuario != null) {
				MessageDigest m = MessageDigest.getInstance("MD5");
				m.update(senha.getBytes(), 0, senha.length());
				senha = new BigInteger(1, m.digest()).toString(16);
				if (senha.equals(senhaUsuario)) {
					return getUsuario(cpf);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void criarSenha(DtoUsuario usuario, String s) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(), 0, s.length());
			usuario.setSenha(new BigInteger(1, m.digest()).toString(16));
			Usuario userDb = daoUsuario.findByID(usuario.getCpf());
			userDb.setSenha(usuario.getSenha());
			daoUsuario.save(userDb);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private String getSenhaUsuario(String cpf) {
		Usuario usuario = daoUsuario.findByID(cpf);
		return usuario != null ? usuario.getSenha() : null;
	}

	public boolean validarSenha(String senha) {
		if (senha != null && (senha.length() < 4 || !senha.matches(".*\\d.*")))
			return false;
		return true;
	}

	public boolean salvarUsuario(DtoUsuario usuario) {
		try {
			Usuario usuarioDb = getUsuarioDB(usuario.getCpf());
			if (usuarioDb == null) {
				usuarioDb = new Usuario();
				usuarioDb.setCpf(usuario.getCpf());
			}
			usuarioDb.setNome(usuario.getNome().trim());
			usuarioDb.setEndereco(usuario.getEndereco().trim());
			usuarioDb.setNascimento(usuario.getNascimento().getTime());
			usuarioDb.setEmail(usuario.getEmail().trim().equals("") ? null : usuario.getEmail().trim());
			usuarioDb.setConta(daoConta.findByID(usuario.getNumConta()));
			usuarioDb.setTipoUsuario(daoTiposUsuarios.findByID(usuario.getTipoUsuarioId()));
			daoUsuario.save(usuarioDb);
			criarSenha(usuario, usuario.getSenha());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void resetarSenha(DtoUsuario usuario) {
		criarSenha(usuario, "abc123");
	}

	public boolean usuarioExiste(String cpf) {
		Usuario usuario = daoUsuario.findByID(cpf);
		return usuario != null;
	}

	public Collection<DtoUsuario> getUsuarios() {
		ArrayList<DtoUsuario> lista = new ArrayList<>();
		for (Usuario usuario : daoUsuario.findAll()) {
			lista.add(getUsuario(usuario));
		}
		return lista;
	}

	public DtoUsuario getUsuario(String cpf) {
		Usuario usuario = daoUsuario.findByID(cpf);
		if (usuario != null) {
			return getUsuario(usuario);
		}
		return null;
	}

	public DtoUsuario getUsuario(Usuario usuario) {
		Calendar nascimento = Calendar.getInstance();
		nascimento.setTime(usuario.getNascimento());
		return new DtoUsuario(usuario.getCPF(), usuario.getNome(), usuario.getEndereco(), usuario.getEmail(),
				usuario.getSenha(), nascimento, usuario.getConta().getId(),
				new DtoTipoUsuario(usuario.getTipoUsuario().getId(), usuario.getTipoUsuario().getDescricao()));
	}

	public Usuario getUsuarioDB(String cpf) {
		Usuario usuario = daoUsuario.findByID(cpf);
		return usuario;
	}
}
