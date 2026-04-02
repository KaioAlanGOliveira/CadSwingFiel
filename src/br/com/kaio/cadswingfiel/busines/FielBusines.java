package br.com.kaio.cadswingfiel.busines;

import java.util.List;

import br.com.kaio.cadswingfiel.dao.FielDao;
import br.com.kaio.cadswingfiel.domain.Fiel;

public class FielBusines {

	private final FielDao dao = new FielDao();

	public void salvarFiel(Fiel fiel) throws Exception {

		if (fiel.getNome() == null || fiel.getNome().trim().length() < 3) {

			throw new Exception("O nome deve ter pelo menos 3 caracteres.");
		}

		if (!isCpfValido(fiel.getCpf())) {

			throw new Exception("CPF inválido!");
		}

		List<Fiel> existente = dao.buscarPorFiltro(fiel.getCpf(), "");
		if (!existente.isEmpty()) {

			dao.atualizarFiel(fiel);
		} else {

			dao.adicionarFiel(fiel);
		}
	}

	public List<Fiel> listarTodos() {

		return dao.listarFiel();
	}

	private boolean isCpfValido(String cpf) {

		return cpf != null && cpf.replaceAll("\\D", "").length() == 11;
	}
}
