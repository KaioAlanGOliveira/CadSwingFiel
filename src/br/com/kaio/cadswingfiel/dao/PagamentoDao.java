package br.com.kaio.cadswingfiel.dao;

import java.util.List;

import br.com.kaio.cadswingfiel.domain.Pagamento;
import br.com.kaio.cadswingfiel.domain.PagamentoId;
//import br.com.kaio.cadswingfiel.domain.PagamentoView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class PagamentoDao {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("fielPersistenceUnit");
	private static EntityManager em = emf.createEntityManager();

	public Pagamento getPagamento(PagamentoId id) throws Exception {
		return em.find(Pagamento.class, id);
	}

	public List<Pagamento> getLista(String cpf) throws Exception {

		try {
			String sql = """
					    select o
					    from Pagamento o
					    where
					        (:cpf = '' or o.id.cpf = :cpf)
					""";

			TypedQuery<Pagamento> query = em.createQuery(sql, Pagamento.class);
			query.setParameter("cpf", cpf);

			return query.getResultList();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void adicionar(Pagamento pg) throws Exception {

		try {

			em.getTransaction().begin();
			String sql = "SELECT MAX(cod_pagamento) AS maior_codigo FROM pagamento WHERE cpf = :cpf";
			jakarta.persistence.Query query = em.createNativeQuery(sql);
			query.setParameter("cpf", pg.getId().getCpf());
			Number max = (Number) query.getSingleResult();
			Integer proximo = (max == null) ? 1 : max.intValue() + 1;

			pg.getId().setCodPagamento(proximo);

			em.persist(pg);

			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao adicionar fiel", e);
		}
	}

	public void atualizar(Pagamento pg) throws Exception {

		try {
			em.getTransaction().begin();
			em.merge(pg);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao atualizar fiel", e);
		}

	}

	public void apagar(Pagamento pagamento) throws Exception {

		try {
			em.getTransaction().begin();
			Pagamento pag = em.find(Pagamento.class, pagamento.getId());
			em.remove(pag);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static List<Pagamento> getLista(long cpf) {

		String sql = """
				    select o
				    from Pagamento o
				    where
				        (:cpf = '' or o.id.cpf = :cpf)
				""";

		TypedQuery<Pagamento> query = em.createQuery(sql, Pagamento.class);
		query.setParameter("cpf", cpf);

		return query.getResultList();
	}

}





//
//public List<PagamentoView> listarDizimistas() {
//	String sql = "SELECT fw FROM PagamentoView  fw";
//	TypedQuery<PagamentoView> typedQuery = em.createQuery(sql, PagamentoView.class);
//	return typedQuery.getResultList();
//}
//

//
//public void adicionaDizimistas(Pagamento novo) {
//
//	em.getTransaction().begin();
//	em.persist(novo);
//	em.getTransaction().commit();
//
//}
//}
