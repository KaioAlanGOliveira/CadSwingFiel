package br.com.kaio.cadswingfiel.dao;

import java.util.List;

import br.com.kaio.cadswingfiel.domain.Fiel;
import br.com.kaio.cadswingfiel.persistence.EmFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class FielDao {

	public List<Fiel> listarFiel() {
		
		EntityManager em = EmFactory.getEm();
		String sql = "SELECT f FROM Fiel f";
		try {

			TypedQuery<Fiel> typedQuery = em.createQuery(sql, Fiel.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Remove um Fiel e todos os seus pagamentos associados de forma segura.
	 */
	public void removerFiel(String cpf) throws Exception {
		
		EntityManager em = EmFactory.getEm();
		if (cpf == null || cpf.trim().isEmpty()) {
			throw new Exception("CPF não pode ser vazio.");
		}

		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			em.createQuery("DELETE FROM Pagamento p WHERE p.id.cpf = :cpf").setParameter("cpf", cpf.trim())
					.executeUpdate();
			int registrosDeletados = em.createQuery("DELETE FROM Fiel f WHERE f.cpf = :cpf")
					.setParameter("cpf", cpf.trim()).executeUpdate();

			tx.commit();

			if (registrosDeletados == 0) {
				throw new Exception("Fiel não encontrado com o CPF: " + cpf);
			}

		} catch (Exception e) {
			if (tx != null && tx.isActive()) {
				try {
					tx.rollback();
				} catch (Exception ex) {
				}
			}
			throw new Exception("Erro ao remover o fiel: " + e.getMessage(), e);
		}
	}

	public void adicionarFiel(Fiel novo) {

		EntityManager em = EmFactory.getEm();
		em.getTransaction().begin();
		em.persist(novo);
		em.getTransaction().commit();

	}

	public List<Fiel> buscarPorFiltro(String cpf, String nome) throws Exception {
		
		EntityManager em = EmFactory.getEm();
		try {
			String jpql = """
					  SELECT f FROM Fiel f
					           WHERE (:cpf = '' OR f.cpf = :cpf)
					           AND (:nome = '' OR f.nome LIKE :nome)
					""";
			TypedQuery<Fiel> query = em.createQuery(jpql, Fiel.class);
			query.setParameter("cpf", cpf != null ? cpf.trim() : "");
			query.setParameter("nome", (nome != null && !nome.trim().isEmpty()) ? "%" + nome.trim() + "%" : "");
			return query.getResultList();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao listar fiéis", e);
		}
	}

	public void atualizarFiel(Fiel fiel) {

		EntityManager em = EmFactory.getEm();
		em.getTransaction().begin();
		em.merge(fiel);
		em.getTransaction().commit();
	}
}