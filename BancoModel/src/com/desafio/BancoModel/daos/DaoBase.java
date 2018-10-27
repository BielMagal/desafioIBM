package com.desafio.BancoModel.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.desafio.BancoModel.model.EntidadeBase;

public class DaoBase<T extends EntidadeBase> {

	public static final String PERSISTENCE_UNIT = "BancoModel";

	public static EntityManagerFactory emf = null;

	public EntityManager getEM() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		}
		return emf.createEntityManager();
	}

	public T save(T obj) {
		EntityManager em = getEM();
		em.getTransaction().begin();

		if (obj.getId() == null) {
			em.persist(obj);
		} else {
			if (!em.contains(obj)) {
				if (em.find(obj.getClass(), obj.getId()) == null) {
				}
			}
			obj = em.merge(obj);
		}
		em.getTransaction().commit();
		return obj;
	}

	protected T delete(Class<T> c, Object id) {
		EntityManager em = getEM();
		T obj = em.find(c, id);

		try {
			em.getTransaction().begin();
			em.remove(obj);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		return obj;
	}

	protected T findByID(Class<T> c, Object id) {
		EntityManager em = getEM();

		if (id == null)
			return null;

		T obj = null;
		try {
			obj = em.find(c, id);
		} finally {
			em.close();
		}

		return obj;
	}

	protected List<T> findAll(Class<T> c) {
		EntityManager em = getEM();
		return em.createNamedQuery(c.getSimpleName() + ".findAll").getResultList();
	}

}
