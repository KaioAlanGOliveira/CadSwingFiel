package br.com.kaio.cadswingfiel.dao;

import java.util.List;

import br.com.kaio.cadswingfiel.domain.Fiel;
import br.com.kaio.cadswingfiel.domain.Pagamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class FielDao {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("fielPersistenceUnit");
	private static EntityManager em = emf.createEntityManager();

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

	public void removerFiel(long cpf) {

		em.getTransaction().begin();
		
		List<Pagamento> lista = PagamentoDao.getLista(cpf);
		for (Pagamento f : lista) {
			em.remove(f);
		}
		Fiel f = em.createQuery("SELECT f FROM Fiel f WHERE f.cpf = :cpf", Fiel.class).setParameter("cpf", cpf)
				.getSingleResult();
		em.remove(f);
		em.getTransaction().commit();

		System.out.println("apagou");
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
		em.persist(fiel);
		em.getTransaction().commit();
	}
}