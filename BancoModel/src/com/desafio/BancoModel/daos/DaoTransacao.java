package com.desafio.BancoModel.daos;

import java.util.List;

import com.desafio.BancoModel.model.Transacao;

public class DaoTransacao extends DaoBase<Transacao> {

	public void delete(Transacao id) {
		super.delete(Transacao.class, id);
	}

	public Transacao findByID(Object id) {
		return super.findByID(Transacao.class, id);
	}

	public List<Transacao> findAll() {
		return super.findAll(Transacao.class);
	}
	
}
