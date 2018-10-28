package com.desafio.BancoModel.daos;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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

	public List<Transacao> encontraTodosPorDataConta(Date inicio, Date fim, Integer contaId){
			String txt = "SELECT t FROM Transacao t WHERE t.data > :inicio AND t.data < :fim";
			if (contaId != null)
				txt += " AND (t.contaOrigem.id = :contaId or t.contaDestino.id = :contaId)";
			txt += " ORDER BY t.data DESC";
			EntityManager em = getEM();
			Query query = em.createQuery(txt);
			query.setParameter("inicio", inicio);
			query.setParameter("fim", fim);
			if (contaId != null)
				query.setParameter("contaId", contaId);
			return query.getResultList();
		}
	
}
