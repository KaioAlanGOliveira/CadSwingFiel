package br.com.kaio.cadswingfiel.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PagamentoId {

	@Column(name = "cpf", length = 14)
	private String cpf;

	@Column(name = "cod_pagamento")
	private Integer codPagamento;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Integer getCodPagamento() {
		return codPagamento;
	}

	public void setCodPagamento(Integer codPagamento) {
		this.codPagamento = codPagamento;
	}

	public PagamentoId() {
		super();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PagamentoId that = (PagamentoId) o;
		return Objects.equals(cpf, that.cpf) && Objects.equals(codPagamento, that.codPagamento);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, codPagamento);
	}
}
