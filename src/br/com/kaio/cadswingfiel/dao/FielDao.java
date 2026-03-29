package br.com.kaio.cadswingfiel.dao;

import java.util.List;

import br.com.kaio.cadswingfiel.domain.Fiel;
import br.com.kaio.cadswingfiel.persistence.EmFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class FielDao {

	private EntityManager getManager() {
		return EmFactory.getEm();
	}

	public List<Fiel> listarFiel() {
		String jpql = "SELECT f FROM Fiel f";
		return getManager().createQuery(jpql, Fiel.class).getResultList();
	}

	public void adicionarFiel(Fiel novo) {
		executeInTransaction(() -> getManager().persist(novo));
	}

	public void atualizarFiel(Fiel fiel) {
		executeInTransaction(() -> getManager().merge(fiel));
	}

	public void removerFiel(String cpf) throws Exception {
		if (cpf == null || cpf.isBlank()) {
			throw new IllegalArgumentException("CPF não pode ser vazio.");
		}

		executeInTransaction(() -> {
			String cleanCpf = cpf.trim();

			// Remove pagamentos primeiro (pela restrição de chave estrangeira)
			getManager().createQuery("DELETE FROM Pagamento p WHERE p.id.cpf = :cpf").setParameter("cpf", cleanCpf)
					.executeUpdate();

			int deletados = getManager().createQuery("DELETE FROM Fiel f WHERE f.cpf = :cpf")
					.setParameter("cpf", cleanCpf).executeUpdate();

			if (deletados == 0) {
				throw new RuntimeException("Fiel não encontrado com o CPF: " + cleanCpf);
			}
		});
	}

	public List<Fiel> buscarPorFiltro(String cpf, String nome) {
		String jpql = """
				  SELECT f FROM Fiel f
				  WHERE (:cpf = '' OR f.cpf = :cpf)
				  AND (:nome = '' OR f.nome LIKE :nome)
				""";

		return getManager().createQuery(jpql, Fiel.class).setParameter("cpf", cpf != null ? cpf.trim() : "")
				.setParameter("nome", (nome != null && !nome.isBlank()) ? "%" + nome.trim() + "%" : "").getResultList();
	}

	/**
	 * Método utilitário para envolver operações em transações de forma segura.
	 */
	private void executeInTransaction(Runnable action) {
		EntityTransaction tx = getManager().getTransaction();
		try {
			tx.begin();
			action.run();
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();
			throw e;
		}
	}
}
