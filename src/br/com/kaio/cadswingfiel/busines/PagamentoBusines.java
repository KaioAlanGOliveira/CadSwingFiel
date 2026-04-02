package br.com.kaio.cadswingfiel.busines;

import br.com.kaio.cadswingfiel.dao.PagamentoDao;
import br.com.kaio.cadswingfiel.domain.Pagamento;

public class PagamentoBusines {

	PagamentoDao pgDao = new PagamentoDao();

	public Integer salvar(Pagamento pg) {

		try {

			Integer codPagamento = pgDao.adicionar(pg).intValue();

			return codPagamento;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}

	public Pagamento atualizarBss(Pagamento pg) {

		try {

			pg = pgDao.atualizar(pg);
			return pg;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	public int apagar(Pagamento pg) {
		
		try {
			
			pgDao.apagar(pg);
			return 2;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return 0;
	}

}
