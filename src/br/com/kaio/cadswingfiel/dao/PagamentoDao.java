package br.com.kaio.cadswingfiel.dao;

import java.util.List;

import br.com.kaio.cadswingfiel.domain.Pagamento;
import br.com.kaio.cadswingfiel.domain.PagamentoId;
import br.com.kaio.cadswingfiel.domain.PagamentoView;
import br.com.kaio.cadswingfiel.persistence.EmFactory;
//import br.com.kaio.cadswingfiel.domain.PagamentoView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class PagamentoDao {

	public Pagamento getPagamentoById(PagamentoId id) throws Exception {
		EntityManager em = EmFactory.getEm();
		return em.find(Pagamento.class, id);
	}

	public Long adicionar(Pagamento pg) throws Exception {

		EntityManager em = EmFactory.getEm();

		try {

			String sql = "SELECT IFNULL(MAX(cod_pagamento) + 1, 1)AS maior_codigo FROM pagamento WHERE cpf = :cpf";
			Query query = em.createNativeQuery(sql);
			query.setParameter("cpf", pg.getId().getCpf());
			Long max = (Long) query.getSingleResult();

			pg.getId().setCodPagamento(max.intValue());

			em.getTransaction().begin();
			em.persist(pg);
			em.getTransaction().commit();

			System.out.println("salvo");

			return max;
		} catch (Exception e) {

			throw new RuntimeException("Erro ao adicionar fiel", e);
		}
	}

	public Pagamento atualizar(Pagamento pg) throws Exception {

		EntityManager em = EmFactory.getEm();

		try {

			em.getTransaction().begin();
			em.merge(pg);
			em.getTransaction().commit();

			return pg;
		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException("erro ao atualizar fiel", e);
		}

	}

	public void apagar(Pagamento pagamento) throws Exception {

		EntityManager em = EmFactory.getEm();

		try {

			em.getTransaction().begin();
			Pagamento pag = em.find(Pagamento.class, pagamento.getId());
			em.remove(pag);
			em.getTransaction().commit();
		} catch (Exception e) {

			throw new RuntimeException(e);
		}
	}

	public List<PagamentoView> buscarPorFiltro(String cpf, String nome) throws Exception {

		EntityManager em = EmFactory.getEm();
		try {

			String jpql = """
					  SELECT vw FROM PagamentoView vw
					           WHERE (:cpf = '' OR vw.id.cpf = :cpf)
					           AND (:nome = '' OR vw.nome LIKE :nome)
					""";

			TypedQuery<PagamentoView> query = em.createQuery(jpql, PagamentoView.class);
			query.setParameter("cpf", cpf != null ? cpf.trim() : "");
			query.setParameter("nome", (nome != null && !nome.trim().isEmpty()) ? "%" + nome.trim() + "%" : "");
			return query.getResultList();
		} catch (Exception e) {

			throw new RuntimeException("Erro ao listar fiéis", e);
		}
	}
}