package br.com.kaio.cadswingfiel.busines;

import java.util.List;

import br.com.kaio.cadswingfiel.dao.FielDao;
import br.com.kaio.cadswingfiel.dao.PagamentoDao;
import br.com.kaio.cadswingfiel.domain.Fiel;
import br.com.kaio.cadswingfiel.domain.Pagamento;
import jakarta.transaction.Transactional.TxType;

public class PagamentoBusines {

	private final PagamentoDao dao = new PagamentoDao();

	public void salvarPagamento(Pagamento pg) throws Exception {

		if (pg.getId().getCpf() == " ") {

			throw new Exception("O nome deve ter pelo menos 3 caracteres.");
		}

		if () {

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
