package com.desafio.BancoModel.daos;

import java.util.List;

import com.desafio.BancoModel.model.Conta;

public class DaoConta extends DaoBase<Conta> {

	public void delete(Object id) {
		super.delete(Conta.class, id);
	}

	public Conta findByID(Object id) {
		return super.findByID(Conta.class, id);
	}

	public List<Conta> findAll() {
		return super.findAll(Conta.class);
	}
	
}
