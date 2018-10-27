package com.desafio.BancoModel.daos;

import java.util.List;

import com.desafio.BancoModel.model.Usuario;

public class DaoUsuario extends DaoBase<Usuario> {

	public void delete(Object id) {
		super.delete(Usuario.class, id);
	}

	public Usuario findByID(Object id) {
		return super.findByID(Usuario.class, id);
	}

	public List<Usuario> findAll() {
		return super.findAll(Usuario.class);
	}
	
}
