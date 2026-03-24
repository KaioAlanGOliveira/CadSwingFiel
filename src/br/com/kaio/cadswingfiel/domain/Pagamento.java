package br.com.kaio.cadswingfiel.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Pagamento {

	
	@EmbeddedId
	private PagamentoId id;
	
	private int cod_pagamento;
	private long valor;
	private int tipo;

	public int getCod_pagamento() {
		return cod_pagamento;
	}

	public void setCod_pagamento(int cod_pagamento) {
		this.cod_pagamento = cod_pagamento;
	}

	public long getValor() {
		return valor;
	}

	public void setValor(long valor) {
		this.valor = valor;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public PagamentoId getId() {
		return id;
	}

	public void setId(PagamentoId id) {
		this.id = id;
	}

	public Pagamento() {

	}

}
