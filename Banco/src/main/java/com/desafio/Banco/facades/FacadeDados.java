package com.desafio.Banco.facades;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.desafio.Banco.dtos.DtoConta;
import com.desafio.Banco.dtos.DtoTipoTransacao;
import com.desafio.Banco.dtos.DtoTipoUsuario;
import com.desafio.Banco.dtos.DtoTransacao;
import com.desafio.Banco.dtos.DtoUsuario;
import com.desafio.Banco.utils.BancoUtil;
import com.desafio.BancoModel.daos.DaoConta;
import com.desafio.BancoModel.daos.DaoTiposTransacoes;
import com.desafio.BancoModel.daos.DaoTiposUsuarios;
import com.desafio.BancoModel.daos.DaoTransacao;
import com.desafio.BancoModel.daos.DaoUsuario;
import com.desafio.BancoModel.model.Conta;
import com.desafio.BancoModel.model.TiposTransacoes;
import com.desafio.BancoModel.model.TiposUsuarios;
import com.desafio.BancoModel.model.Transacao;
import com.desafio.BancoModel.model.Usuario;

public class FacadeDados {
	DaoConta daoConta = new DaoConta();
	DaoUsuario daoUsuario = new DaoUsuario();
	DaoTiposUsuarios daoTiposUsuarios = new DaoTiposUsuarios();
	DaoTransacao daoTransacao = new DaoTransacao();
	DaoTiposTransacoes daoTiposTransacoes = new DaoTiposTransacoes();
	public FacadeDados() {

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

	public boolean salvarUsuario(DtoUsuario usuario, boolean novoUsuario) {
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
			if(novoUsuario) {
				Conta c = new Conta();
				c.setCriacao(new Date(System.currentTimeMillis()));
				c.setSaldo(0d);
				c.setUsuario(usuario.getCpf());
				c = daoConta.save(c);
				usuarioDb.setConta(c);
			}				
			else
				usuarioDb.setConta(daoConta.findByID(Integer.valueOf(usuario.getNumConta())));
			usuarioDb.setTipoUsuario(daoTiposUsuarios.findByID(usuario.getTipoUsuarioId()));
			daoUsuario.save(usuarioDb);
			if(usuarioDb.getSenha() != null && !usuarioDb.getSenha().equals(usuario.getSenha()))
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
				usuario.getSenha(), nascimento, usuario.getConta().getStringId(),
				new DtoTipoUsuario(usuario.getTipoUsuario().getId(), usuario.getTipoUsuario().getDescricao()));
	}

	public Usuario getUsuarioDB(String cpf) {
		Usuario usuario = daoUsuario.findByID(cpf);
		return usuario;
	}

	public Conta getConta(Integer numConta) {
		return daoConta.findByID(numConta);
	}

	public ArrayList<DtoTransacao> getTransacoesPorDataConta(Date inicio, Date fim, Integer contaId) {
		ArrayList<DtoTransacao> list = new ArrayList<DtoTransacao>();
		for(Transacao t : daoTransacao.encontraTodosPorDataConta(inicio, fim, contaId)){
			DtoTransacao dt = new DtoTransacao(t);
			Usuario u;
			if(dt.getTipoTransacao().utilizaOrigem()) {
				u= daoUsuario.findByID(t.getContaOrigem().getUsuario());
				dt.setContaOrigem(t.getContaOrigem().getStringId());
				dt.setUsuarioOrigem(u.getNome());
			}
			if(dt.getTipoTransacao().utilizaDestino()) {
				u = daoUsuario.findByID(t.getContaDestino().getUsuario());
				dt.setContaDestino(t.getContaDestino().getStringId());
				dt.setUsuarioDestino(u.getNome());
			}
			list.add(dt);
		}
		return list;
	}

	public boolean contaValida(String numConta) {
		return daoConta.findByID(Integer.valueOf(numConta)) != null;
	}
	
	public void salvarTransacao(DtoTransacao transacao) {
		Transacao t = new Transacao();
		TiposTransacoes tt = daoTiposTransacoes.findByID(transacao.getTipoTransacaoNome());
		DtoTipoTransacao tipoTransacao = new DtoTipoTransacao(tt);
		t.setValor(transacao.getValor());
		t.setTipoTransacao(tt);
		if(tipoTransacao.utilizaOrigem()) {
			Conta contaOrigem = daoConta.findByID(Integer.valueOf(transacao.getContaOrigem()));
			t.setContaOrigem(contaOrigem);
			contaOrigem.setSaldo(BancoUtil.fixCasasDecimais(contaOrigem.getSaldo() - t.getValor()));
			daoConta.save(contaOrigem);			
		}
		if(tipoTransacao.utilizaDestino()) {
			Conta contaDestino = daoConta.findByID(Integer.valueOf(transacao.getContaDestino()));
			t.setContaDestino(contaDestino);
			contaDestino.setSaldo(BancoUtil.fixCasasDecimais(contaDestino.getSaldo() + t.getValor()));
			daoConta.save(contaDestino);
		}
		t.setData(new Date(System.currentTimeMillis()));
		daoTransacao.save(t);
	}

	public ArrayList<DtoConta> getContas() {
		ArrayList<DtoConta> list = new ArrayList<DtoConta>();
		for(Conta c : daoConta.findAll()){
			DtoConta dc = new DtoConta(c);
			Usuario u = daoUsuario.findByID(c.getUsuario());
			if(u != null)
				dc.setNomeCliente(u.getNome());
			list.add(dc);
		}
		return list;
	}

	public ArrayList<DtoTipoUsuario> getTiposUsuarios() {
		ArrayList<DtoTipoUsuario> list = new ArrayList<DtoTipoUsuario>();
		for(TiposUsuarios tu : daoTiposUsuarios.findAll()){
			list.add(new DtoTipoUsuario(tu));
		}
		return list;
	}
}
