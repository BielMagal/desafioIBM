package com.desafio.BancoModel.model;

public interface EntidadeBase {
	
	public Object getId();
	
	public default String getStringId(){
		return getId().toString();
	}
}