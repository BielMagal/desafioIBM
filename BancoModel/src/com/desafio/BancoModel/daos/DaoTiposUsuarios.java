package com.desafio.BancoModel.daos;

import java.util.List;

import com.desafio.BancoModel.model.TiposUsuarios;

public class DaoTiposUsuarios extends DaoBase<TiposUsuarios> {

	public void delete(Object id) {
		super.delete(TiposUsuarios.class, id);
	}

	public TiposUsuarios findByID(Object id) {
		return super.findByID(TiposUsuarios.class, id);
	}

	public List<TiposUsuarios> findAll() {
		return super.findAll(TiposUsuarios.class);
	}
	
}
