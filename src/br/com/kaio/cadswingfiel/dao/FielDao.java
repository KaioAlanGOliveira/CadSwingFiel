package br.com.kaio.cadswingfiel.dao;

import java.util.List;

import br.com.kaio.cadswingfiel.domain.Fiel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class FielDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("fielPersistenceUnit");
	private EntityManager em = emf.createEntityManager();

	public List<Fiel> listarFiel() {

		String sql = "SELECT f FROM Fiel f";
		try {
			TypedQuery<Fiel> typedQuery = em.createQuery(sql, Fiel.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public void removerFiel(String cpf) throws Exception {

		try {

			em.getTransaction().begin();

			// 1. Remover pagamentos associados primeiro
			em.createQuery("DELETE FROM Pagamento p WHERE p.fiel.cpf = :cpf").setParameter("cpf", cpf).executeUpdate();

			// 2. Procurar o Fiel e remover
			List<Fiel> resultados = em.createQuery("SELECT f FROM Fiel f WHERE f.cpf = :cpf", Fiel.class)
					.setParameter("cpf", cpf).getResultList();

			if (!resultados.isEmpty()) {
				em.remove(resultados.get(0));
				em.getTransaction().commit();
				System.out.println("Fiel e pagamentos removidos.");
			} else {
				em.getTransaction().rollback();
				System.out.println("Fiel não encontrado.");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void adicionarFiel(Fiel novo) {

		em.getTransaction().begin();
		em.persist(novo);
		em.getTransaction().commit();

	}

	public List<Fiel> buscarPorFiltro(String cpf, String nome) throws Exception {
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

		em.getTransaction().begin();
		em.merge(fiel);
		em.getTransaction().commit();
	}
}