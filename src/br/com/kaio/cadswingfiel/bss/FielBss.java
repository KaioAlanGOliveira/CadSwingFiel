package br.com.kaio.cadswingfiel.bss;

import java.util.List;

import br.com.kaio.cadswingfiel.dao.FielDao;
import br.com.kaio.cadswingfiel.domain.Fiel;

public class FielBss {

	private final FielDao dao = new FielDao();

	public void salvarFiel(Fiel fiel) throws Exception {
		// REGRA DE NEGÓCIO 1: Validação de Campos
		if (fiel.getNome() == null || fiel.getNome().trim().length() < 3) {
			throw new Exception("O nome deve ter pelo menos 3 caracteres.");
		}

		if (!isCpfValido(fiel.getCpf())) {
			throw new Exception("CPF inválido!");
		}

		// REGRA DE NEGÓCIO 2: Verificar se já existe
		List<Fiel> existente = dao.buscarPorFiltro(fiel.getCpf(), "");
		if (!existente.isEmpty()) {
			// Se for um novo cadastro, não pode repetir CPF
			dao.atualizarFiel(fiel); // Se já existe, atualiza
		} else {
			dao.adicionarFiel(fiel); // Se não existe, cria novo
		}
	}

	public List<Fiel> listarTodos() {
		return dao.listarFiel();
	}

	private boolean isCpfValido(String cpf) {
		// Aqui podes colocar a lógica real de validação de CPF
		return cpf != null && cpf.replaceAll("\\D", "").length() == 11;
	}
}
