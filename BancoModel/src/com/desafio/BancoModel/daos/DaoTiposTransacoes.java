package com.desafio.BancoModel.daos;

import java.util.List;

import com.desafio.BancoModel.model.TiposTransacoes;

public class DaoTiposTransacoes extends DaoBase<TiposTransacoes> {

	public void delete(Object id) {
		super.delete(TiposTransacoes.class, id);
	}

	public TiposTransacoes findByID(Object id) {
		return super.findByID(TiposTransacoes.class, id);
	}

	public List<TiposTransacoes> findAll() {
		return super.findAll(TiposTransacoes.class);
	}
	
}
